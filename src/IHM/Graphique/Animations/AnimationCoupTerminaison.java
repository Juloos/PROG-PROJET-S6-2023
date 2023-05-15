package IHM.Graphique.Animations;

import Global.Config;
import IHM.Graphique.Composants.PlateauGraphique;
import Modele.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnimationCoupTerminaison implements Animation {

    private static final int NB_FRAMES = 4;
    private final PlateauGraphique plateauGraphique;
    private final List<Coord> pions;

    public AnimationCoupTerminaison(PlateauGraphique plateauGraphique, Set<Coord> pions) {
        this.plateauGraphique = plateauGraphique;
        this.pions = new ArrayList<>(pions);
    }

    @Override
    public void start() {

    }

    @Override
    public void play() {
        final long TIME_BETWEEN_FRAMES = (long) (Config.ANIMATION_DURATION * 2 / NB_FRAMES);
        System.out.println("Time between frames : " + TIME_BETWEEN_FRAMES);
        System.out.println("Nombre de pions : " + pions.size());

        for (int i = 0; i < NB_FRAMES; i++) {
            plateauGraphique.setTuilesSurbrillance(new ArrayList<>(pions));
            plateauGraphique.repaint();
            try {
                Thread.sleep(TIME_BETWEEN_FRAMES / 2);
            } catch (InterruptedException e) {
            }
            plateauGraphique.setTuilesSurbrillance(null);
            plateauGraphique.repaint();
            try {
                Thread.sleep(TIME_BETWEEN_FRAMES / 2);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }
}
