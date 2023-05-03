package IHM.Actions;

import Controlleur.MoteurJeu;
import Modele.Coup;

public class ActionJouerCoup implements ActionIHM {

    Coup coup;

    public ActionJouerCoup(Coup coup) {
        this.coup = coup;
    }

    @Override
    public void action(MoteurJeu moteurJeu) {
        moteurJeu.jouerCoup(coup);
    }
}
