package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeu.Jeu;

public interface IA {
    public Coup reflechir(Jeu j);

    public IA.Difficulte getDifficulte();

    public static enum Difficulte {
        ALEATOIRE,
        FACILE,
        MOYEN,
        DIFFICILE
    }
}
