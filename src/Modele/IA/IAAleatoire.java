package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.JeuConcret;
import Modele.Jeux.JeuGraphe;
import Modele.Joueurs.Joueur;

import java.util.ArrayList;
import java.util.Random;

public class IAAleatoire implements IA {
    Random rand;
    Joueur joueur;

    public IAAleatoire(Joueur j) {
        joueur = j;
        rand = new Random();
    }

    public IAAleatoire(Joueur j, int seed) {
        joueur = j;
        rand = new Random(seed);
    }

    @Override
    public Coup reflechir(JeuConcret j) {
        JeuGraphe jg = new JeuGraphe(j);
        ArrayList<Coup> coups = jg.coupsPossibles();
        return coups.get(rand.nextInt(coups.size()));
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.ALEATOIRE;
    }
}
