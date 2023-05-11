package IA;

import Modele.Coord;
import Modele.Jeu;

public class H_debut_1 implements Heuristique {
    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        int nbrpoisson = 0;
        for (Coord c : j.getJoueur(pdvJoueur).getPions()) {
            for (Coord co : j.deplacementsPion(c)) {
                nbrpoisson = nbrpoisson + j.getPlateau().get(co);
            }
        }
        return Double.valueOf(nbrpoisson);
    }
}
