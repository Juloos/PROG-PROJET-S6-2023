package Modele.Joueurs;

import Controleur.MoteurJeu;
import Modele.Actions.Action;
import Modele.Actions.ActionCoup;
import Modele.Coord;
import Modele.IA.*;

import java.util.HashMap;

public class JoueurIA extends Joueur {
    IA ia;

    public JoueurIA(int id) {
        super(id);
        this.ia = new IAAleatoire(this);
    }

    public JoueurIA(int id, IA.Difficulte d) {
        super(id);
        assignerIA(d);
    }

    public JoueurIA(int id, int score, int tuiles, HashMap<Coord, Boolean> pions, boolean termine, IA.Difficulte d) {
        super(id, score, tuiles, pions, termine);
        assignerIA(d);
    }

    private void assignerIA(IA.Difficulte d) {
        switch (d) {
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
                throw new IllegalArgumentException("Difficulté inconnue : " + d);
        }
    }

    @Override
    public Action reflechir(MoteurJeu mt) {
        if (mt.getJeu().getJoueur().id != id)
            throw new IllegalArgumentException("Mauvais joueur courant pôur ce joueur : " + mt.getJoueurActif().id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ActionCoup(ia.reflechir(mt.getJeu()));
    }

    @Override
    public JoueurIA clone() {
        return new JoueurIA(id, score, tuiles, new HashMap<>(pions), termine, ia.getDifficulte());
    }

    @Override
    public String toString() {
        String dataHash = "";
        Coord[] tempL = pions.keySet().toArray(new Coord[pions.size()]);
        for (int j = 0; j < pions.size(); j++) {
            dataHash += " " + tempL[j].q + " " + tempL[j].r;
        }
        return id + " " + 2 + " " + ia.getDifficulte() + " " + score + " " + tuiles + " " + pions.size() + dataHash;
    }
}