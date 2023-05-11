package Modele.IA;

import Modele.Jeux.Jeu;
import Global.Config;


public class Hmax implements Heuristique {
    Heuristique[] heuristiques = {new H1(), new H2(), new H3()};
    double[] poids = {Double.MAX_VALUE, 1, 1000};
    final double HVAL_THRESHOLD;

    Hmax() {
        this(Config.HVAL_THRESHOLD);
    }

    Hmax(double hval_threshold) {
        HVAL_THRESHOLD = hval_threshold;
    }

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        int i = 0;
        double value = 0;
        while (i < poids.length && Math.abs(value) < HVAL_THRESHOLD)
            value += poids[i] * heuristiques[i++].evaluer(j, pdvJoueur);
        return value;
    }
}
