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

public class MoteurJeu {

    IHM ihm;
    JeuConcret jeu;
    GestionnairePartie gestionnairePartie;
    volatile boolean isAlive;


    public MoteurJeu() {
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
        try {
            if (!action.peutAppliquer(this)) {
                action.afficherMessageErreur(this);
            } else {
                action.appliquer(this);
            }
        } catch (Exception e) {
        }
    }

    public synchronized void jouerCoup(Coup coup) {
        gestionnairePartie.jouerCoup(coup);
    }

    public synchronized void annulerCoup() {
        gestionnairePartie.annulerCoup();
    }

    public synchronized void refaireCoup() {
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

    public synchronized void updateAffichage() {
        if (hasIHM()) {
            gestionnairePartie.pauseGame(true);
            ihm.updateAffichage();
        }
    }

    public synchronized void finUpdateAffichage() {
        if (hasIHM()) {
            gestionnairePartie.pauseGame(false);
        }
    }

    public boolean partieEnPause() {
        return gestionnairePartie.isGamePaused();
    }

    public synchronized void pauseGame(boolean pause) {
        if (pause | gestionnairePartie.isGamePaused()) {
            gestionnairePartie.pauseGame(pause);
            if (pause) {
                ihm.pause();
            } else {
                ihm.resume();
            }
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

        JeuConcret jeu = JeuConcret.charger(nomSave);

        if (jeu != null) {
            gestionnairePartie.lancerPartie(jeu);
            this.gestionnairePartie.start();
            if (hasIHM()) {
                ihm.resume();
            }
            debug("Lancement dune nouvelle partie depuis une sauvegarde");
        } else {
            afficherMessage("Erreur lors du chargement de la partie", 2000);
        }
    }

    public synchronized void arreterPartie() {
        gestionnairePartie.interrupt();
    }

    public void attendreFin() {
        while (isAlive) ;
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
