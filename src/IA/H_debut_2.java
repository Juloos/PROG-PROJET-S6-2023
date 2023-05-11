package IA;

import Modele.Coord;
import Modele.Jeu;

public class H_debut_2 implements Heuristique {
        @Override
        public double evaluer(Jeu j, int pdvJoueur) {
            int nbrcase3poisson = 0;
            for (Coord c : j.getJoueur(pdvJoueur).getPions()) {
                for (Coord co : j.deplacementsPion(c)) {
                    if (j.getPlateau().get(co) == 3) {
                        nbrcase3poisson = nbrcase3poisson + 1;
                    }
                }
            }
            return Double.valueOf(nbrcase3poisson);
        }

    }