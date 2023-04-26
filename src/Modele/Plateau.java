package Modele;

import Global.Config;
import java.util.ArrayList;
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
        for (int q = 0; q < qMax; q++)
            for (int r = 0; r < rMax; r++)
                set(q, r, weights[rand.nextInt(0, 6)]);
    }

    public void set(int q, int r, int val) {
        grille[r][q] = val;
    }

    public int get(int q, int r) {
        return grille[r][q];
    }

    public static int[] decaleHautGauche(int q, int r) {
        return new int[] {q, r - 1};
    }

    public static int[] decaleHautDroite(int q, int r) {
        return new int[] {q + 1, r - 1};
    }

    public static int[] decaleDroite(int q, int r) {
        return new int[] {q + 1, r};
    }

    public static int[] decaleBasDroite(int q, int r) {
        return new int[] {q + 1, r + 1};
    }

    public static int[] decaleBasGauche(int q, int r) {
        return new int[] {q, r + 1};
    }

    public static int[] decaleGauche(int q, int r) {
        return new int[] {q - 1, r};
    }
}
