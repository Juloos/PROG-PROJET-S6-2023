package IA;
import Modele.*;

public interface IA {
    public Coup reflechir(Jeu j);

    public static enum Difficulte {
        ALEATOIRE,
        FACILE,
        MOYEN,
        DIFFICILE
    }
}