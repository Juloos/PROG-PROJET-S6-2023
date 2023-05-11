package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;

import static Global.Config.*;

import java.util.Random;

public class IADifficile extends Minimax implements IA {
    Random rand;
    int joueur;

    public IADifficile(int joueur) {
        super(joueur, IA_DIFFICILE_HEURISTIQUE, IA_DIFFICILE_PROFONDEUR,  IA_DIFFICILE_THRESHOLD);
        this.joueur = joueur;
        rand = new Random();
    }

    public IADifficile(int joueur, int seed) {
        super(joueur, IA_DIFFICILE_HEURISTIQUE, IA_DIFFICILE_PROFONDEUR,  IA_DIFFICILE_THRESHOLD);
        this.joueur = joueur;
        rand = new Random(seed);
    }

    @Override
    public Coup reflechir(Jeu j) {
        return calculerCoup(j);
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.DIFFICILE;
    }
}
