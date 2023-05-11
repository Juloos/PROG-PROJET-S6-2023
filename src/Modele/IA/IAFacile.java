package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;

import java.util.Random;

import static Global.Config.*;

public class IAFacile extends Minimax implements IA {
    Random rand;
    int joueur;

    public IAFacile(int joueur) {
        super(joueur, IA_FACILE_HEURISTIQUE, IA_FACILE_PROFONDEUR,  IA_FACILE_THRESHOLD);
        this.joueur = joueur;
        rand = new Random();
    }

    public IAFacile(int joueur, int seed) {
        super(joueur, IA_FACILE_HEURISTIQUE, IA_FACILE_PROFONDEUR,  IA_FACILE_THRESHOLD);
        this.joueur = joueur;
        rand = new Random(seed);
    }

    @Override
    public Coup reflechir(Jeu j) {
        return calculerCoup(j);
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.FACILE;
    }
}
