package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PlateauGraphique;

public class EcranJeu extends Ecran {

    PlateauGraphique plateauGraphique;

    public EcranJeu() {
        super("Partie en cours");

        plateauGraphique = new PlateauGraphique();
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);

        ihm.getFrame().add(plateauGraphique);

        ihm.getFrame().revalidate();
    }
}
