package Modele.Actions;

import Controleur.MoteurJeu;

public class ActionAnnuler implements Action {
    @Override
    public void appliquer(MoteurJeu mt) {
        mt.annulerCoup();
    }

    @Override
    public boolean peutAppliquer(MoteurJeu mt) {
        return !mt.getJeu().getPasse().empty();
    }

    @Override
    public void afficherMessageErreur(MoteurJeu mt) {
        mt.getIHM().afficherMessage("Aucun coup possible à annuler", 0);
    }
}
