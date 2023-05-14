package IHM.Graphique.Animations;

import Global.Config;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import Modele.Coord;
import Modele.Coups.CoupDeplacement;

import java.util.ArrayList;
import java.util.List;

public class AnimationDeplacementPion implements Animation {

    private final PlateauGraphique plateauGraphique;
    private final CoupDeplacement deplacement;

    public AnimationDeplacementPion(IHMGraphique ihm, CoupDeplacement deplacement) {
        this.plateauGraphique = ihm.getPlateauGraphique();
        this.deplacement = deplacement;
    }

    @Override
    public void start() {
    }

    @Override
    public void play(int frameNumber) {
    }

    @Override
    public void stop() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void play() {
        List<Coord> coords = new ArrayList<>();
        int decalage = deplacement.source.getDecalage(deplacement.destination);
        Coord current = deplacement.source.clone();

        while (!current.equals(deplacement.destination)) {
            coords.add(current);
            current = current.decale(decalage);
        }
        coords.add(deplacement.destination);

        plateauGraphique.setTuilesSurbrillance(coords);

        try {
            Thread.sleep((long) (Config.ANIMATION_DURATION * 1000.0));
        } catch (InterruptedException e) {
        }

        plateauGraphique.setTuilesSurbrillance(null);
        stop();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
