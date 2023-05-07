package Modele.IA;

import Modele.Jeux.Jeu;

public interface Heuristique {
    public double evaluer(Jeu j, int pdvJoueur);
}
