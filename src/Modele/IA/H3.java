package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuGraphe;

import java.util.ArrayList;
import java.util.Random;

import static Global.Config.*;

public class H3 implements Heuristique {
    Random rand = new Random();

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        JeuGraphe jg;
        double nbGagnant = 0;
        for (int i = 0; i < MONTE_CARLO_NB_SIMULE; i++) {
            jg = new JeuGraphe(j);
            ArrayList<Coup> coups = jg.coupsPossibles();
            while (!coups.isEmpty()) {
                jg.jouer(coups.get(rand.nextInt(coups.size())));
                coups = jg.coupsPossibles();
            }
            if (jg.getWinner().contains(pdvJoueur))
                nbGagnant++;
        }
        return 2 * nbGagnant / MONTE_CARLO_NB_SIMULE - 1;
    }
}
