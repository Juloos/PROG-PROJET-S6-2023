package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import Modele.Plateau;

import java.awt.*;

public class EcranJeu extends Ecran {

    PlateauGraphique plateauGraphique;

    public EcranJeu() {
        super("Partie en cours");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        panel.setLayout(new BorderLayout());

        if (plateauGraphique == null) {
            plateauGraphique = new PlateauGraphique();
        }

        panel.add(plateauGraphique, BorderLayout.CENTER);
    }

    public void updatePlateau(Plateau plateau) {
        plateauGraphique.setPlateau(plateau);
    }
}
