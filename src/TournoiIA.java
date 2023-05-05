import Controleur.MoteurJeu;
import Modele.*;
import static Global.Config.*;
import IA.*;

import java.util.Arrays;

public class TournoiIA {
    public static void main(String[] args) {
        Joueur[] joueurs = new Joueur[] {new JoueurIA(0, IA.Difficulte.ALEATOIRE), new JoueurIA(1, IA.Difficulte.DIFFICILE)};
        int[] winnersRate = new int[joueurs.length];
        for (int i = 0; i < NB_PARTIES; i++) {
            MoteurJeu moteurJeu = new MoteurJeu(Arrays.stream(joueurs).map(Joueur::clone).toArray(Joueur[]::new));
            moteurJeu.run();
            for (int j : moteurJeu.getJeu().getWinner())
                winnersRate[j]++;
            if (DEBUG)
                System.out.println("Gagnant(s) Partie " + (i + 1) + " : " + moteurJeu.getJeu().getWinner());
        }
        System.out.println("\nRésultats :");
        for (int i = 0; i < winnersRate.length; i++)
            System.out.println("Joueur " + i + " : " + winnersRate[i] + " victoires sur " + NB_PARTIES + " parties (" + (winnersRate[i] * 100 / NB_PARTIES) + "%)");
    }
}
