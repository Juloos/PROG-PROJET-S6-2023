package IHM.Graphique.Animations;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import Modele.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnimationCoupTerminaison extends Animation {
    private static final int NB_FRAMES = 3;
    private final PlateauGraphique plateauGraphique;
    private final IHMGraphique ihm;
    private final List<Coord> pions;
    private final String nomJoueur;
    private final int TIME_BETWEEN_FRAMES;

    public AnimationCoupTerminaison(IHMGraphique ihm, String nomJoueur, Set<Coord> pions) {
        super(2000, ihm);
        this.TIME_BETWEEN_FRAMES = DURATION / NB_FRAMES;
        this.ihm = ihm;
        this.plateauGraphique = ihm.getPlateauGraphique();
        this.pions = new ArrayList<>(pions);
        this.nomJoueur = nomJoueur;
    }

    @Override
    public void begin() {
        ihm.afficherMessage("Le joueur " + nomJoueur + " est mort", 0);
    }

    @Override
    public void play() {
        System.out.println("TEMPS ENTRE CHAQUE FRAME : " + (TIME_BETWEEN_FRAMES / 2));
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
        ihm.afficherMessage("", 0);
    }
}
