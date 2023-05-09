package IA;

import Modele.Coord;
import Modele.Jeu;
import Modele.Joueur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class H3 implements Heuristique {


    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        return 0;
    }

    public boolean pionseul (Jeu j, Coord pion){

        Set<Coord> accessible = new HashSet<>();
        accessible.add(pion);
        Iterator<Coord> iterator = accessible.iterator();
        while (iterator.hasNext()) {
            Coord element = iterator.next();
            for (Coord v : element.voisins()){
                if (v estpion)
                    {return false}
                if (v casebonne)
                    accessible.add(v);
            }
        }
        return true;
    }
}