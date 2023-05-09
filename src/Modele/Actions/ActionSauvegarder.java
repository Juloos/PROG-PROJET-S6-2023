package Modele.Actions;

import Controleur.MoteurJeu;

public class ActionSauvegarder implements Action {
    String nomSave;

    public ActionSauvegarder(String nomSave) {
        this.nomSave = nomSave;
    }

    @Override
    public void appliquer(MoteurJeu mt) {mt.sauvegarder(nomSave); }

    @Override
    public boolean peutAppliquer(MoteurJeu mt) {
        return true;
    }

    @Override
    public void afficherMessageErreur(MoteurJeu mt) {
        mt.getIHM().afficherMessage("Erreur lors de la sauvegarde");
    }
}
