package Modele.IA;

import Modele.Coord;
import Modele.Jeux.Jeu;

public class H6 implements Heuristique {
    int nbAppels = 0;

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        int nb3Poissons = 0;
        for (Coord pion : j.getJoueur(pdvJoueur).getPions())
            for (Coord c : j.deplacementsPion(pion))
                nb3Poissons += (j.getPlateau().get(c) == 3) ? 1 : 0;
        return nb3Poissons;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}