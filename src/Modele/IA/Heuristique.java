package Modele.IA;

import Modele.Jeux.Jeu;

public interface Heuristique {
    double evaluer(Jeu j, int pdvJoueur);
    int getNbAppels();
}
