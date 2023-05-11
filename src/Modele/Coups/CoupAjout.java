package Modele.Coups;

import Modele.Coord;
import Modele.Jeux.Jeu;

public class CoupAjout implements Coup {
    Coord cible;
    int joueur;

    public CoupAjout(Coord cible, int joueur) {
        this.cible = cible;
        this.joueur = joueur;
    }

    public void jouer(Jeu j) {
        j.ajouterPion(cible);
    }

    public boolean estJouable(Jeu j) {
        return j.getPlateau().get(cible) == 1 && !j.estPion(cible) && j.getJoueur(joueur).getPions().size() < j.getNbPions();
    }

    public void annuler(Jeu j) {
        j.annulerAjout(joueur,cible);
    }

    public int getJoueur() {
        return joueur;
    }

    public String getSaveString() {
        return "-1 " + joueur + " " + cible.q + " " + cible.r;
    }

    @Override
    public String toString() {
        return "CoupAjout{" + "cible=" + cible + ", joueur=" + joueur + '}';
    }

    @Override
    public boolean equals(Object c) {
        if (c instanceof CoupAjout) {
            CoupAjout ca = (CoupAjout) c;
            return ca.cible.equals(cible) && ca.joueur == joueur;
        }
        return false;
    }
}
