package Modele;

import java.util.HashMap;

public class CoupTerminaison implements Coup {
    HashMap<Coord, Integer> oldCase;
    int joueur;

    public CoupTerminaison(int joueur) {
        this.oldCase = new HashMap<>();
        this.joueur = joueur;
    }

    public void jouer(Jeu j) {
        for (Coord key : j.getJoueur(joueur).getPions())
            oldCase.put(key, j.getValeur(key));
        j.terminerJoueur();
    }

    public boolean estJouable(Jeu j) {
        return !j.peutJouer(joueur);
    }

    public void annuler(Jeu j){
        oldCase.forEach(
            (source, oldVal) -> {
                j.getPlateau().set(source, oldVal);
                j.getJoueur(joueur).ajouterPion(source);
            }
        );
    }

    public int getJoueur() {
        return joueur;
    }

    @Override
    public String toString() {
        return "CoupTerminaison{" + "oldVals=" + oldCase + ", joueur=" + joueur + '}';
    }
}
