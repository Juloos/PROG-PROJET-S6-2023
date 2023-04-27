package Modele;

import java.util.HashMap;

public class Coup {
    public enum Type {
        AJOUT,
        DEPLACEMENT,
        TERMINAISON
    }

    Type type;
    Coord source;
    Coord cible;
    HashMap<Coord, Integer> oldVals;
    int joueur;

    public Coup(Coord cible, int joueur) {
        this.type = Type.AJOUT;
        this.cible = cible;
        this.joueur = joueur;
    }

    public Coup(Coord source, Coord cible, int joueur) {
        this.type = Type.DEPLACEMENT;
        this.source = source;
        this.cible = cible;
        this.oldVals = new HashMap<>();
        this.joueur = joueur;
    }

    public Coup(int joueur) {
        this.type = Type.TERMINAISON;
        this.oldVals = new HashMap<>();
        this.joueur = joueur;
    }

    public void putOldVal(Coord c, int val) {
        oldVals.put(c, val);
    }

    public int getOldVal(Coord c) {
        return oldVals.get(c);
    }
}
