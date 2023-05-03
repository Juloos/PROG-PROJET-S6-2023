package Modele;

import Controleur.MoteurJeu;
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

    @Override
    public Coup reflechir(MoteurJeu mt) {
        if (mt.getJeu().getJoueur().id != id)
            throw new IllegalArgumentException("Mauvais joueur courant pôur ce joueur : " + mt.getJoueurActif().id);
        return ia.reflechir(mt.getJeu());
    }
}
