package IHM.Graphique.Ecrans;

import IHM.Graphique.Fenetre;
import IHM.Graphique.IHMGraphique;

public abstract class Ecran extends Fenetre {

    public Ecran(String title) {
        super(title);
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);

        ihm.getFrame().getContentPane().removeAll();
        ihm.getFrame().getContentPane().add(panel);

        panel.removeAll();
    }
}
