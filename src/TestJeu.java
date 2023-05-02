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
            System.out.println(c);
            if (DEBUG)
                System.out.println(j);
        }
        System.out.println(j + "\n");
        while (!j.estTermine()) {
            while (j.peutJouer()) {
                j.jouer(c = randomDeplacement(j));
                System.out.println(c);
                if (DEBUG)
                    System.out.println(j);
            }
            j.jouer(c = new CoupTerminaison(j.getJoueur().id));
            System.out.println(c);

        }
        System.out.println(j);
        System.out.println("Le gagnant est le joueur "+ (j.getWinner()+1));
    }

    static CoupAjout randomAjout(Jeu j) {
        Random rand = new Random();
        Coord c = new Coord();
        do {
            c.r = rand.nextInt(TAILLE_PLATEAU_X);
            c.q = (c.r % 2 == 0) ? rand.nextInt(TAILLE_PLATEAU_Y - 1) : rand.nextInt(TAILLE_PLATEAU_Y);
        } while (j.estPion(c) || j.getPlateau().get(c) != 1);
        return new CoupAjout(c, j.getJoueur().id);
    }

    static CoupDeplacement randomDeplacement(Jeu j) {
        Random rand = new Random();
        Coord c = new Coord();
        do {
            c.r = rand.nextInt(TAILLE_PLATEAU_X);
            c.q = (c.r % 2 == 0) ? rand.nextInt(TAILLE_PLATEAU_Y - 1) : rand.nextInt(TAILLE_PLATEAU_Y);
        } while (!j.estPion(c) || j.joueurDePion(c) != j.getJoueur().id || j.estPionBloque(c));
        ArrayList<Coord> voisins = j.deplacementsPion(c);
        return new CoupDeplacement(c, voisins.get(rand.nextInt(voisins.size())), j.getJoueur().id);
    }
}
