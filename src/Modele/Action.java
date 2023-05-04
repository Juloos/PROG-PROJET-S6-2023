package Modele;

import Controleur.MoteurJeu;

public interface Action {
    public void appliquer(MoteurJeu mt);
    public boolean peutAppliquer(MoteurJeu mt);
}
