package Modele;

import Controleur.MoteurJeu;
import IA.*;

import java.util.HashMap;

public class JoueurIA extends Joueur {
    IA ia;

    public JoueurIA(int id) {
        super(id);
        this.ia = new IAAleatoire(this);
    }
    public JoueurIA(int id, int score, int tuiles, HashMap<Coord,Boolean> pions, boolean termine, IA.Difficulte difficulte){
        super(id, score, tuiles, pions, termine);
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

    @Override
    public String toString() {
        String dataHash = "";
        Coord[] tempL = pions.keySet().toArray(new Coord[pions.size()]);
        for (int j = 0; j < pions.size(); j++) {
            dataHash += " " + tempL[j].q + " " + tempL[j].r;
        }
        return id + " " + 1 + " " + ia.getDifficulte() + " " + score + " " + tuiles + " " + pions.size() + dataHash;
    }

    @Override
    public JoueurIA clone() {
        JoueurIA j = new JoueurIA(id);
        j.score = score;
        j.tuiles = tuiles;
        j.termine = termine;
        j.ia = ia;
        return j;
    }
}
