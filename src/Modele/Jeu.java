package Modele;

import java.util.ArrayList;
import java.util.Arrays;

import static Global.Config.*;

public abstract class Jeu {
    private final Plateau plateau;
    private final Joueur[] joueurs;
    int joueurCourant;

    public Jeu() {
        plateau = new Plateau();
        joueurs = new Joueur[NB_JOUEUR];
        for (int i = 0; i < NB_JOUEUR; i++)
            joueurs[i] = new Joueur(i);
        joueurCourant = 0;
    }

    public Joueur getJoueur(int id) {
        return joueurs[id];
    }

    public int getValeur(Coord c) {
        return plateau.get(c);
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean estJoueurValide(int joueur) {
        return joueur >= 0 && joueur < NB_JOUEUR;
    }

    public boolean peutJouer(int joueur) {
        return estJoueurValide(joueur) && joueurs[joueur].peutJouer();
    }

    public boolean peutJouer() {
        return joueurs[joueurCourant].peutJouer();
    }

    public boolean estTermine() {
        return Arrays.stream(joueurs).noneMatch(Joueur::peutJouer);
    }

    public boolean estPion(Coord c) {
        return Arrays.stream(joueurs).anyMatch(j -> j.estPion(c));
    }

    public int joueurDePion(Coord c) {
        for (int i = 0; i < NB_JOUEUR; i++)
            if (joueurs[i].estPion(c))
                return i;
        return -1;
    }

    void manger(Coord c) {
        joueurs[joueurCourant].ajouterTuile(plateau.get(c));
        plateau.set(c, Plateau.VIDE);
    }

    ArrayList<Coord> deplacementsPion(Coord c) {
        ArrayList<Coord> liste = new ArrayList<>();
        if (Arrays.stream(joueurs).anyMatch(j -> j.estPion(c))) {
            for (int dir = 0; dir < 6; dir++) {
                Coord curr = c.decale(dir);
                while (
                        curr.q >= 0 && curr.r >= 0 && curr.q < plateau.qMax && curr.r < plateau.rMax &&
                        plateau.get(curr) != Plateau.VIDE && !estPion(curr)) {
                    liste.add(curr);
                    curr = curr.decale(dir);
                }
            }
            if (liste.isEmpty())
                joueurs[joueurCourant].bloquerPion(c);
            return liste;
        } else
            return null;
    }

    void joueurSuivant() {
        do {
            joueurCourant = (joueurCourant + 1) % NB_JOUEUR;
        } while (!peutJouer());
    }

    public void deplacerPion(Coord c1, Coord c2) {
        if (!deplacementsPion(c1).contains(c2))
            throw new RuntimeException("Déplacement impossible vers la destination " + c2 + ".");
        manger(c1);
        joueurs[joueurCourant].deplacerPion(c1, c2);
        joueurSuivant();
    }

    public void ajouterPion(Coord c) {
        if (plateau.get(c) != 1 || Arrays.stream(joueurs).anyMatch(j -> j.estPion(c)))
            throw new RuntimeException("Impossible de placer le pion à l'emplacement " + c + ".");
        joueurs[joueurCourant].ajouterPion(c);
        joueurSuivant();
    }

    public void terminerJoueur(int joueur) {
        if (peutJouer(joueur))
            throw new IllegalArgumentException("Le joueur " + joueur + " peut encore jouer.");
        for (Coord c : joueurs[joueurCourant].getPions())
            manger(c);
        joueurSuivant();
    }

    public void jouer(Coup c) {
        if (!c.estJouable(this))
            throw new IllegalArgumentException("Ce coup n'est pas possible");
        c.jouer(this);
    }

    @Override
    public String toString() {
        // Même format que Plateau.toString() mais en mettant en couleur les pions des différents joueurs,
        // et une Ligne de score à la fin.
        StringBuilder str = new StringBuilder();
        for (int r = 0; r < plateau.rMax; r++) {
            if (r % 2 == 0)
                str.append(" ");
            for (int q = 0; q < plateau.qMax; q++) {
                Coord c = new Coord(q, r);
                str.append(COULEURS[joueurDePion(c) + 1]);
                str.append(plateau.get(c) != Plateau.VIDE ? plateau.get(c) : " ");
                str.append(" ");
            }
            str.append("\n");
        }
        str.append(COULEURS[0]);
        str.append("Score : ");
        for (int i = 0; i < NB_JOUEUR; i++) {
            str.append(COULEURS[i + 1]);
            str.append(joueurs[i].getScore());
            str.append(COULEURS[0]);
            str.append(" - ");
        }
        str.delete(str.length() - 3, str.length());
        return str.toString();
    }
}
