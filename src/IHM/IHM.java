package IHM;

import Controleur.MoteurJeu;
import Modele.Actions.Action;
import Modele.Jeux.JeuConcret;

public abstract class IHM {

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
     * @param jeu : le jeu mit à jour
     */
    public abstract void updateAffichage(JeuConcret jeu);

    /**
     * Attend que le joueur actif demande de faire une action
     *
     * @return l'action que le joueur actif veut faire
     */
    public abstract Action attendreActionJoueur();

    /**
     * Affiche un message sur l'IHM
     *
     * @param message : le message à afficher
     */
    public abstract void afficherMessage(String message);

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
