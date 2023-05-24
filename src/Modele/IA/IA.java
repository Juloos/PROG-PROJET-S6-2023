package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;

public interface IA {
    Coup reflechir(Jeu j);

    IA.Difficulte getDifficulte();

    enum Difficulte {
        ALEATOIRE,  // identifiant : 0
        FACILE,     // identifiant : 1
        MOYEN,      // identifiant : 2
        DIFFICILE,  // identifiant : 3
        LEGENDAIRE  // identifiant : 4
    }

    static  Difficulte stringToDiff(String nom) {
        switch (nom) {
            case "ALEATOIRE":
                return IA.Difficulte.ALEATOIRE;
            case "FACILE":
                return IA.Difficulte.FACILE;
            case "MOYEN":
                return IA.Difficulte.MOYEN;
            case "DIFFICILE":
                return IA.Difficulte.DIFFICILE;
            case "LEGENDAIRE":
                return IA.Difficulte.LEGENDAIRE;
            default:
                return null;
        }
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
            case LEGENDAIRE:
                return new IALegendaire(joueur);
            default:
                return null;
        }
    }
}
