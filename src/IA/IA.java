package IA;
import Modele.*;

public interface IA {
    public Coup reflechir(Jeu j);

    public IA.Difficulte getDifficulte();

    public static enum Difficulte {
        ALEATOIRE, // identifiant : 0
        FACILE,    // identifiant : 1
        MOYEN,     // identifiant : 2
        DIFFICILE  // identifiant : 3
    }
}
