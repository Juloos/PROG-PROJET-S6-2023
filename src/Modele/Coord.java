package Modele;

public class Coord {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coord c))
            return false;
        return q == c.q && r == c.r;
    }
}
