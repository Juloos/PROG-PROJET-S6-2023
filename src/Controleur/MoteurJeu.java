package Controleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Coups.Coup;
import Modele.Jeux.JeuConcret;
import Modele.Joueurs.Joueur;

public class MoteurJeu extends Thread {

    IHM ihm;
    JeuConcret jeu;
    GestionnairePartie gestionnairePartie;
    volatile EtatMoteurJeu etat;
    boolean isPaused;


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
                ihm = null;
        }

        etat = EtatMoteurJeu.ATTENTE_PARTIE;
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

    public void afficherMessage(String message) {
        if (hasIHM()) {
            ihm.afficherMessage(message);
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
            jeu.sauvegarder(nomSave + ".txt");
        } catch (Exception e) {
            ihm.afficherMessage("Erreur lors de la sauvegarde");
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
        while (etat != EtatMoteurJeu.FIN) ;
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
        return gestionnairePartie.partieEnCours();
    }

    public synchronized void pauseGame(boolean pause) {
        gestionnairePartie.pauseGame(pause);
        if (pause) {
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
        if (hasIHM()) {
            this.gestionnairePartie = new GestionnairePartie(this);
            this.gestionnairePartie.start();
            debug("Lancement d'une nouvelle partie");
            gestionnairePartie.lancerPartie(joueurs);
        } else {
            this.gestionnairePartie = new GestionnairePartie(this);
            this.gestionnairePartie.lancerPartie(joueurs);
            this.gestionnairePartie.start();
        }
    }

    public synchronized void lancerPartie(String nomSave) {
        this.gestionnairePartie = new GestionnairePartie(this);
        this.gestionnairePartie.start();
        debug("Lancement dune nouvelle partie depuis une sauvegarde");
        gestionnairePartie.lancerPartie(nomSave);
    }

    public synchronized void arreterPartie() {
        gestionnairePartie.interrupt();
        System.out.println("Arrêt de la partie");
    }

    public synchronized void terminer() {
        debug("Arrêt des threads");
        try {
            gestionnairePartie.interrupt();
            debug("Thread gestionnaire de parties terminé");
        } catch (Exception e) {
            debug("Exception batard");
        }
        debug("Threads tous terminer");
        etat = EtatMoteurJeu.FIN;
    }
}
