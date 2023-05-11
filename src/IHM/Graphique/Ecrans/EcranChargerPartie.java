package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;

import java.awt.*;

public class EcranChargerPartie extends Ecran {

    public EcranChargerPartie() {
        super("Charger partie");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.add(retour, BorderLayout.PAGE_END);
    }
}
