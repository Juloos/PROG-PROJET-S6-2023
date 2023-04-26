package Modele;

import java.util.ArrayList;

public class Jeu {
    Plateau plateau;
    int scoreJ1, scoreJ2;
    int tuilesJ1, tuilesJ2;
    int joueurCourant;
    ArrayList<Coord> pionsJ1, pionsJ2;

    public final static int J1 = 1;
    public final static int J2 = 2;

    public Jeu() {
        plateau = new Plateau();
        scoreJ1 = 0;
        scoreJ2 = 0;
        tuilesJ1 = 0;
        tuilesJ2 = 0;
        joueurCourant = J1;
        pionsJ1 = new ArrayList<>();
        pionsJ2 = new ArrayList<>();
    }

    public int getScoreJ1() {
        return scoreJ1;
    }

    public int getScoreJ2() {
        return scoreJ2;
    }

    public int getNbTuilesJ1() {
        return tuilesJ1;
    }

    public int getNbTuilesJ2() {
        return tuilesJ2;
    }

    public boolean estPionJ1(Coord c) {
        return pionsJ1.contains(c);
    }

    public boolean estPionJ2(Coord c) {
        return pionsJ2.contains(c);
    }

    public void manger(Coord c) {
        if (joueurCourant == J1) {
            scoreJ1 += plateau.get(c);
            tuilesJ1++;
        } else {
            scoreJ2 += plateau.get(c);
            tuilesJ2++;
        }
        plateau.set(c, Plateau.VIDE);
    }

    ArrayList<Coord> deplacementsPion(Coord c) {
        ArrayList<Coord> liste = new ArrayList<>();
        if (estPionJ1(c) || estPionJ2(c)) {
            for (int dir = 0; dir < 6; dir++) {
                Coord curr = c.decale(dir);
                while (plateau.get(curr) != Plateau.VIDE && curr.q > 0 && curr.r > 0 &&
                        curr.q <= plateau.qMax && curr.r <= plateau.rMax &&
                        !estPionJ1(curr) && !estPionJ2(curr)) {
                    liste.add(curr);
                    curr = curr.decale(dir);
                }
            }
            return liste;
        } else
            return null;
    }

    public boolean deplacer(Coord c1, Coord c2) {
        // Renvoie vrai si le pion à bien été déplacé de (q1, r1) à (q2, r2) ou non
        if ((!estPionJ1(c1) || joueurCourant != J1) && (!estPionJ2(c1) || joueurCourant != J2))
            return false;
        if (!deplacementsPion(c1).contains(c2))
            return false;
        manger(c1);
        if (joueurCourant == J1) {
            pionsJ1.remove(c1);
            pionsJ1.add(c2);
        } else {
            pionsJ2.remove(c1);
            pionsJ2.add(c2);
        }
        return true;
    }
}
