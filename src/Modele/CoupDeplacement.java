package Modele;

public class CoupDeplacement implements Coup {
    Coord source;
    Coord destination;
    int oldVal;
    int joueur;

    public CoupDeplacement(Coord source, Coord destination, int joueur) {
        this.source = source;
        this.destination = destination;
        this.oldVal = Plateau.VIDE;
        this.joueur = joueur;
    }

    public void jouer(Jeu j) {
        oldVal = j.getPlateau().get(source);
        j.deplacerPion(source, destination);
    }

    public boolean estJouable(Jeu j) {
        return j.peutJouer(joueur) && j.getJoueur(joueur).estPion(source) && j.deplacementsPion(source).contains(destination);
    }

    public void annuler(Jeu j) {
        j.getPlateau().set(source, oldVal);
        j.getJoueur(joueur).deplacerPion(destination, source);
    }

    public int getJoueur() {
        return joueur;
    }

    @Override
    public String toString() {
        return "CoupDeplacement{" + "source=" + source + ", destination=" + destination + ", oldVal=" + oldVal + ", joueur=" + joueur + '}';
    }
}
