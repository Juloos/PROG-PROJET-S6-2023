package Controleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Coups.Coup;
import Modele.Coups.CoupTerminaison;
import Modele.Jeu.JeuConcret;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;

public class MoteurJeu implements Runnable {

    IHM ihm;

    JeuConcret jeu;

    int nbPionsPlaces;

    boolean pause;
    Thread threadIHM;

    public MoteurJeu(Joueur[] joueurs) {
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

    public MoteurJeu() {
        this(new Joueur[] {new JoueurHumain(0), new JoueurHumain(1)});
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
        if (coup.estJouable(jeu)) {
            jeu.jouer(coup);
            nbPionsPlaces++;
            if (ihm != null)
                ihm.updateAffichage(jeu);
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

    public synchronized void sauvegarder() {
        System.out.println("Sauvegarde de la partie");
        try {
            jeu.sauvegarder("sauvegarde.txt");
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde");
        }
    }

    public void appliquerAction(Action action) {
        waitPause();
//        System.out.println("Action en cours de traitement " + action.toString());
        if (!action.peutAppliquer(this)) {
            ihm.afficherMessage("Action non applicable");
        } else {
            action.appliquer(this);
        }

        if (ihm != null) {
            ihm.updateAffichage(jeu);
        }
    }

    @Override
    public void run() {
//        try {
//            threadIHM.wait();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        if (ihm != null)
            ihm.updateAffichage(jeu);

        pause = false;
        nbPionsPlaces = 0;
        while (estPhasePlacementPions()) {
            waitPause();
            appliquerAction(jeu.getJoueur().reflechir(this));
            System.out.println("On a traitée l'action");
        }

        System.out.println("Fin de la phase de placements des pions");

        while (!jeu.estTermine()) {
            waitPause();
            while (jeu.peutJouer()) {
                waitPause();
                appliquerAction(jeu.getJoueur().reflechir(this));
                System.out.println("Coup joué");
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
            jeu.jouer(new CoupTerminaison(jeu.getJoueur().id));
        }

        ihm.updateAffichage(jeu);

        System.out.println("Fin de jeu");
    }

    public synchronized boolean isPaused() {
        return pause;
    }

    public synchronized void pauseGame(boolean pause) {
        System.out.println("Jeu en pause " + pause);
        this.pause = pause;
    }

    private void waitPause() {
        while (isPaused()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
