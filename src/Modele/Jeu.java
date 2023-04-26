package Modele;

import java.util.ArrayList;

public class Jeu {
    Plateau plateau;
    int scoreJ1, scoreJ2;
    int joueurCourant;
    ArrayList<Integer[]> pionsJ1, pionsJ2;

    public final static int J1 = 1;
    public final static int J2 = 2;

    public Jeu() {
        plateau = new Plateau();
        scoreJ1 = 0;
        scoreJ2 = 0;
        joueurCourant = J1;
        pionsJ1 = new ArrayList<>();
        pionsJ2 = new ArrayList<>();
    }

    ArrayList<Integer[]> deplacementsPion(int q, int r) {
        return null;
    }

    public void deplacerPion(int q1, int r1, int q2, int r2) {
        return;
    }
}
