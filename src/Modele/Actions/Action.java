package Modele.Actions;

import Controleur.MoteurJeu;

public interface Action {
    public void appliquer(MoteurJeu mt);

    public boolean peutAppliquer(MoteurJeu mt);

    public void afficherMessageErreur(MoteurJeu mt);
}
