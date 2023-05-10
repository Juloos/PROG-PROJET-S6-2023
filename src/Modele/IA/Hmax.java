package Modele.IA;

import Modele.Jeux.Jeu;
import static Global.Config.*;


// TODO: heuristique pour la phase d'ajouts des pions avec écartement des pions mais pas trop
// TODO: racler tout les points d'un ilot

public class Hmax implements Heuristique {
    Heuristique[] heuristiques = {new H1(), new H2(), new H3()};
    double[] poids = {Double.MAX_VALUE, 1, TAILLE_PLATEAU_X * TAILLE_PLATEAU_Y * 100};

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        int i = 0;
        double value = 0;
        while (i < poids.length && Math.abs(value) < HEURISTIQUE_ABSVAL)
            value += poids[i] * heuristiques[i++].evaluer(j, pdvJoueur);
        return value;
    }
}
