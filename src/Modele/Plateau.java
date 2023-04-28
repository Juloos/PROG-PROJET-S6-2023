package Modele;

import java.util.ArrayList;
import java.util.Collections;
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

        ArrayList<Integer> weights = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            weights.add(1);
        for (int i = 30; i < 50; i++)
            weights.add(2);
        for (int i = 50; i < 60; i++)
            weights.add(3);
        Collections.shuffle(weights);
        Coord c = new Coord();
        int i = 0;
        for (c.q = 0; c.q < qMax; c.q++)
            for (c.r = 0; c.r < rMax; c.r++)
                set(c, (c.r % 2 == 0 && c.q == qMax - 1) ? VIDE : weights.get(i++));
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
