package Modele;

import java.util.ArrayList;
import java.util.Arrays;

public class Coord {
    public final static int HAUT_GAUCHE = 0;
    public final static int HAUT_DROITE = 1;
    public final static int DROITE = 2;
    public final static int BAS_DROITE = 3;
    public final static int BAS_GAUCHE = 4;
    public final static int GAUCHE = 5;
    public int q, r;

    public Coord() {
        this.q = 0;
        this.r = 0;
    }

    public Coord(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public Coord(int[] coord) {
        this.q = coord[0];
        this.r = coord[1];
    }

    public Coord(Integer[] coord) {
        this.q = coord[0];
        this.r = coord[1];
    }

    public static ArrayList<Coord> getCoordsEntre(Coord source, Coord cible) {
        ArrayList<Coord> coords = new ArrayList<>();
        int decalage = source.getDecalage(cible);
        Coord current = source.clone();

        while (!current.equals(cible)) {
            coords.add(current);
            System.out.println(current);
            current = current.decale(decalage);
        }
        coords.add(cible);
        return coords;
    }

    public Coord decaleHautGauche() {
        return new Coord(q - (r % 2), r - 1);
    }

    public Coord decaleHautDroite() {
        return new Coord(q + 1 - (r % 2), r - 1);
    }

    public Coord decaleDroite() {
        return new Coord(q + 1, r);
    }

    public Coord decaleBasDroite() {
        return new Coord(q + 1 - (r % 2), r + 1);
    }

    public Coord decaleBasGauche() {
        return new Coord(q - (r % 2), r + 1);
    }

    public Coord decaleGauche() {
        return new Coord(q - 1, r);
    }

    public Coord decale(int dir) {
        switch (dir) {
            case HAUT_GAUCHE:
                return decaleHautGauche();
            case HAUT_DROITE:
                return decaleHautDroite();
            case DROITE:
                return decaleDroite();
            case BAS_DROITE:
                return decaleBasDroite();
            case BAS_GAUCHE:
                return decaleBasGauche();
            case GAUCHE:
                return decaleGauche();
            default:
                return this;
        }
    }

    public int getDecalage(Coord cible) {
        int dq = cible.q - q;
        if (dq != 0) {
            dq /= Math.abs(dq);
        }

        int dr = cible.r - r;
        if (dr != 0) {
            dr /= Math.abs(dr);
        }

        if (dr == 0 && dq == 0) {
            return -1;
        }

        if (r % 2 > 0) {
            // Pour les lignes impaires
            if (dr == 0) {
                return dq > 0 ? DROITE : GAUCHE;
            } else if (dr > 0) {
                return dq >= 0 ? BAS_DROITE : BAS_GAUCHE;
            } else {
                return dq >= 0 ? HAUT_DROITE : HAUT_GAUCHE;
            }
        } else {
            // Pour les lignes paires
            if (dr == 0) {
                return dq > 0 ? DROITE : GAUCHE;
            } else if (dr > 0) {
                return dq > 0 ? BAS_DROITE : BAS_GAUCHE;
            } else {
                return dq > 0 ? HAUT_DROITE : HAUT_GAUCHE;
            }
        }
    }

    public ArrayList<Coord> voisins() {
        ArrayList<Coord> voisins = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            voisins.add(decale(i));
        return voisins;
    }

    @Override
    public Coord clone() {
        return new Coord(q, r);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coord))
            return false;
        Coord c = (Coord) o;
        return q == c.q && r == c.r;
    }

    @Override
    public String toString() {
        return "(" + q + ", " + r + ")";
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{q, r});
    }
}
