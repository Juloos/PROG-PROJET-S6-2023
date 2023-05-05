package IA;
import Modele.Jeu;

public interface Heuristique {
    public double evaluer(Jeu j, int pdvJoueur);
}
