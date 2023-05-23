package Controleur;

import Global.Config;
import IHM.TypeIHM;
import Modele.Actions.ActionCoup;
import Modele.Coups.Coup;
import Modele.Coups.CoupTerminaison;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuConcret;
import Modele.Joueurs.Joueur;

public class GestionnairePartie extends Thread {
    private final MoteurJeu moteurJeu;
    private JeuConcret jeu;
    private int nbPions;
    private boolean plateauGenere, coupJouer, pause;

    public GestionnairePartie(MoteurJeu moteurJeu) {
        super();
        this.moteurJeu = moteurJeu;
        this.plateauGenere = Config.TYPE_IHM != TypeIHM.GRAPHIQUE;
        this.coupJouer = this.pause = false;
    }

    public synchronized boolean isGamePaused() {
        return pause;
    }

    public synchronized JeuConcret getJeu() {
        return jeu;
    }

    private synchronized boolean getCoupJouer() {
        return coupJouer;
    }

    public synchronized void jouerCoup(Coup coup) {
        if (!isGamePaused()) {
            coupJouer = true;
            jeu.jouer(coup);
            moteurJeu.afficherMessage("", 0);
            nbPions++;
            updateAffichage(true);
        }
    }

    private synchronized void updateAffichage(boolean jouerAnimation) {
        moteurJeu.updateAffichage(jouerAnimation);
    }

    public synchronized boolean estPlateauFixer() {
        return plateauGenere;
    }

    public synchronized boolean estPhasePlacementPions() {
        return nbPions < jeu.getNbPions() * jeu.getNbJoueurs();
    }

    public synchronized boolean estPhaseDeplacementPion() {
        return !jeu.estTermine();
    }

    private void afficherMessage(String message, int duration) {
        moteurJeu.afficherMessage(message, duration);
    }

    public synchronized void annulerCoup() {
        moteurJeu.debug("Annulation du dernier coup joué");
        moteurJeu.pauseGame(true);
        nbPions--;
        jeu.annuler();
        updateAffichage(false);
    }

    public synchronized void refaireCoup() {
        moteurJeu.debug("Refaison du dernier coup annulé");
        moteurJeu.pauseGame(true);
        nbPions++;
        jeu.refaire();
        updateAffichage(false);
    }

    private boolean peutJouer(Jeu jeu) {
        return jeu.peutJouer();
    }

    @Override
    public void run() {
        super.run();

        moteurJeu.debutDePartie();

        updateAffichage(false);

        while (!estPlateauFixer()) ;

        afficherMessage("Début de la partie", 3000);

        while (!jeu.estTermine()) {
            while (peutJouer(jeu)) {
                waitPause();
                coupJouer = false;
                Joueur joueur = jeu.getJoueur();
                joueur.reflechir(moteurJeu);
                while (!getCoupJouer() && !isGamePaused()) ;
            }
            waitPause();
            moteurJeu.appliquerAction(new ActionCoup(new CoupTerminaison(jeu.getJoueur().getID())));
        }

        waitPause();
        moteurJeu.finDePartie();

        moteurJeu.debug("Fin de la partie");

        if (!moteurJeu.hasIHM()) {
            moteurJeu.terminer();
        }
    }

    private void waitPause() {
        while (isGamePaused()) ;
    }

    public synchronized void genePlateau() {
        jeu.generateNewPlateau();
        updateAffichage(false);
    }

    public synchronized void fixerPlateau() {
        this.plateauGenere = true;
    }

    public synchronized void lancerPartie(Joueur[] joueurs) {
        this.jeu = new JeuConcret(joueurs);
        updateAffichage(false);
        this.nbPions = 0;

        moteurJeu.debug("Nombre de joueurs : " + joueurs.length);
        for (Joueur j : joueurs) {
            moteurJeu.debug(j.getNom());
        }
    }

    public synchronized void lancerPartie(JeuConcret jeu) {
        this.jeu = jeu;
        updateAffichage(false);
        this.plateauGenere = jeu.nbPionsSurPlateau() > 0;
        this.nbPions = 0;
        for (int i = 0; i < jeu.getNbJoueurs(); i++) {
            nbPions += jeu.getJoueur(i).getPions().size();
        }
    }

    public synchronized void pauseGame(boolean pause) {
        moteurJeu.debug("Jeu en pause : " + pause);
        this.pause = pause;
        if (pause) {
            afficherMessage("", 0);
        }
    }
}
