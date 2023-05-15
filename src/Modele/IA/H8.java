package Modele.IA;

import Modele.Coord;
import Modele.Jeux.Jeu;

public class H8 implements Heuristique {
    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        double ecart = 0;
        for (Coord pion : j.getJoueur(pdvJoueur).getPions())
            for (Coord autrePion : j.getJoueur(pdvJoueur).getPions())
                ecart += Math.abs(pion.q - autrePion.q) + Math.abs(pion.q + pion.r - autrePion.q - autrePion.r) + Math.abs(pion.r - autrePion.r);
        return ecart;
    }
}
