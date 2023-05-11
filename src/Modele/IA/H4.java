package Modele.IA;

import Modele.Coord;
import Modele.Jeux.Jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class H4 implements Heuristique {
    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        return 0;
    }

    public boolean pionseul (Jeu j, Coord pion){

        Set<Coord> accessible = new HashSet<>();
        accessible.add(pion);
        for (Coord element : accessible) {
            for (Coord v : element.voisins()) {
                if (j.estPion(v))
                    return false;
                if (j.getPlateau().estCoordValide(v))
                    accessible.add(v);
            }
        }
        return true;
    }
}