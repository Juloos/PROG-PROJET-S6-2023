package Modele.Actions;

import Controleur.MoteurJeu;

public class ActionRefaire implements Action {
    @Override
    public void appliquer(MoteurJeu mt) {
        mt.refaireCoup();
    }

    @Override
    public boolean peutAppliquer(MoteurJeu mt) {
        return !mt.getJeu().getFuture().empty();
    }
}
