package Modele;

import Global.Config;
import java.util.ArrayList;

public class Plateau {
    int[][] grille = new int[Config.TAILLE_PLATEAU_Y][Config.TAILLE_PLATEAU_X];

    public Plateau() {

    }

    public void set(int q, int r, int val) {
        grille[r][q] = val;
    }

    public int get(int q, int r) {
        return grille[r][q];
    }

    public ArrayList<Integer[]> voisins(int q, int r) {
        // ArrayList de couple d'entier (q, r)
        ArrayList<Integer[]> vcells = new ArrayList<>();
        if (q > 0)
            vcells.add(new Integer[] {q - 1, r});
        if (q < Config.TAILLE_PLATEAU_X - 1)
            vcells.add(new Integer[] {q + 1, r});
        if (r > 0)
            vcells.add(new Integer[] {q, r - 1});
        if (r < Config.TAILLE_PLATEAU_Y - 1)
            vcells.add(new Integer[] {q, r + 1});
        if (q > 0 && r > 0)
            vcells.add(new Integer[] {q - 1, r - 1});
        if (q < Config.TAILLE_PLATEAU_X - 1 && r < Config.TAILLE_PLATEAU_Y - 1)
            vcells.add(new Integer[] {q + 1, r + 1});
        return vcells;
    }
}
