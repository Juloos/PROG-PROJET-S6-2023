package Modele.IA;

import Modele.Coups.*;
import Modele.Jeux.*;
import Modele.Joueurs.*;

import static Global.Config.*;

import java.util.Random;

public class IADifficile implements IA {
    Random rand;
    Joueur joueur;
    Coup coup;
    JeuGraphe pointeur;

    public IADifficile(Joueur j) {
        joueur = j;
        rand = new Random();
    }

    public IADifficile(Joueur j, int seed) {
        joueur = j;
        rand = new Random(seed);
    }

    private boolean fautMaximiser() {
        return joueur.id == pointeur.getJoueur().id;
    }

    private double parcourirArbre(int profondeur, double alpha, double beta) {
        if (profondeur == 0 || pointeur.estTermine()) {
            return HEURISTIQUE.evaluer(pointeur, joueur.id);
        }

        double valeur;
        double minimaxVal;
        if (fautMaximiser()) {
            minimaxVal = Double.NEGATIVE_INFINITY;
            for (Coup cfils : pointeur.coupsPossibles()) {
                pointeur.jouer(cfils);
                valeur = parcourirArbre(profondeur - 1, alpha, beta);
                if (profondeur == NB_COUPS_PREDICTION && valeur > minimaxVal)
                    this.coup = cfils;
                minimaxVal = Double.max(minimaxVal, valeur);
                cfils.annuler(pointeur);
                if (minimaxVal > beta || minimaxVal > HEURISTIQUE_ABSVAL)
                    break;
                alpha = Double.max(alpha, minimaxVal);
            }
        } else {
            minimaxVal = Double.POSITIVE_INFINITY;
            for (Coup cfils : pointeur.coupsPossibles()) {
                pointeur.jouer(cfils);
                valeur = parcourirArbre(profondeur - 1, alpha, beta);
                if (profondeur == NB_COUPS_PREDICTION && valeur < minimaxVal)
                    this.coup = cfils;
                minimaxVal = Double.min(minimaxVal, valeur);
                cfils.annuler(pointeur);
                if (minimaxVal < alpha || -minimaxVal > HEURISTIQUE_ABSVAL)
                    break;
                beta = Double.min(beta, minimaxVal);
            }
        }
        return minimaxVal;
    }

    @Override
    public Coup reflechir(JeuConcret j) {
        pointeur = new JeuGraphe(j);
        coup = null;
        if (DEBUG)
            System.out.println("IA.DIFFICILE " + joueur.id + " réfléchit...");
        parcourirArbre(NB_COUPS_PREDICTION, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        if (DEBUG)
            System.out.println("IA.DIFFICILE " + joueur.id + " a joué " + coup);
        return coup;
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.DIFFICILE;
    }
}
