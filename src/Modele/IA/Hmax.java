package Modele.IA;

import Modele.Jeux.Jeu;


public class Hmax implements Heuristique {
    int nbAppels = 0;
    Heuristique[] heuristiques = {new H1(), new H2(), new H9(), new H7(), new H4(), new H5(), new H6(), new H8(), new H10()};
    double[] poids;

    public Hmax() {
        this.poids = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1};
    }

    public Hmax(double[] poids) {
        this.poids = poids;
    }

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        int i = 0;
        double value = 0;
        while (i < heuristiques.length)
            value += poids[i] * heuristiques[i++].evaluer(j, pdvJoueur);
        return value;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}
