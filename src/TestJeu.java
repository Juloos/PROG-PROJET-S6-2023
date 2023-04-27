import Modele.*;
import java.util.*;
import static Global.Config.*;

public class TestJeu {
    public static void main(String[] args) {
        JeuConcret j = new JeuConcret();
        for (int i = 0; i < NB_JOUEUR * NB_PIONS; i++)
            j.ajouterPion(randomPion(j));
        System.out.println(j);
    }

    static Coord randomPion(Jeu j) {
        Random rand = new Random();
        Coord c = new Coord();
        do {
            c.r = rand.nextInt(TAILLE_PLATEAU_X);
            c.q = (c.r % 2 == 0) ? rand.nextInt(TAILLE_PLATEAU_Y - 1) : rand.nextInt(TAILLE_PLATEAU_Y);
        } while (j.estPion(c));
        return c;
    }
}
