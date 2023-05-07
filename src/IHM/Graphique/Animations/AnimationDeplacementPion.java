package IHM.Graphique.Animations;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import Modele.Coord;

import java.util.Arrays;

public class AnimationDeplacementPion extends Animation {

    private final PlateauGraphique plateauGraphique;
    private final Coord[] coords;

    public AnimationDeplacementPion(IHMGraphique ihm, Coord[] coords) {
        super(ihm, coords.length > 1 ? 0.6 : 1.5, coords.length - 1);
        this.coords = coords;
        this.plateauGraphique = ihm.getPlateauGraphique();
    }

    @Override
    void start() {
        ihm.getPlateauGraphique().setTuilesSurbrillance(Arrays.asList(coords));
    }

    @Override
    void play(int frameNumber) {
        plateauGraphique.deplacerPion(coords[frameNumber], coords[frameNumber + 1], frameNumber == 0);
    }

    @Override
    void stop() {
        try {
            Thread.sleep((long) TIME_BETWEEN_FRAMES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
