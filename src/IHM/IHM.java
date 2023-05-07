package IHM;

import Controleur.MoteurJeu;
import IHM.Graphique.Animations.Animation;
import Modele.Actions.Action;
import Modele.Jeu.Jeu;

public abstract class IHM implements Runnable {

    protected MoteurJeu moteurJeu;

    public IHM(MoteurJeu moteurJeu) {
        this.moteurJeu = moteurJeu;
    }

    /**
     * Met à jour l'affichage du plateau ainsi que des informations des joueurs
     *
     * @param jeu : le jeu actuel
     */
    public abstract void updateAffichage(Jeu jeu);

    /**
     * Attend que le joueur actif demande de faire une action
     *
     * @return l'action que le jouer actif veut faire
     */
    public abstract Action attendreActionJoueur();

    /**
     * affiche un message sur l'IHM
     *
     * @param message : le message à afficher
     */
    public abstract void afficherMessage(String message);
}
