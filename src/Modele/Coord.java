package Modele;

import java.util.Arrays;
import java.util.ArrayList;

public class Coord {
    public int q, r;

    public final static int HAUT_GAUCHE = 0;
    public final static int HAUT_DROITE = 1;
    public final static int DROITE = 2;
    public final static int BAS_DROITE = 3;
    public final static int BAS_GAUCHE = 4;
    public final static int GAUCHE = 5;

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
        if(dq != 0) {
            dq /= Math.abs(dq);
        }

        int dr = cible.r - r;
        if(dr != 0) {
            dr /= Math.abs(dr);
        }

        System.out.println("dq : " + dq + ", dr : " + dr);

        if(dr == 0) {
            if(dq > 0) {
                return DROITE;
            } else if(dq < 0) {
                return GAUCHE;
            } else {
                return -1;
            }
        } else if(dr < 0) {
            if(dq > 0) {
                return HAUT_DROITE;
            } else if(dq < 0) {
                return HAUT_GAUCHE;
            }
        } else {
            if(dq > 0) {
                return BAS_DROITE;
            } else if(dq < 0) {
                return BAS_GAUCHE;
            }
        }

        return -1;
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
