import Controleur.MoteurJeu;
import Modele.Joueurs.Joueur;
import static Global.Config.*;

import java.util.Arrays;

public class TournoiIA {
    public static void main(String[] args) throws InterruptedException {
        int[] winnersRate = new int[JOUEURS_TOURNOI.length];
        for (int i = 0; i < NB_PARTIES; i++) {
            MoteurJeu moteurJeu = new MoteurJeu(Arrays.stream(JOUEURS_TOURNOI).map(Joueur::clone).toArray(Joueur[]::new));
            moteurJeu.start();
            moteurJeu.join();
            for (int j : moteurJeu.getJeu().getWinner())
                winnersRate[j]++;
            if (DEBUG)
                System.out.println("Gagnant(s) Partie " + (i + 1) + " : " + moteurJeu.getJeu().getWinner());
            else
                System.out.print("\rPartie " + (i + 1) + "/" + NB_PARTIES);
        }
        System.out.println("\nRésultats :");
        for (int i = 0; i < winnersRate.length; i++)
            System.out.println("  Joueur " + i + " : " + winnersRate[i] + " victoires sur " + NB_PARTIES + " parties (" + (winnersRate[i] * 100 / NB_PARTIES) + "%)");
    }
}
