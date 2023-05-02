package IHM;

import Modele.CoupAjout;
import Modele.CoupDeplacement;
import Modele.Jeu;
import Modele.Plateau;

public abstract class IHM {

    public abstract void AfficherPlateau(Jeu jeu);

    public abstract CoupAjout placerPingouin(int numJoueur);

    public abstract CoupDeplacement deplacerPingouin(int numJoueur);
}
