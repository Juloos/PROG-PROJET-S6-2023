package Modele.IA;

import Modele.Jeux.Jeu;
import static Global.Config.*;


// TODO: heuristique pour la phase d'ajouts des pions avec écartement des pions mais pas trop
// TODO: racler tout les points d'un ilot

public class Hmax implements Heuristique {
    Heuristique h1 = new H1();
    Heuristique h2 = new H2();
    Heuristique h3 = new H3();
    double[] poids = {69042, 1, 100};

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        double value = poids[0] * h1.evaluer(j, pdvJoueur);
        if (Math.abs(value) < HEURISTIQUE_ABSVAL)
            value += poids[1] * h2.evaluer(j, pdvJoueur);
        if (Math.abs(value) < HEURISTIQUE_ABSVAL)
            value += poids[2] * h3.evaluer(j, pdvJoueur);
        return value;
    }
}
