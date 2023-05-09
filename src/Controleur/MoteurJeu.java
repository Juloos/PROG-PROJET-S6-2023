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
import Modele.IA.IA;
import Modele.Jeu.JeuConcret;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;

import java.util.ArrayList;
import java.util.List;

public class MoteurJeu extends Thread {

    IHM ihm;

    JeuConcret jeu;

    int nbPionsPlaces;

    boolean pause, miseAJourAffichage;
    Thread threadIHM;

    public MoteurJeu() {
        super();
        Joueur[] joueurs = new Joueur[]{new JoueurIA(0), new JoueurIA(1)};
        jeu = new JeuConcret(joueurs);

        switch (Config.TYPE_IHM) {
            case CONSOLE:
                ihm = new IHMConsole(this);
                break;
            case GRAPHIQUE:
                ihm = new IHMGraphique(this);
                break;
            case AUCUNE:
                ihm = null;
                break;
        }

        if (ihm != null) {
            threadIHM = new Thread(ihm);
            threadIHM.start();
        }
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
        return nbPionsPlaces < jeu.getNbJoueurs() * jeu.getNbPions();
    }

    public synchronized void jouerCoup(Coup coup) {
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
        if (ihm != null) {
            ihm.updateAffichage(jeu);
        }
    }


    @Override
    public void run() {
        updateAffichage();

        pause = false;
        nbPionsPlaces = 0;
        while (estPhasePlacementPions()) {
            waitPause();
            appliquerAction(jeu.getJoueur().reflechir(this));
        }

        ihm.afficherMessage("Fin de la phase de placement des pions");
        updateAffichage();

        while (!jeu.estTermine()) {
            waitPause();
            while (jeu.peutJouer()) {
                waitPause();
                appliquerAction(jeu.getJoueur().reflechir(this));
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            jeu.jouer(new CoupTerminaison(jeu.getJoueur().id));
        }

        updateAffichage();
    }

    public synchronized boolean isPaused() {
        return pause;
    }

    public synchronized void pauseGame(boolean pause) {
        System.out.println("Jeu en pause " + pause);
        this.pause = pause;
    }

    private void waitPause() {
        while (pause) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public synchronized void close() {
        try {
            threadIHM.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
