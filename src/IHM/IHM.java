package IHM;

import Controleur.MoteurJeu;
import Modele.Actions.Action;
import Modele.Jeu.Jeu;

public abstract class IHM implements Runnable {

    private MoteurJeu moteurJeu;

    public IHM(MoteurJeu moteurJeu) {
        this.moteurJeu = moteurJeu;
    }

    public synchronized MoteurJeu getMoteurJeu() {
        return moteurJeu;
    }

    /**
     * Met à jour l'affichage du plateau ainsi que des informations des joueurs
     *
     * @param jeu : le jeu mit à jour
     */
    public abstract void updateAffichage(Jeu jeu);

    /**
     * Attend que le joueur actif demande de faire une action
     *
     * @return l'action que le jouer actif veut faire
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
     * Ferme l'IHM
     */
    public abstract void terminer();
}
