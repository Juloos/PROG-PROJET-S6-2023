package Modele;

import Controleur.MoteurJeu;

public class ActionCoup implements Action {
    Coup coup;

    public ActionCoup(Coup coup) {
        this.coup = coup;
    }

    @Override
    public void appliquer(MoteurJeu mt) {
        mt.jouerCoup(coup);
    }

    @Override
    public boolean peutAppliquer(MoteurJeu mt) {
        return coup.estJouable(mt.getJeu());
    }
}
