package Modele.IA;

import Modele.Jeux.Jeu;


public class Hmax implements Heuristique {
    int nbAppels = 0;
    Heuristique[] heuristiques = {new H1(), new H2(), new H4(), new H5(), new H6(), new H7(), new H8(), new H9(), new H10()};
    double[] poids = {Double.MAX_VALUE, 1, 2000, 100, 100, 100, 100, 100, 100};
    final double HVAL_THRESHOLD;

    public Hmax() {
        HVAL_THRESHOLD = 10000;
    }

    public Hmax(double hval_threshold) {
        HVAL_THRESHOLD = hval_threshold;
    }

    public Hmax(double[] poids) {
        HVAL_THRESHOLD = poids[poids.length - 1];
        this.poids = poids;
    }

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        int i = 0;
        double value = 0;
        while (i < poids.length && Math.abs(value) < HVAL_THRESHOLD)
            value += poids[i] * heuristiques[i++].evaluer(j, pdvJoueur);
        return value;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}
