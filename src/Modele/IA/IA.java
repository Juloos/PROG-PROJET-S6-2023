package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;

public interface IA {
    Coup reflechir(Jeu j);

    IA.Difficulte getDifficulte();

    enum Difficulte {
        ALEATOIRE, // identifiant : 0
        FACILE,    // identifiant : 1
        MOYEN,     // identifiant : 2
        DIFFICILE  // identifiant : 3
    }

    static IA getIA(Difficulte d, int joueur) {
        switch (d) {
            case ALEATOIRE:
                return new IAAleatoire(joueur);
            case FACILE:
                return new IAFacile(joueur);
            case MOYEN:
                return new IAMoyen(joueur);
            case DIFFICILE:
                return new IADifficile(joueur);
            default:
                return null;
        }
    }
}
