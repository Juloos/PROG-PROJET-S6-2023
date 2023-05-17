package Modele.IA;

import Modele.Jeux.Jeu;

import java.util.ArrayList;

public class H1 implements Heuristique {
    int nbAppels = 0;

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        if (j.estTermine()) {
            ArrayList<Integer> winners = j.getWinner();
            if (winners.contains(pdvJoueur))
                return winners.size() == 1 ? 1 : 0;
            else
                return -1;
        } else
            return 0;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}
