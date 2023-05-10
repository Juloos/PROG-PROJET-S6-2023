package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.JeuConcret;
import Modele.Jeux.JeuGraphe;
import Modele.Joueurs.Joueur;

import java.util.ArrayList;
import java.util.Random;

public class IAFacile implements IA {
    Random rand;
    Joueur joueur;

    public IAFacile(Joueur j) {
        joueur = j;
        rand = new Random();
    }

    public IAFacile(Joueur j, int seed) {
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
        return Difficulte.FACILE;
    }
}
