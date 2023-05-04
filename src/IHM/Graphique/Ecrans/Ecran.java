package IHM.Graphique.Ecrans;

import IHM.Graphique.Fenetre;
import IHM.Graphique.IHMGraphique;

import java.awt.*;

public abstract class Ecran extends Fenetre {

    protected static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public Ecran(String title) {
        super(title);
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);

        ihm.getFrame().setContentPane(panel);
    }
}
