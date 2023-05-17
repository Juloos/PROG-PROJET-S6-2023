package Controleur;

import Modele.Actions.ActionCoup;
import Modele.Coups.Coup;
import Modele.Coups.CoupTerminaison;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuConcret;
import Modele.Joueurs.Joueur;

public class GestionnairePartie extends Thread {
    private final MoteurJeu moteurJeu;
    private JeuConcret jeu;
    private int nbPionsPlaces;
    private PhasesPartie phasePartie;

    private boolean isPaused;

    public GestionnairePartie(MoteurJeu moteurJeu) {
        super();
        this.moteurJeu = moteurJeu;
        this.phasePartie = PhasesPartie.ATTENTE_PARTIE;
    }

    public synchronized JeuConcret getJeu() {
        return jeu;
    }

    public synchronized void jouerCoup(Coup coup) {
        waitPause();
        jeu.jouer(coup);
        nbPionsPlaces++;
        moteurJeu.afficherMessage("", 0);
        updateAffichage();
    }

    private synchronized void updateAffichage() {
        if (partieEnCours()) {
            moteurJeu.updateAffichage();
        }
    }

    public synchronized boolean estPhasePlacementPions() {
        return partieEnCours() && nbPionsPlaces < jeu.getNbPions() * jeu.getNbJoueurs();
    }

    public synchronized boolean partieEnCours() {
        return phasePartie == PhasesPartie.PARTIE_EN_COURS || phasePartie == PhasesPartie.PAUSE;
    }

    public synchronized boolean estPhaseDeplacementPion() {
        return partieEnCours() && !jeu.estTermine();
    }

    private void afficherMessage(String message, int duration) {
        moteurJeu.afficherMessage(message, duration);
    }

    public synchronized void annulerCoup() {
        moteurJeu.debug("Annulation du dernier coup joué");
        jeu.annuler();
        nbPionsPlaces--;
        updateAffichage();
    }

    public synchronized void refaireCoup() {
        moteurJeu.debug("Refaison du dernier coup annulé");
        jeu.refaire();
        nbPionsPlaces++;
        updateAffichage();
    }

    private boolean peutJouer(Jeu jeu) {
        return partieEnCours() && jeu.peutJouer();
    }

    @Override
    public void run() {
        super.run();

        moteurJeu.debutDePartie();

        updateAffichage();

        moteurJeu.debug("Début de la partie");

        updateAffichage();

        while (!jeu.estTermine()) {
            while (peutJouer(jeu)) {
                moteurJeu.appliquerAction(jeu.getJoueur().reflechir(moteurJeu));
            }
            moteurJeu.appliquerAction(new ActionCoup(new CoupTerminaison(jeu.getJoueur().getID())));
        }
        updateAffichage();

        moteurJeu.finDePartie();

        moteurJeu.debug("Fin de la partie");

        if (!moteurJeu.hasIHM()) {
            moteurJeu.terminer();
        }
    }

    public void waitPause() {
        while (phasePartie == PhasesPartie.PAUSE) ;
    }

    public synchronized void genePlateau(){
            jeu.generateNewPlateau();
    }

    public synchronized void lancerPartie(Joueur[] joueurs) {
        this.jeu = new JeuConcret(joueurs);
        this.phasePartie = PhasesPartie.PARTIE_EN_COURS;
        this.nbPionsPlaces = 0;
        updateAffichage();

        moteurJeu.debug("Nombre de joueurs : " + joueurs.length);
        for (Joueur j : joueurs) {
            moteurJeu.debug(j.getNom());
        }
    }

    public synchronized void lancerPartie(String nomSave) {
        this.jeu = JeuConcret.charger(nomSave);
        this.phasePartie = PhasesPartie.PARTIE_EN_COURS;
        this.nbPionsPlaces = 0;
        for (int i = 0; i < jeu.getNbJoueurs(); i++) {
            nbPionsPlaces += jeu.getJoueur(i).getPions().size();
        }
        updateAffichage();
    }

    public synchronized void pauseGame(boolean pause) {
        moteurJeu.debug("Jeu en pause : " + pause);
        this.phasePartie = pause ? PhasesPartie.PAUSE : PhasesPartie.PARTIE_EN_COURS;
    }
}
