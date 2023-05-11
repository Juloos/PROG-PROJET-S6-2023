package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuGraphe;

import java.util.ArrayList;
import java.util.Random;

public class IAAleatoire implements IA {
    Random rand;
    int joueur;

    public IAAleatoire(int joueur) {
        this.joueur = joueur;
        rand = new Random();
    }

    public IAAleatoire(int joueur, int seed) {
        this.joueur = joueur;
        rand = new Random(seed);
    }

    @Override
    public Coup reflechir(Jeu j) {
        JeuGraphe jg = new JeuGraphe(j);
        ArrayList<Coup> coups = jg.coupsPossibles();
        return coups.get(rand.nextInt(coups.size()));
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.ALEATOIRE;
    }
}
