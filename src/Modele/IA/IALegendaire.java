package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;

import java.util.Random;

import static Global.Config.*;

public class IALegendaire extends Minimax implements IA {
    Random rand;
    int joueur;

    public IALegendaire(int joueur) {
        super(joueur, IA_LEGENDAIRE_HEURISTIQUE, IA_LEGENDAIRE_PROFONDEUR,  IA_LEGENDAIRE_THRESHOLD);
        this.joueur = joueur;
        rand = new Random();
    }

    public IALegendaire(int joueur, int seed) {
        super(joueur, IA_LEGENDAIRE_HEURISTIQUE, IA_LEGENDAIRE_PROFONDEUR,  IA_LEGENDAIRE_THRESHOLD);
        this.joueur = joueur;
        rand = new Random(seed);
    }

    @Override
    public Coup reflechir(Jeu j) {
        return calculerCoup(j);
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.LEGENDAIRE;
    }
}
