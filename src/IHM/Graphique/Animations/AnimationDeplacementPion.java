package IHM.Graphique.Animations;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import Modele.Coord;
import Modele.Coups.CoupDeplacement;

import java.util.ArrayList;
import java.util.List;

public class AnimationDeplacementPion extends Animation {
    private final PlateauGraphique plateauGraphique;
    private final CoupDeplacement deplacement;

    public AnimationDeplacementPion(IHMGraphique ihm, CoupDeplacement deplacement) {
        super(750, ihm);
        this.plateauGraphique = ihm.getPlateauGraphique();
        this.deplacement = deplacement;
    }

    @Override
    public void begin() {
        super.begin();
    }

    @Override
    public void end() {
        plateauGraphique.setTuilesSurbrillance(null);
        System.out.println("Fin de l'animation de déplacement");
        super.end();
    }

    @Override
    public void play() {
        List<Coord> coords = new ArrayList<>();
        int decalage = deplacement.source.getDecalage(deplacement.destination);
        Coord current = deplacement.source.clone();

        while (!current.equals(deplacement.destination)) {
            coords.add(current);
            System.out.println(current);
            current = current.decale(decalage);
        }
        coords.add(deplacement.destination);

        plateauGraphique.setTuilesSurbrillance(coords);

        try {
            Thread.sleep(DURATION);
        } catch (InterruptedException e) {
        }
    }
}
