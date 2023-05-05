package Modele.IA;

import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Coups.CoupTerminaison;
import Modele.Jeu.Jeu;
import Modele.Joueurs.Joueur;

import java.util.ArrayList;
import java.util.Random;

import static Global.Config.*;

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
