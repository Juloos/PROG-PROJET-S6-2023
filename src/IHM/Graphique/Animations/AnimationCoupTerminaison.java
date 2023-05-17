package IHM.Graphique.Animations;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import Modele.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnimationCoupTerminaison extends Animation {
    private static final int NB_FRAMES = 4;
    private final PlateauGraphique plateauGraphique;
    private final List<Coord> pions;
    private final int TIME_BETWEEN_FRAMES;

    public AnimationCoupTerminaison(IHMGraphique ihm, Set<Coord> pions) {
        super(3000, ihm);
        this.TIME_BETWEEN_FRAMES = DURATION / NB_FRAMES;
        this.plateauGraphique = ihm.getPlateauGraphique();
        this.pions = new ArrayList<>(pions);
    }

    @Override
    public void begin() {
    }

    @Override
    public void play() {
        for (int numFrame = 0; numFrame < NB_FRAMES * 2; numFrame++) {
            if (numFrame % 2 == 0) {
                plateauGraphique.setTuilesSurbrillance(null);
            } else {
                plateauGraphique.setTuilesSurbrillance(pions);
            }
            System.out.println("Frame numéro " + numFrame);
            try {
                Thread.sleep(TIME_BETWEEN_FRAMES / 2);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void end() {
        System.out.println("Fin de l'animation de coup terminaison");
    }
}
