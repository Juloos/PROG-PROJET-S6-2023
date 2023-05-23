import Controleur.MoteurJeu;
import Modele.Joueurs.Joueur;

import java.util.Arrays;

import static Global.Config.*;

public class MatchIA {
    public static void main(String[] args) {
        int[] winnersRate = new int[JOUEURS_MATCH.length];
        long start = System.currentTimeMillis();
        if (!DEBUG)
            System.out.print("\rPartie 0/" + NB_PARTIES);
        for (int i = 0; i < NB_PARTIES; i++) {
            MoteurJeu moteurJeu = new MoteurJeu();
            long startingTimeMillis = System.currentTimeMillis();
            moteurJeu.lancerPartie(Arrays.stream(JOUEURS_MATCH).map(Joueur::clone).toArray(Joueur[]::new));
            moteurJeu.attendreFin();
            for (int j : moteurJeu.getJeu().getWinner())
                winnersRate[j]++;
            if (DEBUG)
                System.out.println("Gagnant(s) Partie " + (i + 1) + " : " + moteurJeu.getJeu().getWinner());
            else
                System.out.print("\rPartie " + (i + 1) + "/" + NB_PARTIES + " (" + ((System.currentTimeMillis() - startingTimeMillis) / 1000.0) + "s)");
        }
        System.out.println("\nRésultats (" + ((System.currentTimeMillis() - start) / 1000.0) + "s) :");
        for (int i = 0; i < winnersRate.length; i++)
            System.out.println("  Joueur " + i + " : " + winnersRate[i] + " victoires sur " + NB_PARTIES + " parties (" + (winnersRate[i] * 100 / NB_PARTIES) + "%)");
        System.out.println("Appels aux heuristiques :");
        System.out.println("  IA Facile : " + IA_FACILE_HEURISTIQUE.getNbAppels());
        System.out.println("  IA Moyen : " + IA_MOYEN_HEURISTIQUE.getNbAppels());
        System.out.println("  IA Difficile : " + IA_DIFFICILE_HEURISTIQUE.getNbAppels());
        System.out.println("  IA Légendaire : " + IA_LEGENDAIRE_HEURISTIQUE.getNbAppels());
    }
}
