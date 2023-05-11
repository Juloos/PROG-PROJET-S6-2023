package IA;

import Modele.Coord;
import Modele.Jeu;

public class H_debut_3 implements Heuristique {
        @Override
        public double evaluer(Jeu j, int pdvJoueur) {

            for (Coord c : j.getJoueur(pdvJoueur).getPions()) {
                if (c.q == j.getPlateau().getNbColumns() || c.r == j.getPlateau().getNbRows() || c.q == 0 || c.r ==0)
                    return -1.0;
            }
            return 0.0;
        }
    }