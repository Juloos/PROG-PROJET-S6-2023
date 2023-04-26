package Modele;

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
        return new Coord(q, r - 1);
    }

    public Coord decaleHautDroite() {
        return new Coord(q + 1, r - 1);
    }

    public Coord decaleDroite() {
        return new Coord(q + 1, r);
    }

    public Coord decaleBasDroite() {
        return new Coord(q + 1, r + 1);
    }

    public Coord decaleBasGauche() {
        return new Coord(q, r + 1);
    }

    public Coord decaleGauche() {
        return new Coord(q - 1, r);
    }

    public Coord decale(int dir) {
        switch (dir) {
            case HAUT_GAUCHE: return decaleHautGauche();
            case HAUT_DROITE: return decaleHautDroite();
            case DROITE: return decaleDroite();
            case BAS_DROITE: return decaleBasDroite();
            case BAS_GAUCHE: return decaleBasGauche();
            case GAUCHE: return decaleGauche();
            default: return this;
        }
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
}
