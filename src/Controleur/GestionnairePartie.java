package Controleur;

import Modele.Coups.Coup;
import Modele.Coups.CoupTerminaison;
import Modele.Jeux.JeuConcret;
import Modele.Joueurs.Joueur;

public class GestionnairePartie extends Thread {
    private final MoteurJeu moteurJeu;
    private boolean isAlive;
    private JeuConcret jeu;
    private int nbPionsPlaces;
    private volatile PhasesPartie phasePartie;

    public GestionnairePartie(MoteurJeu moteurJeu) {
        super();
        this.isAlive = true;
        this.nbPionsPlaces = 0;
        this.moteurJeu = moteurJeu;
        this.phasePartie = PhasesPartie.ATTENTE_PARTIE;
    }

    public synchronized JeuConcret getJeu() {
        return jeu;
    }

    public synchronized void jouerCoup(Coup coup) {
        jeu.jouer(coup);
        nbPionsPlaces++;
        moteurJeu.updateAffichage();
    }

    private void updateAffichage() {
        moteurJeu.updateAffichage();
    }

    public synchronized boolean estPhasePlacementPions() {
        return nbPionsPlaces < jeu.getNbJoueurs() * jeu.getNbPions();
    }

    public boolean estPhaseDeplacementPion() {
        return !jeu.estTermine();
    }

    private void afficherMessage(String message) {
        moteurJeu.afficherMessage(message);
    }

    public synchronized void annulerCoup() {
        moteurJeu.debug("Annulation du dernier coup joué");
        nbPionsPlaces--;
        jeu.annuler();
        updateAffichage();
    }

    public synchronized void refaireCoup() {
        moteurJeu.debug("Refaison du dernier coup annulé");
        nbPionsPlaces++;
        jeu.refaire();
        updateAffichage();
    }

    @Override
    public void run() {
        super.run();
        while (isAlive) {
            moteurJeu.debug("En attente d'une nouvelle partie");

            if (moteurJeu.hasIHM()) {
                moteurJeu.attendreNouvellePartie();
            }
            while (phasePartie == PhasesPartie.ATTENTE_PARTIE) ;

            if (moteurJeu.hasIHM()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            updateAffichage();

            moteurJeu.debug("Début de la partie");

            nbPionsPlaces = 0;
            while (estPhasePlacementPions()) {
                while (phasePartie == PhasesPartie.PAUSE) ;
                moteurJeu.appliquerAction(jeu.getJoueur().reflechir(moteurJeu));
            }

            moteurJeu.debug("Fin de la phase de placement des pions");
            afficherMessage("Fin de la phase de placement des pions");

            updateAffichage();

            while (estPhaseDeplacementPion()) {
                while (jeu.peutJouer()) {
                    while (phasePartie == PhasesPartie.PAUSE) ;
                    moteurJeu.appliquerAction(jeu.getJoueur().reflechir(moteurJeu));
                }
                while (phasePartie == PhasesPartie.PAUSE) ;
                jeu.jouer(new CoupTerminaison(jeu.getJoueur().id));
            }
            updateAffichage();

            moteurJeu.debug("Fin de la partie");
            if (jeu.getWinner().size() == 1) {
                moteurJeu.afficherMessage("Le joueur " + jeu.getWinner().get(0) + " a gagné!!");
            }

            if (moteurJeu.hasIHM()) {
                phasePartie = PhasesPartie.ATTENTE_PARTIE;
            } else {
                moteurJeu.terminer();
            }
        }
    }

    public void lancerPartie(Joueur[] joueurs) {
        this.jeu = new JeuConcret(joueurs);
        this.phasePartie = PhasesPartie.PARTIE_EN_COURS;
        System.out.println("Lancement partie");
    }

    public void lancerPartie(String nomSave) {
        this.jeu = JeuConcret.charger(nomSave);
        this.phasePartie = PhasesPartie.PARTIE_EN_COURS;
    }

    public synchronized void pause(boolean pause) {
        this.phasePartie = pause ? PhasesPartie.PAUSE : PhasesPartie.PARTIE_EN_COURS;
    }

    public synchronized void terminer() {
        this.isAlive = false;
        this.interrupt();
    }
}
