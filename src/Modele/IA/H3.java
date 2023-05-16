package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuGraphe;

import java.util.ArrayList;
import java.util.Random;

public class H3 implements Heuristique {
    int nbAppels = 0;

    Random rand = new Random();

    final int NB_SIMULE;

    public H3() {
        this(100);
    }

    public H3(int nb_simulations) {
        NB_SIMULE = nb_simulations;
    }

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        JeuGraphe jg;
        double nbGagnant = 0;
        for (int i = 0; i < NB_SIMULE; i++) {
            jg = new JeuGraphe(j);
            ArrayList<Coup> coups;
            while (!(coups = jg.coupsPossibles()).isEmpty())
                jg.jouer(coups.get(rand.nextInt(coups.size())));
            if (jg.getWinner().contains(pdvJoueur))
                nbGagnant++;
        }
        return 2 * nbGagnant / NB_SIMULE - 1;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}
