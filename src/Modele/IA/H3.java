package Modele.IA;

import Modele.Coups.CoupTerminaison;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuGraphe;

import java.util.Random;

public class H3 implements Heuristique {
    Random rand = new Random();

    final int NB_SIMULE;
    final IA.Difficulte IA_DIFFICULTE;

    public H3() {
        this(200, IA.Difficulte.ALEATOIRE);
    }

    public H3(int nb_simulations, IA.Difficulte sous_ia_difficulte) {
        NB_SIMULE = nb_simulations;
        IA_DIFFICULTE = sous_ia_difficulte;
    }

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        IA[] ias = new IA[j.getNbJoueurs()];
        for (int i = 0; i < j.getNbJoueurs(); i++)
            ias[i] = IA.getIA(IA_DIFFICULTE, i);
        JeuGraphe jg;
        double nbGagnant = 0;
        for (int i = 0; i < NB_SIMULE; i++) {
            jg = new JeuGraphe(j);
            while (!jg.estTermine()) {
                while (jg.peutJouer())
                    jg.jouer(ias[jg.getJoueur().id].reflechir(jg));
                jg.jouer(new CoupTerminaison(jg.getJoueur().id));
            }
            if (jg.getWinner().contains(pdvJoueur))
                nbGagnant++;
        }
        return 2 * nbGagnant / NB_SIMULE - 1;
    }
}
