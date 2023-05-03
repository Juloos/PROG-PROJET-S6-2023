package IHM;

import Controleur.MoteurJeu;
import Modele.Jeu;

public abstract class IHM {

    protected MoteurJeu moteurJeu;

    public IHM(MoteurJeu moteurJeu) {
        this.moteurJeu = moteurJeu;
    }

    /**
     * met à jour l'affichage du plateau ainsi que des informations des joueurs
     *
     * @param jeu : le jeu actuel
     */
    public abstract void updateAffichage(Jeu jeu);

    /**
     * attend que le joueur fasse une action
     */
    public abstract void attendreActionJoueur();

    /**
     * affiche un message sur l'IHM
     *
     * @param message : le message à afficher
     */
    public abstract void afficherMessage(String message);
}
