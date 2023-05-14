package Controleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Coups.Coup;
import Modele.Jeux.JeuConcret;
import Modele.Joueurs.Joueur;

import javax.swing.*;

public class MoteurJeu extends Thread {

    IHM ihm;
    JeuConcret jeu;
    GestionnairePartie gestionnairePartie;
    volatile boolean isAlive;


    public MoteurJeu() {
        super();
        this.isAlive = true;

        switch (Config.TYPE_IHM) {
            case CONSOLE:
                ihm = new IHMConsole(this);
                break;
            case GRAPHIQUE:
                ihm = new IHMGraphique(this);
                SwingUtilities.invokeLater(ihm);
                break;
            default:
                ihm = null;
        }

        attendreNouvellePartie();
    }

    public void debug(String message) {
        if (Config.DEBUG) {
            System.out.println(message);
        }
    }

    public boolean hasIHM() {
        return ihm != null;
    }

    public IHM getIHM() {
        return ihm;
    }

    public synchronized JeuConcret getJeu() {
        try {
            return gestionnairePartie.getJeu();
        } catch (Exception e) {
        }
        return null;
    }

    public synchronized Joueur getJoueurActif() {
        return gestionnairePartie.getJeu().getJoueur();
    }

    public boolean estPhasePlacementPions() {
        return gestionnairePartie.getJeu() != null && gestionnairePartie.estPhasePlacementPions();
    }

    public boolean estPhaseDeplacementPion() {
        return gestionnairePartie.getJeu() != null && gestionnairePartie.estPhaseDeplacementPion();
    }

    public void attendreNouvellePartie() {
        if (hasIHM()) {
            ihm.attendreCreationPartie();
        }
    }

    public void afficherMessage(String message, int duration) {
        if (hasIHM()) {
            ihm.afficherMessage(message, duration);
        }
    }

    public void appliquerAction(Action action) {
        if (action == null || !action.peutAppliquer(this)) {
//            action.afficherMessageErreur(this);
        } else {
            action.appliquer(this);
        }
    }

    public synchronized void jouerCoup(Coup coup) {
        gestionnairePartie.jouerCoup(coup);
    }

    public synchronized void annulerCoup() {
        debug("Annulation du dernier coup joué");
        gestionnairePartie.annulerCoup();
    }

    public synchronized void refaireCoup() {
        debug("Refaison du dernier coup annulé");
        gestionnairePartie.refaireCoup();
    }

    public synchronized void sauvegarder(String nomSave) {
        debug("Sauvegarde de la partie");
        jeu = gestionnairePartie.getJeu();
        try {
            jeu.sauvegarder(nomSave);
        } catch (Exception e) {
            ihm.afficherMessage("Erreur lors de la sauvegarde", 3000);
            debug("Erreur lors de la sauvegarde");
        }
    }

    public void updateAffichage() {
        if (hasIHM()) {
            ihm.updateAffichage(gestionnairePartie.getJeu());
        }
    }

    @Override
    public void run() {
        while (isAlive) ;
    }

    public boolean partieEnPause() {
        return false;
    }

    public boolean partieEnCours() {
        return gestionnairePartie.partieEnCours();
    }

    public synchronized void pauseGame(boolean pause) {
        gestionnairePartie.pauseGame(pause);
        if (pause) {
            System.out.println("Pause");
            ihm.pause();
        } else {
            ihm.resume();
        }
    }

    public void debutDePartie() {
        if (hasIHM()) {
            ihm.debutDePartie();
        }
    }

    public void finDePartie() {
        if (hasIHM()) {
            ihm.finDePartie();
        }
    }

    public synchronized void lancerPartie(Joueur[] joueurs) {
        this.gestionnairePartie = new GestionnairePartie(this);
        gestionnairePartie.lancerPartie(joueurs);
        this.gestionnairePartie.start();
        if (hasIHM()) {
            ihm.resume();
        }
        debug("Lancement d'une nouvelle partie");
    }

    public synchronized void lancerPartie(String nomSave) {
        this.gestionnairePartie = new GestionnairePartie(this);
        gestionnairePartie.lancerPartie(nomSave);
        this.gestionnairePartie.start();
        if (hasIHM()) {
            ihm.resume();
        }
        debug("Lancement dune nouvelle partie depuis une sauvegarde");
    }

    public synchronized void arreterPartie() {
        gestionnairePartie.interrupt();
        System.out.println("Arrêt de la partie");
    }

    public synchronized void terminer() {
        debug("Arrêt des threads");
        if (hasIHM()) {
            ihm.terminer();
        }

        try {
            gestionnairePartie.interrupt();
            debug("Thread gestionnaire de parties terminé");
        } catch (Exception e) {
        }
        this.isAlive = false;
        debug("Threads tous terminer");
    }
}
