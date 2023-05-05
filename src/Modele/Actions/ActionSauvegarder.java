package Modele.Actions;

import Controleur.MoteurJeu;

public class ActionSauvegarder implements Action {
    @Override
    public void appliquer(MoteurJeu mt) {
        mt.sauvegarder();
    }

    @Override
    public boolean peutAppliquer(MoteurJeu mt) {
        return true;
    }
}
