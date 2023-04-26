package Modele;

import Global.Config;
import java.util.Random;

public class Plateau {
    int[][] grille;
    int qMax, rMax;

    public final static int VIDE = 0;  // Après avoir mangé une case

    public Plateau() {
        grille = new int[Config.TAILLE_PLATEAU_Y][Config.TAILLE_PLATEAU_X];
        qMax = Config.TAILLE_PLATEAU_X;
        rMax = Config.TAILLE_PLATEAU_Y;
        Random rand = new Random();
        int[] weights = new int[] {1, 1, 1, 2, 2, 3};
        Coord c = new Coord();
        for (c.q = 0; c.q < qMax; c.q++)
            for (c.r = 0; c.r < rMax; c.r++)
                set(c, weights[rand.nextInt(7) - 1]);
    }

    public void set(Coord c, int val) {
        grille[c.r][c.q] = val;
    }

    public int get(Coord c) {
        return grille[c.r][c.q];
    }
}
