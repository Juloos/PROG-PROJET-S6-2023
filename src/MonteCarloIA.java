import Controleur.MoteurJeu;
import Modele.Coups.Coup;
import Modele.IA.Hmax;
import Modele.IA.IA;
import Modele.IA.Minimax;
import Modele.Jeux.Jeu;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;

import java.util.*;
import java.util.stream.Collectors;

import static Global.Config.*;

public class MonteCarloIA {
    public static void main(String[] args) {
        ArrayList<Double[]> heuristiques = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < NB_MONTECARLOS; i++) {
            Double[] poids = new Double[NB_HEURISTIQUES];
            for (int j = 0; j < poids.length; j++)
                poids[j] = 2 * rand.nextDouble() - 1;
            heuristiques.add(poids);
        }

        HashMap<Double[], Integer> winnersRate = new HashMap<>();
        for (int g = 0; g <= NB_GENERATIONS; g++) {
            System.out.print("\n\rGénération " + g + "/" + NB_GENERATIONS + " - Partie 0/" + (NB_MONTECARLOS * (NB_MONTECARLOS - 1) * NB_PARTIES / 2));
            for (int i = 0; i < NB_MONTECARLOS; i++)
                winnersRate.put(heuristiques.get(i), 0);
            int nbFinis = 0;

            for (int i = 0; i < NB_MONTECARLOS; i++) {
                for (int j = i + 1; j < NB_MONTECARLOS; j++) {
                    for (int k = 0; k < NB_PARTIES; k++) {
                        MoteurJeu moteurJeu = new MoteurJeu();
                        long startingTimeMillis = System.currentTimeMillis();
                        moteurJeu.lancerPartie(new Joueur[]{new JoueurIA(0, new IAHmax(0, heuristiques.get(i))), new JoueurIA(1, new IAHmax(1, heuristiques.get(j)))});
                        moteurJeu.attendreFin();
                        if (moteurJeu.getJeu().getWinner().contains(0))
                            winnersRate.put(heuristiques.get(i), winnersRate.get(heuristiques.get(i)) + 1);
                        if (moteurJeu.getJeu().getWinner().contains(1))
                            winnersRate.put(heuristiques.get(j), winnersRate.get(heuristiques.get(j)) + 1);
                        System.out.print("\rGénération " + g + "/" + NB_GENERATIONS + " - Partie " + (++nbFinis) + "/" + (NB_MONTECARLOS * (NB_MONTECARLOS - 1) * NB_PARTIES / 2) + " (" + ((System.currentTimeMillis() - startingTimeMillis) / 1000.0) + "s)");
                    }
                }
            }

            ArrayList<Double[]> heuristiquesV2 = winnersRate.keySet().stream().sorted((ia1, ia2) -> winnersRate.get(ia2) - winnersRate.get(ia1)).limit(NB_MONTECARLOS / 10).collect(Collectors.toCollection(ArrayList::new));

            System.out.println("\n\rRésultats des 10% meilleurs heuristiques de la génération " + g + " : ");
            for (Double[] poids : heuristiquesV2)
                System.out.println("  " + Arrays.toString(poids) + " : " + winnersRate.get(poids) + " victoires sur " + ((NB_MONTECARLOS - 1) * NB_PARTIES) + " parties (" + (winnersRate.get(poids) * 100 / ((NB_MONTECARLOS - 1) * NB_PARTIES)) + "%)");
            winnersRate.clear();

            for (Double[] poids : new ArrayList<>(heuristiquesV2)) {
                for (int i = 0; i < 8; i++) {
                    Double[] poidsV2 = new Double[NB_HEURISTIQUES];
                    for (int j = 0; j < poidsV2.length; j++)
                        poidsV2[j] = poids[j] + (2 * rand.nextDouble() - 1) * MUTATION_COEFF;
                    heuristiquesV2.add(poidsV2);
                }
            }
            while (heuristiquesV2.size() < NB_MONTECARLOS) {
                Double[] poids = new Double[NB_HEURISTIQUES];
                for (int j = 0; j < poids.length; j++)
                    poids[j] = 2 * rand.nextDouble() - 1;
                heuristiquesV2.add(poids);
            }
        }
    }
}

class IAHmax extends Minimax implements IA {
    public IAHmax(int joueur, Double[] poids) {
        super(joueur, new Hmax(Arrays.stream(poids).mapToDouble(Double::doubleValue).toArray()), 2,  Double.POSITIVE_INFINITY);
    }

    @Override
    public Coup reflechir(Jeu j) {
        return calculerCoup(j);
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.LEGENDAIRE;
    }
}
