package Modele;

import Global.Config;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Jeu {
    Plateau plateau;
    Joueur[] joueurs;

    int joueurCourant;

    public final static int J1 = 0;
    public final static int J2 = 1;

    public Jeu() {
        plateau = new Plateau();
        joueurs = new Joueur[Config.NB_JOUEUR];
        for (int i = 0; i < Config.NB_JOUEUR; i++)
            joueurs[i] = new Joueur();
        joueurCourant = J1;
    }

    public boolean peutJouer(int joueur) {
        if (joueur == J1)
            return joueurs[J1].peutJouer();
        else
            return joueurs[J2].peutJouer();
    }

    public boolean estTermine() {
        return !peutJouer(J1) && !peutJouer(J2);
    }

    void manger(Coord c) {
        if (joueurCourant == J1) {
            joueurs[J1].score += plateau.get(c);
            joueurs[J1].tuiles++;
        } else {
            joueurs[J2].score += plateau.get(c);
            joueurs[J2].tuiles++;
        }
        plateau.set(c, Plateau.VIDE);
    }

    ArrayList<Coord> deplacementsPion(Coord c) {
        ArrayList<Coord> liste = new ArrayList<>();
        if (joueurs[J1].estPion(c) || joueurs[J2].estPion(c)) {
            for (int dir = 0; dir < 6; dir++) {
                Coord curr = c.decale(dir);
                while (plateau.get(curr) != Plateau.VIDE && curr.q > 0 && curr.r > 0 &&
                        curr.q <= plateau.qMax && curr.r <= plateau.rMax &&
                        !joueurs[J1].estPion(curr) && !joueurs[J2].estPion(curr)) {
                    liste.add(curr);
                    curr = curr.decale(dir);
                }
            }
            if (liste.isEmpty()) {
                if (joueurs[J1].estPion(c))
                    joueurs[J1].pions.put(c, true);
                else
                    joueurs[J2].pions.put(c, true);
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
        if ((!joueurs[J1].estPion(c1) || joueurCourant != J1) && (!joueurs[J1].estPion(c1) || joueurCourant != J2))
            throw new RuntimeException("Le pion ne correspond pas au joueur courant.");
        if (!deplacementsPion(c1).contains(c2))
            throw new RuntimeException("Déplacement impossible vers la destination " + c2 + ".");
        manger(c1);
        if (joueurCourant == J1) {
            joueurs[J1].pions.remove(c1);
            joueurs[J1].pions.put(c2, false);
        } else {
            joueurs[J2].pions.remove(c1);
            joueurs[J2].pions.put(c2, false);
        }
        joueurCourant = joueurSuivant();
    }

    public void ajouterPion(Coord c) {
        if (plateau.get(c) == Plateau.VIDE || joueurs[J1].estPion(c) || joueurs[J2].estPion(c))
            throw new RuntimeException("Impossible de placer le pion à l'emplacement " + c + ".");
        if (joueurCourant == J1) {
            if (joueurs[J1].pions.size() >= Config.NB_PIONS)
                throw new RuntimeException("J1 a déjà placé tout ses pions.");
            joueurs[J1].pions.put(c,false);
        } else {
            if (joueurs[J2].pions.size() >= Config.NB_PIONS)
                throw new RuntimeException("J1 a déjà placé tout ses pions.");
            joueurs[J2].pions.put(c,false);
        }
        joueurCourant = joueurSuivant();
    }

    public void terminerJoueur(int joueur) {
        if (peutJouer(joueur))
            return;

        if (joueur == J1){
            for (Coord c : joueurs[J1].pions.keySet())
                manger(c);
        }else {
            for (Coord c : joueurs[J2].pions.keySet())
                manger(c);
        }
        joueurCourant = joueurSuivant();
    }
}
