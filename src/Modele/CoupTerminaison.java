package Modele;

import java.util.HashMap;

public class CoupTerminaison implements Coup {
    HashMap<Coord, Integer> oldVals;
    int joueur;

    public CoupTerminaison(int joueur) {
        this.oldVals = new HashMap<>();
        this.joueur = joueur;
    }

    public CoupTerminaison(HashMap<Coord, Integer> oldVals, int joueur) {
        this.oldVals = oldVals;
        this.joueur = joueur;
    }

    public void jouer(Jeu j) {
        for (Coord key : j.getJoueur(joueur).getPions())
            oldVals.put(key, j.getPlateau().get(key));
        j.terminerJoueur();
    }

    public boolean estJouable(Jeu j) {
        return !j.peutJouer(joueur);
    }

    public void annuler(Jeu j) {
        oldVals.forEach(
                (source, oldVal) -> {
                    j.getPlateau().set(source, oldVal);
                    j.getJoueur(joueur).ajouterPion(source);
                }
        );
    }

    public int getJoueur() {
        return joueur;
    }

    public String getSaveString() {
        StringBuilder save = new StringBuilder();
        final String[] temp = new String[1];
        oldVals.forEach(
                (source, oldVal) -> {
                    temp[0] = " " + source.q + " " + source.r + " " + oldVal;
                    save.append(temp[0]);
                }
        );
        return "-3 " + joueur + save;
    }

    @Override
    public String toString() {
        return "CoupTerminaison{" + "oldVals=" + oldVals + ", joueur=" + joueur + '}';
    }

    @Override
    public boolean equals(Object c) {
        if (c instanceof CoupTerminaison) {
            CoupTerminaison ct = (CoupTerminaison) c;
            return ct.oldVals.equals(oldVals) && ct.joueur == joueur;
        }
        return false;
    }
}
