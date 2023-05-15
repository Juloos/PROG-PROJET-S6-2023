package Modele.IA;

import Modele.Coord;
import Modele.Jeux.Jeu;
import Modele.Plateau;

public class H7 implements Heuristique {
    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        double valeur = 0;
        for (Coord pion : j.getJoueur(pdvJoueur).getPions())
            valeur += nbVoisinLibre(j, pion);
        return 2 * Math.sqrt(valeur / (j.getJoueur(pdvJoueur).getPions().size() * 6)) - 1;
    }

    private int nbVoisinLibre(Jeu j, Coord c) {
        int nbVoisins = 0;
        for (Coord v : c.voisins())
            if (j.getPlateau().get(v) != Plateau.VIDE && !j.estPion(v))
                nbVoisins++;
        return nbVoisins;
    }
}
