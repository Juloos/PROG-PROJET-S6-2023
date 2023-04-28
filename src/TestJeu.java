import Modele.*;
import java.util.*;
import static Global.Config.*;

public class TestJeu {
    public static void main(String[] args) {
        JeuConcret j = new JeuConcret();
        System.out.println(j + "\n");
        Coup c;
        for (int i = 0; i < NB_JOUEUR * NB_PIONS; i++) {
            j.jouer(c = randomAjout(j));
            if (DEBUG)
                System.out.println(c);
        }
        System.out.println(j + "\n");
        j.jouer(c = randomDeplacement(j));
        if (DEBUG)
            System.out.println(c);
        System.out.println(j);
    }

    static CoupAjout randomAjout(Jeu j) {
        Random rand = new Random();
        Coord c = new Coord();
        do {
            c.r = rand.nextInt(TAILLE_PLATEAU_X);
            c.q = (c.r % 2 == 0) ? rand.nextInt(TAILLE_PLATEAU_Y - 1) : rand.nextInt(TAILLE_PLATEAU_Y);
        } while (j.estPion(c) || j.getValeur(c) != 1);
        return new CoupAjout(c, j.getJoueur().id);
    }

    static CoupDeplacement randomDeplacement(Jeu j) {
        Random rand = new Random();
        Coord c = new Coord();
        do {
            c.r = rand.nextInt(TAILLE_PLATEAU_X);
            c.q = (c.r % 2 == 0) ? rand.nextInt(TAILLE_PLATEAU_Y - 1) : rand.nextInt(TAILLE_PLATEAU_Y);
        } while (!j.estPion(c) || j.joueurDePion(c) != j.getJoueur().id);
        ArrayList<Coord> voisins = j.deplacementsPion(c);
        return new CoupDeplacement(c, voisins.get(rand.nextInt(voisins.size())), j.getJoueur().id);
    }
}
