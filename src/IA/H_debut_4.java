package IA;

import Modele.Coord;
import Modele.Jeu;

public class H_debut_4 implements Heuristique {
        @Override
        public double evaluer(Jeu j, int pdvJoueur) {
            Coord c = j.getJoueur(pdvJoueur).getPions().stream().findFirst().orElse(null);
            int ecart = 0;
            if (c!=null) {
                for (Coord autres : j.getJoueur(pdvJoueur).getPions()) {
                    ecart = ecart + Math.abs(c.q - autres.q) + Math.abs(c.r - autres.r);
                }
            }
            return Double.valueOf(ecart);
        }

    }