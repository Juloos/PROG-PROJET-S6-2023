import IA.*;
import Modele.*;
import java.util.*;
import static Global.Config.*;

public class TestJeu {
    public static void main(String[] args) {
        Joueur[] Joueurs = new Joueur[] {new JoueurHumain(0, "Joueur 1"), new JoueurIA(1, IA.Difficulte.ALEATOIRE)};
        JeuConcret j = new JeuConcret(Joueurs);
        Coup c;
        for (int i = 0; i < NB_JOUEUR * NB_PIONS; i++) {
            if (DEBUG || j.getJoueur() instanceof JoueurHumain)
                System.out.println(j);
            if (j.getJoueur() instanceof JoueurIA)
                c = ((JoueurIA) j.getJoueur()).jouer(j);
            else
                c = new CoupAjout(askAjout(j), j.getJoueur().id);
            if (DEBUG || j.getJoueur() instanceof JoueurIA)
                System.out.println(c);
            j.jouer(c);
        }
        while (!j.estTermine()) {
            while (j.peutJouer()) {
                if (DEBUG || j.getJoueur() instanceof JoueurHumain)
                    System.out.println(j);
                if (j.getJoueur() instanceof JoueurIA)
                    c = ((JoueurIA) j.getJoueur()).jouer(j);
                else
                    c = new CoupDeplacement(askDeplacement1(j), askDeplacement2(j), j.getJoueur().id);
                if (DEBUG || j.getJoueur() instanceof JoueurIA)
                    System.out.println(c);
                j.jouer(c);
            }
            c = new CoupTerminaison(j.getJoueur().id);
            if (DEBUG)
                System.out.println(c);
            j.jouer(c);
        }
        System.out.println("Le gagnant est le joueur "+ (j.getWinner()+1));
    }

    static Coord askAjout(Jeu j) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Coordonnées du pion à ajouter : ");
        return new Coord(sc.nextInt(), sc.nextInt());
    }

    static Coord askDeplacement1(Jeu j) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Coordonnées du pion à déplacer : ");
        return new Coord(sc.nextInt(), sc.nextInt());
    }

    static Coord askDeplacement2(Jeu j) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Coordonnées où déplacer le pion : ");
        return new Coord(sc.nextInt(), sc.nextInt());
    }
}
