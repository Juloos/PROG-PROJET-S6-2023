package IA;

import Modele.Jeu;

import java.util.ArrayList;

public class H1 implements Heuristique {
    @Override
    public int evaluer(Jeu j, int pdvJoueur) {
        if (j.estTermine()) {
            ArrayList<Integer> winners = j.getWinner();
            if (winners.contains(pdvJoueur))
                return winners.size() == 1 ? 1 : 0;
            else
                return -1;
        } else
            return 0;
    }
}
