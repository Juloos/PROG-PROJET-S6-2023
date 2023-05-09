package Modele.Coups;

import Modele.Coord;
import Modele.Jeux.Jeu;
import Modele.Plateau;

public class CoupDeplacement implements Coup {
    public final Coord source, destination;
    int oldVal;
    int joueur;

    public CoupDeplacement(Coord source, Coord destination, int joueur) {
        this.source = source;
        this.destination = destination;
        this.oldVal = Plateau.VIDE;
        this.joueur = joueur;
    }

    public CoupDeplacement(Coord source, Coord destination, int oldVal, int joueur) {
        this.source = source;
        this.destination = destination;
        this.oldVal = oldVal;
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
        j.annulerDeplacer(joueur, destination, source);
        j.getJoueur(joueur).decrementerScore(oldVal);
        j.getJoueur(joueur).supprimerTuile();
    }

    public int getJoueur() {
        return joueur;
    }

    public Coord getSource() {
        return source;
    }

    public Coord getDestination() {
        return destination;
    }

    public String getSaveString() {
        return "-2 " + joueur + " " + source.q + " " + source.r + " " + destination.q + " " + destination.r + " " + oldVal;
    }

    @Override
    public String toString() {
        return "CoupDeplacement{" + "source=" + source + ", destination=" + destination + ", oldVal=" + oldVal + ", joueur=" + joueur + '}';
    }

    @Override
    public boolean equals(Object c) {
        if (c instanceof CoupDeplacement) {
            CoupDeplacement cd = (CoupDeplacement) c;
            return cd.source.equals(source) && cd.destination.equals(destination) && cd.oldVal == oldVal && cd.joueur == joueur;
        }
        return false;
    }
}
