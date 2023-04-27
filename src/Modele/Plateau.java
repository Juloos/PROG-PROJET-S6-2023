package Modele;

import java.util.Random;
import static Global.Config.*;

public class Plateau {
    int[][] grille;
    int qMax, rMax;

    public final static int VIDE = 0;  // Après avoir mangé une case

    public Plateau() {
        qMax = TAILLE_PLATEAU_X;
        rMax = TAILLE_PLATEAU_Y;
        grille = new int[rMax][qMax];
        Random rand = new Random();
        int[] weights = new int[] {1, 1, 1, 2, 2, 3};
        Coord c = new Coord();
        for (c.q = 0; c.q < qMax; c.q++)
            for (c.r = 0; c.r < rMax; c.r++)
                set(c, weights[rand.nextInt(6)]);
    }

    public void set(Coord c, int val) {
        grille[c.r][c.q] = val;
    }

    public int get(Coord c) {
        return grille[c.r][c.q];
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Coord c = new Coord();
        for (c.r = 0; c.r < rMax; c.r++) {
            for (c.q = 0; c.q < qMax; c.q++)
                str.append(get(c)).append(" ");
            str.append("\n");
        }
        return str.toString();
    }
}
