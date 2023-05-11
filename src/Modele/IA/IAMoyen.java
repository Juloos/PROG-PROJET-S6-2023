package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuGraphe;
import Modele.Joueurs.Joueur;

import java.util.ArrayList;
import java.util.Random;

public class IAMoyen implements IA {
    Random rand;
    int joueur;

    public IAMoyen(int joueur) {
        this.joueur = joueur;
        rand = new Random();
    }

    public IAMoyen(int joueur, int seed) {
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
        return Difficulte.MOYEN;
    }
}
