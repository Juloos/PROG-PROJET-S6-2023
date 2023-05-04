package IA;
import Modele.*;

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
        Coup c;
        if ((c = randomAjout(j)).estJouable(j))
            return c;
        else if ((c = randomDeplacement(j)).estJouable(j))
            return c;
        else
            return new CoupTerminaison(joueur.id);
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.ALEATOIRE;
    }

    private CoupAjout randomAjout(Jeu j) {
        Coord c = new Coord();
        do {
            c.r = rand.nextInt(TAILLE_PLATEAU_X);
            c.q = (c.r % 2 == 0) ? rand.nextInt(TAILLE_PLATEAU_Y - 1) : rand.nextInt(TAILLE_PLATEAU_Y);
        } while (j.estPion(c) || j.getPlateau().get(c) != 1);
        return new CoupAjout(c, joueur.id);
    }

    private CoupDeplacement randomDeplacement(Jeu j) {
        Coord c = new Coord();
        do {
            c.r = rand.nextInt(TAILLE_PLATEAU_X);
            c.q = (c.r % 2 == 0) ? rand.nextInt(TAILLE_PLATEAU_Y - 1) : rand.nextInt(TAILLE_PLATEAU_Y);
        } while (!j.estPion(c) || j.joueurDePion(c) != j.getJoueur().id || j.estPionBloque(c));
        ArrayList<Coord> voisins = j.deplacementsPion(c);
        return new CoupDeplacement(c, voisins.get(rand.nextInt(voisins.size())), joueur.id);
    }
}
