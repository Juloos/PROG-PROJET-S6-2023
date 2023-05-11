package Controleur;

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

    private synchronized PhasesPartie getPhasePartie() {
        return phasePartie;
    }

    public synchronized void jouerCoup(Coup coup) {
        waitPause();
        jeu.jouer(coup);
        updateAffichage();
    }

    private synchronized void updateAffichage() {
        if (partieEnCours()) {
            moteurJeu.debug("Mise à jour de l'affichage");
            moteurJeu.updateAffichage();
        }
    }

    public synchronized boolean estPhasePlacementPions() {
        nbPionsPlaces = 0;
        for (int i = 0; i < jeu.getNbJoueurs(); i++) {
            nbPionsPlaces += jeu.getJoueur(i).getPions().size();
        }
        return partieEnCours() && nbPionsPlaces < jeu.getNbJoueurs() * jeu.getNbPions();
    }

    public synchronized boolean partieEnCours() {
        return phasePartie == PhasesPartie.PARTIE_EN_COURS || phasePartie == PhasesPartie.PAUSE;
    }

    public synchronized boolean estPhaseDeplacementPion() {
        return partieEnCours() && !jeu.estTermine();
    }

    private void afficherMessage(String message) {
        moteurJeu.afficherMessage(message);
    }

    public synchronized void annulerCoup() {
        moteurJeu.debug("Annulation du dernier coup joué");
        jeu.annuler();
        updateAffichage();
    }

    public synchronized void refaireCoup() {
        moteurJeu.debug("Refaison du dernier coup annulé");
        jeu.refaire();
        updateAffichage();
    }

    private boolean peutJouer(Jeu jeu) {
        return partieEnCours() && jeu.peutJouer();
    }

    @Override
    public void run() {
        super.run();
        moteurJeu.debug("En attente d'une nouvelle partie");

        if (moteurJeu.hasIHM()) {
            moteurJeu.attendreNouvellePartie();
        }
        phasePartie = PhasesPartie.ATTENTE_PARTIE;
        while (moteurJeu.hasIHM() && phasePartie == PhasesPartie.ATTENTE_PARTIE) ;

        moteurJeu.debutDePartie();

        moteurJeu.debug("Début de la partie");
        moteurJeu.updateAffichage();

        nbPionsPlaces = 0;
        while (estPhasePlacementPions()) {
            moteurJeu.appliquerAction(jeu.getJoueur().reflechir(moteurJeu));
        }

        moteurJeu.debug("Fin de la phase de placement des pions");
        afficherMessage("Fin de la phase de placement des pions");

        moteurJeu.updateAffichage();

        while (estPhaseDeplacementPion()) {
            while (peutJouer(jeu)) {
                moteurJeu.appliquerAction(jeu.getJoueur().reflechir(moteurJeu));
            }
            jeu.jouer(new CoupTerminaison(jeu.getJoueur().getID()));
        }

        moteurJeu.finDePartie();

        moteurJeu.debug("Fin de la partie");

        if (!moteurJeu.hasIHM()) {
            moteurJeu.terminer();
        }
    }

    public void waitPause() {
        while (moteurJeu.hasIHM() && phasePartie == PhasesPartie.PAUSE) ;
    }

    public synchronized void lancerPartie(Joueur[] joueurs) {
        this.jeu = new JeuConcret(joueurs);
        this.phasePartie = PhasesPartie.PARTIE_EN_COURS;
    }

    public synchronized void lancerPartie(String nomSave) {
        this.jeu = JeuConcret.charger(nomSave);
        this.phasePartie = PhasesPartie.PARTIE_EN_COURS;
    }

    public synchronized void pauseGame(boolean pause) {
        moteurJeu.debug("Jeu en pause : " + pause);
        this.phasePartie = pause ? PhasesPartie.PAUSE : PhasesPartie.PARTIE_EN_COURS;
    }
}
