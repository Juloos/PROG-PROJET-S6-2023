package Controleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.Animations.AnimationDeplacementPion;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import IHM.TypeIHM;
import Modele.Actions.Action;
import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupDeplacement;
import Modele.Coups.CoupTerminaison;
import Modele.Jeu.JeuConcret;
import Modele.Joueurs.Joueur;

import java.util.ArrayList;
import java.util.List;

public class MoteurJeu extends Thread {

    IHM ihm;

    JeuConcret jeu;

    int nbPionsPlaces;

    boolean pause, miseAJourAffichage;
    Thread threadIHM;

    EtatMoteurJeu etat;


    public MoteurJeu() {
        super();

        switch (Config.TYPE_IHM) {
            case CONSOLE:
                ihm = new IHMConsole(this);
                break;
            case GRAPHIQUE:
                ihm = new IHMGraphique(this);
                break;
            default:
                throw new RuntimeException("Besoin d'une IHM");
        }

        threadIHM = new Thread(ihm);
        threadIHM.start();

        etat = EtatMoteurJeu.ATTENTE_PARTIE;
    }

    public synchronized IHM getIHM() {
        return ihm;
    }

    public synchronized JeuConcret getJeu() {
        return jeu;
    }

    public synchronized Joueur getJoueurActif() {
        return jeu.getJoueur();
    }

    public synchronized boolean estPhasePlacementPions() {
        return partieEnCours() && nbPionsPlaces < jeu.getNbJoueurs() * jeu.getNbPions();
    }

    public synchronized boolean estPhaseDeplacementPion() {
        return partieEnCours() && !jeu.estTermine();
    }

    public synchronized void jouerCoup(Coup coup) {
        if (coup.estJouable(jeu)) {
            jeu.jouer(coup);
            nbPionsPlaces++;

            if (Config.TYPE_IHM == TypeIHM.GRAPHIQUE && coup instanceof CoupDeplacement) {
                CoupDeplacement deplacement = (CoupDeplacement) coup;

                List<Coord> coords = new ArrayList<>();
                int decalage = deplacement.source.getDecalage(deplacement.destination);
                System.out.println("Décalage : " + decalage + "\n");
                Coord current = new Coord(deplacement.source.q, deplacement.source.r);

                while (!current.equals(deplacement.destination)) {
                    coords.add(current);
                    current = current.decale(decalage);
                }
                coords.add(deplacement.destination);
                Coord[] array = new Coord[coords.size()];
                ((IHMGraphique) ihm).setAnimation(new AnimationDeplacementPion(((IHMGraphique) ihm), coords.toArray(array)));
            }
        } else if (ihm != null)
            ihm.afficherMessage("Coup injouable");
    }

    public synchronized void annulerCoup() {
        System.out.println("Annulation du dernier coup joué");
        nbPionsPlaces--;
        jeu.annuler();
    }

    public synchronized void refaireCoup() {
        System.out.println("Refaison du dernier coup annulé");
        nbPionsPlaces++;
        jeu.refaire();
    }

    public synchronized void sauvegarder(String nomSave) {
        System.out.println("Sauvegarde de la partie");
        try {
            jeu.sauvegarder(nomSave + ".txt");
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde");
        }
    }

    public void appliquerAction(Action action) {
        if (!action.peutAppliquer(this)) {
            ihm.afficherMessage("Action non applicable");
        } else {
            action.appliquer(this);
        }

        updateAffichage();
    }

    public synchronized void updateAffichage() {
        if (ihm != null && partieEnCours()) {
            ihm.updateAffichage(jeu);
        }
    }


    public synchronized EtatMoteurJeu getEtat() {
        return etat;
    }

    @Override
    public void run() {
        while (etat != EtatMoteurJeu.FIN) {
            etat = EtatMoteurJeu.ATTENTE_PARTIE;
            ihm.attendreCreationPartie();
            System.out.println("En attente d'une nouvelle partie");
            while (getEtat() == EtatMoteurJeu.ATTENTE_PARTIE) ;

            System.out.println("Début de la partie");

            updateAffichage();

            nbPionsPlaces = 0;
            while (estPhasePlacementPions()) {
                waitPause();
                appliquerAction(jeu.getJoueur().reflechir(this));
            }

            System.out.println("Fin de la phase de placement des pions");
            ihm.afficherMessage("Fin de la phase de placement des pions");
            updateAffichage();

            while (estPhaseDeplacementPion()) {
                waitPause();
                while (partieEnCours() && jeu.peutJouer()) {
                    waitPause();
                    appliquerAction(jeu.getJoueur().reflechir(this));
                }
                waitTime(100);
                if (partieEnCours()) {
                    jeu.jouer(new CoupTerminaison(jeu.getJoueur().id));
                }
            }
            System.out.println("Fin de la partie");
            updateAffichage();
        }
        System.out.println("Fin du moteur de jeu");
    }

    private void waitTime(int time) {
        if (ihm != null) {
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
        }
    }

    public boolean partieEnPause() {
        return etat == EtatMoteurJeu.PAUSE;
    }

    public boolean partieEnCours() {
        return etat == EtatMoteurJeu.PARTIE_EN_COURS || etat == EtatMoteurJeu.PAUSE;
    }

    public synchronized void pauseGame(boolean pause) {
        if (partieEnPause() || partieEnCours()) {
            System.out.println("Jeu en pause " + pause);
            this.etat = pause ? EtatMoteurJeu.PAUSE : EtatMoteurJeu.PARTIE_EN_COURS;
        }
    }

    private void waitPause() {
        while (partieEnPause()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void lancerPartie(Joueur[] joueurs) {
        this.jeu = new JeuConcret(joueurs);
        this.etat = EtatMoteurJeu.PARTIE_EN_COURS;
        System.out.println("Lancemenet d'une nouvelle partie");
    }

    public synchronized void arreterPartie() {
        if (partieEnCours()) {
            ihm.updateAffichage(jeu);
            this.etat = EtatMoteurJeu.ATTENTE_PARTIE;
        }
    }

    public synchronized void fin() {
        try {
            ihm.terminer();
            threadIHM.join();
        } catch (Exception e) {
        }
        etat = EtatMoteurJeu.FIN;
    }
}
