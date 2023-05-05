package Modele.IA;

import Modele.Jeu.Jeu;

public interface Heuristique {
    public double evaluer(Jeu j, int pdvJoueur);
}
