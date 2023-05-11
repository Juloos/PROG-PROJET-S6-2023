package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;

import java.util.Random;

import static Global.Config.*;

public class IAMoyen extends Minimax implements IA {
    Random rand;
    int joueur;

    public IAMoyen(int joueur) {
        super(joueur, IA_MOYEN_HEURISTIQUE, IA_MOYEN_PROFONDEUR,  IA_MOYEN_THRESHOLD);
        this.joueur = joueur;
        rand = new Random();
    }

    public IAMoyen(int joueur, int seed) {
        super(joueur, IA_MOYEN_HEURISTIQUE, IA_MOYEN_PROFONDEUR,  IA_MOYEN_THRESHOLD);
        this.joueur = joueur;
        rand = new Random(seed);
    }

    @Override
    public Coup reflechir(Jeu j) {
        return calculerCoup(j);
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.MOYEN;
    }
}
