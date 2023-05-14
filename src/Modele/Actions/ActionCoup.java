package Modele.Actions;

import Controleur.MoteurJeu;
import Modele.Coups.Coup;

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

    @Override
    public void afficherMessageErreur(MoteurJeu mt) {
        mt.getIHM().afficherMessage(coup.getMessageErreur(), 0);
    }
}
