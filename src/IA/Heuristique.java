package IA;
import Modele.Jeu;

public interface Heuristique {
    public int evaluer(Jeu j, int pdvJoueur);
}
