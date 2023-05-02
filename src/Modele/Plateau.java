package Modele;

import java.util.ArrayList;
import java.util.Collections;
import static Global.Config.*;

public class Plateau {
    boolean[][][] grille;
    int qMax, rMax;

    public final static int VIDE = 0;  // Après avoir mangé une case

    public Plateau() {
        qMax = TAILLE_PLATEAU_X;
        rMax = TAILLE_PLATEAU_Y;
        grille = new boolean[rMax][qMax][2];

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

    private boolean[] int2booleans(int i) {
        return new boolean[] {i % 2 == 1, i / 2 == 1};
    }

    private int booleans2int(boolean[] b) {
        return (b[0] ? 1 : 0) + (b[1] ? 2 : 0);
    }

    public void set(Coord c, int val) {
        if (!estCoordValide(c))
            throw new IllegalArgumentException("Coordonnées invalides : " + c + ".");
        grille[c.r][c.q] = int2booleans(val);
    }

    public int get(Coord c) {
        if (!estCoordValide(c))
            throw new IllegalArgumentException("Coordonnées invalides : " + c + ".");
        return booleans2int(grille[c.r][c.q]);
    }

    public boolean estCoordValide(Coord c) {
        return c.r >= 0 && c.r < rMax && c.q >= 0 && c.q < qMax;
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
