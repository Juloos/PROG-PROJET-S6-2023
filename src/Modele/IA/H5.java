package Modele.IA;

import Modele.Coord;
import Modele.Jeux.Jeu;

public class H5 implements Heuristique {
    int nbAppels = 0;

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        int nbPoissons = 0;
        for (Coord pion : j.getJoueur(pdvJoueur).getPions())
            for (Coord c : j.deplacementsPion(pion))
                nbPoissons += j.getPlateau().get(c);
        return nbPoissons;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}
