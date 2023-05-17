import Controleur.MoteurJeu;
import Modele.Coups.Coup;
import Modele.IA.Hmax;
import Modele.IA.IA;
import Modele.IA.Minimax;
import Modele.Jeux.Jeu;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static Global.Config.*;

public class MonteCarloIA {
    public static void main(String[] args) {
        IAHmax[] heuristiques = new IAHmax[10000];
        Random rand = new Random();
        for (int i = 0; i < heuristiques.length; i++) {
            double[] poids = new double[10];
            for (int j = 0; j < poids.length; j++)
                poids[j] = 2 * rand.nextDouble() - 1;
            heuristiques[i] = new IAHmax(1, poids);
        }

        HashMap<IAHmax, Integer> winnersRate = new HashMap<>();
        System.out.print("\rHeuristique 0/" + heuristiques.length + " - Partie 0/" + NB_PARTIES + " (0s)");
        for (int i = 0; i < heuristiques.length; i++) {
            for (int j = 0; j < NB_PARTIES; j++) {
                MoteurJeu moteurJeu = new MoteurJeu();
                long startingTimeMillis = System.currentTimeMillis();
                moteurJeu.lancerPartie(new Joueur[]{new JoueurIA(0, IA.Difficulte.DIFFICILE), new JoueurIA(1, heuristiques[i])});
                moteurJeu.attendreFin();
                if (moteurJeu.getJeu().getWinner().contains(1))
                    winnersRate.put(heuristiques[i], winnersRate.getOrDefault(heuristiques[i], -1) + 1);
                System.out.print("\rHeuristique " + (i + 1) + "/" + heuristiques.length + " - Partie " + (j + 1) + "/" + NB_PARTIES + " (" + ((System.currentTimeMillis() - startingTimeMillis) / 1000.0) + "s)");
            }
        }

        System.out.println("Résultats :");
        for (IAHmax ia : winnersRate.keySet().stream().sorted((a, b) -> winnersRate.get(b) - winnersRate.get(a)).toArray(IAHmax[]::new))
            System.out.println("  " + ia + " : " + winnersRate.get(ia) + " victoires sur " + NB_PARTIES + " parties (" + (winnersRate.get(ia) * 100 / NB_PARTIES) + "%)");
    }
}

class IAHmax extends Minimax implements IA {
    double[] poids;

    public IAHmax(int joueur, double[] poids) {
        super(joueur, new Hmax(poids), 4,  poids[poids.length - 1]);
        this.poids = poids;
    }

    @Override
    public Coup reflechir(Jeu j) {
        return calculerCoup(j);
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.DIFFICILE;
    }

    @Override
    public String toString() {
        return Arrays.toString(poids);
    }
}
