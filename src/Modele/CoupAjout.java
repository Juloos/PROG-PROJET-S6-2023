package Modele;

import static Global.Config.NB_PIONS;

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
        return j.getValeur(cible) == 1 && !j.estPion(cible) && j.getJoueur(joueur).getPions().size() < NB_PIONS;
    }

    public void annuler(Jeu j){
        j.getJoueur(joueur).supprimerPion(cible);
    }

    public int getJoueur() {
        return joueur;
    }

    @Override
    public String toString() {
        return "CoupAjout{" + "cible=" + cible + ", joueur=" + joueur + '}';
    }
}
