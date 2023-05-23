package Modele.IA;

import Modele.Jeux.Jeu;

import static Global.Config.*;


public class H11 implements Heuristique {
    int nbAppels = 0;
    Heuristique[] heuristiques = {new H1(), new H2(), new H9(), new H7()};
    double[] poids = {Double.MAX_VALUE, 0.001, 1, 2.15};

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        int i = 0;
        double value = 0;
        while (i < heuristiques.length) {
            value += poids[i] * heuristiques[i].evaluer(j, pdvJoueur);
            i++;
        }
        return value;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}
