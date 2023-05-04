package Modele;

import Controleur.MoteurJeu;

public class ActionAnnuler implements Action {
    @Override
    public void appliquer(MoteurJeu mt) {
        mt.annulerCoup();
    }

    @Override
    public boolean peutAppliquer(MoteurJeu mt) {
        return !mt.getJeu().passe.empty();
    }
}
