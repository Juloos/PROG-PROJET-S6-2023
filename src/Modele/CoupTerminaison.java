package Modele;

import java.util.HashMap;

public class CoupTerminaison implements Coup {
    HashMap<Coord, Integer> oldVals;
    int joueur;

    public CoupTerminaison(int joueur) {
        this.oldVals = new HashMap<>();
        this.joueur = joueur;
    }

    public void jouer(Jeu j) {
        for (Coord key : j.getJoueur(joueur).getPions())
            oldVals.put(key, j.getValeur(key));
        j.terminerJoueur();
    }

    public boolean estJouable(Jeu j) {
        return !j.peutJouer(joueur);
    }

    public int getJoueur() {
        return joueur;
    }
}
