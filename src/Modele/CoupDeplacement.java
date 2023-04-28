package Modele;

public class CoupDeplacement implements Coup {
    Coord source;
    Coord cible;
    int oldVal;
    int joueur;

    public CoupDeplacement(Coord source, Coord cible, int joueur) {
        this.source = source;
        this.cible = cible;
        this.oldVal = Plateau.VIDE;
        this.joueur = joueur;
    }

    public void jouer(Jeu j) {
        oldVal = j.getValeur(source);
        j.deplacerPion(source, cible);
    }

    public boolean estJouable(Jeu j) {
        return j.peutJouer(joueur) && j.getJoueur(joueur).estPion(source) && j.deplacementsPion(source).contains(cible);
    }

    public void annuler(Jeu j){
        j.getPlateau().set(source,oldVal);
        j.getJoueur(joueur).deplacerPion(cible,source);
    }

    public int getJoueur() {
        return joueur;
    }

    @Override
    public String toString() {
        return "CoupDeplacement{" + "source=" + source + ", cible=" + cible + ", oldVal=" + oldVal + ", joueur=" + joueur + '}';
    }
}
