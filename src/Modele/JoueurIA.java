package Modele;
import IA.*;

public class JoueurIA extends Joueur {
    IA ia;

    public JoueurIA(int id) {
        super(id);
        this.ia = new IAAleatoire(this);
    }

    public JoueurIA(int id, IA.Difficulte difficulte) {
        super(id);
        switch (difficulte) {
            case ALEATOIRE:
                ia = new IAAleatoire(this);
                break;
            case FACILE:
                ia = new IAFacile(this);
                break;
            case MOYEN:
                ia = new IAMoyen(this);
                break;
            case DIFFICILE:
                ia = new IADifficile(this);
                break;
            default:
                throw new IllegalArgumentException("Difficulté inconnue : " + difficulte);
        }
    }

    public Coup jouer(Jeu j) {
        if (j.getJoueur().id != id)
            throw new IllegalArgumentException("Mauvais joueur courant pôur ce joueur : " + j.getJoueur().id);
        return ia.reflechir(j);
    }
}
