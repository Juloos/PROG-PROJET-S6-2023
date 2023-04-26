package Modele;

import Global.Config;
import java.util.ArrayList;

public abstract class Jeu {
    Plateau plateau;
    int scoreJ1, scoreJ2;      // Score des joueurs
    int tuilesJ1, tuilesJ2;    // Nombre de tuiles mangées
    int bloquesJ1, bloquesJ2;  // Nombre de pions bloqués
    int joueurCourant;
    ArrayList<Coord> pionsJ1, pionsJ2;  // Liste des pions des joueurs

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

    public boolean peutJouer(int joueur) {
        if (joueur == J1)
            return bloquesJ1 != pionsJ1.size();
        else
            return bloquesJ2 != pionsJ2.size();
    }

    public boolean estTermine() {
        return (tuilesJ1 + tuilesJ2) > 0 && !peutJouer(J1) && !peutJouer(J2);
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

    void manger(Coord c) {
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
            if (liste.isEmpty()) {
                if (estPionJ1(c))
                    bloquesJ1++;
                else
                    bloquesJ2++;
            }
            return liste;
        } else
            return null;
    }

    int joueurSuivant() {
        if (joueurCourant == J1)
            return peutJouer(J2) ? J2 : J1;
        else
            return peutJouer(J1) ? J1 : J2;
    }

    public void deplacerPion(Coord c1, Coord c2) {
        if ((!estPionJ1(c1) || joueurCourant != J1) && (!estPionJ2(c1) || joueurCourant != J2))
            throw new RuntimeException("Le pion ne correspond pas au joueur courant.");
        if (!deplacementsPion(c1).contains(c2))
            throw new RuntimeException("Déplacement impossible vers la destination " + c2 + ".");
        manger(c1);
        if (joueurCourant == J1) {
            pionsJ1.remove(c1);
            pionsJ1.add(c2);
        } else {
            pionsJ2.remove(c1);
            pionsJ2.add(c2);
        }
        joueurCourant = joueurSuivant();
    }

    public void ajouterPion(Coord c) {
        if (plateau.get(c) == Plateau.VIDE || estPionJ1(c) || estPionJ2(c))
            throw new RuntimeException("Impossible de placer le pion à l'emplacement " + c + ".");
        if (joueurCourant == J1) {
            if (pionsJ1.size() >= Config.NB_PIONS)
                throw new RuntimeException("J1 a déjà placé tout ses pions.");
            pionsJ1.add(c);
        } else {
            if (pionsJ2.size() >= Config.NB_PIONS)
                throw new RuntimeException("J2 a déjà placé tout ses pions.");
            pionsJ2.add(c);
        }
        joueurCourant = joueurSuivant();
    }

    public void terminerJoueur(int joueur) {
        if (peutJouer(joueur))
            return;
        if (joueur == J1)
            for (Coord c : pionsJ1)
                manger(c);
        else
            for (Coord c : pionsJ2)
                manger(c);
    }
}
