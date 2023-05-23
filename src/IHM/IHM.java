package IHM;

import Controleur.MoteurJeu;
import Modele.Joueurs.JoueurHumain;

/**
 * Classe abstraite étendue par toutes les IHM
 */
public abstract class IHM implements Runnable {

    protected final MoteurJeu moteurJeu;
    protected boolean isPaused;

    public IHM(MoteurJeu moteurJeu) {
        super();
        this.moteurJeu = moteurJeu;
    }

    /* Getters */

    public MoteurJeu getMoteurJeu() {
        return moteurJeu;
    }

    /* Méthodes d'instance */

    /**
     * Méthode appelée lorsqu'une partie est lancée
     */
    public abstract void debutDePartie();

    /**
     * Méthode appelé lorsqu'une partie est terminée
     */
    public abstract void finDePartie();

    /**
     * Met à jour l'affichage du plateau ainsi que des informations des joueurs
     *
     * @param jouerAnimation vrai si il faut jouer des animations
     */
    public abstract void updateAffichage(boolean jouerAnimation);

    /**
     * Attend que le joueur actif demande de faire une action
     *
     * @param : joueur le joueur humain qui doit faire une action
     */
    public abstract void attendreActionJoueur(JoueurHumain joueur);

    /**
     * Affiche un message sur l'IHM
     *
     * @param message  le message à afficher
     * @param duration la durée d'affichage du message en millis secondes
     */
    public abstract void afficherMessage(String message, int duration);

    /**
     * Attends que le nombre et le type de joueurs de la future partie soit choisis,
     * puis lance une nouvelle partie.
     */
    public abstract void attendreCreationPartie();

    /**
     * Méthode appelée lorsque le moteur de jeu se termine
     * L'IHM doit se fermer correctement et proprement
     */
    public abstract void terminer();

    /**
     * Méthode appelée lorsqu'une partie est mise en pause
     */
    public synchronized void pause() {
        this.isPaused = true;
    }

    /**
     * Méthode appelée lorsqu'une partie mise en pause reprend
     */
    public synchronized void resume() {
        this.isPaused = false;
    }
}
