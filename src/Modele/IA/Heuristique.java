package Modele.IA;

import Modele.Jeu.Jeu;

public interface Heuristique {
    public int evaluer(Jeu j, int pdvJoueur);
}
