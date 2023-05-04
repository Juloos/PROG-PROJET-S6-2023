package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;

import java.awt.*;

public class EcranJeu extends Ecran {

    public EcranJeu() {
        super("Partie en cours");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        panel.setLayout(new BorderLayout());

        panel.add(ihm.getPlateauGraphique(), BorderLayout.CENTER);
    }
}
