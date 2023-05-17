package Modele.IA;

import Modele.Coups.Coup;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuGraphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static Global.Config.*;

public abstract class Minimax {
    Coup coup;
    JeuGraphe pointeur;

    final Heuristique HEURISTIQUE;
    final int PDV_JOUEUR;
    final int PROFONDEUR_MAX;
    final double HVAL_THRESHOLD;

    public Minimax(int joueur, Heuristique heuristique, int profondeur, double hval_threshold) {
        HEURISTIQUE = heuristique;
        PROFONDEUR_MAX = profondeur;
        HVAL_THRESHOLD = hval_threshold;
        PDV_JOUEUR = joueur;
    }

    private boolean fautMaximiser() {
        return PDV_JOUEUR == pointeur.getJoueur().id;
    }

    private double parcourirArbre(int profondeur, double alpha, double beta) {
        if (profondeur == 0 || pointeur.estTermine())
            return HEURISTIQUE.evaluer(pointeur, PDV_JOUEUR);

        ArrayList<Coup> coups = pointeur.coupsPossibles();
        if (profondeur > 1) {
            HashMap<Coup, Double> valeurs = new HashMap<>();
            for (Coup cfils : coups) {
                pointeur.jouer(cfils);
                valeurs.put(cfils, HEURISTIQUE.evaluer(pointeur, PDV_JOUEUR));
                cfils.annuler(pointeur);
                if (Math.abs(valeurs.get(cfils)) > HVAL_THRESHOLD) {
                    if (profondeur == PROFONDEUR_MAX)
                        this.coup = cfils;
                    return valeurs.get(cfils);
                }
            }
            coups.sort(Comparator.comparingDouble(valeurs::get));
            if (fautMaximiser())
                Collections.reverse(coups);
        }

        double valeur;
        double minimaxVal;
        if (fautMaximiser()) {
            minimaxVal = Double.NEGATIVE_INFINITY;
            for (Coup cfils : coups) {
                pointeur.jouer(cfils);
                valeur = parcourirArbre(profondeur - 1, alpha, beta);
                if (profondeur == PROFONDEUR_MAX && valeur > minimaxVal)
                    this.coup = cfils;
                minimaxVal = Double.max(minimaxVal, valeur);
                cfils.annuler(pointeur);
                if (minimaxVal > beta || minimaxVal > HVAL_THRESHOLD)
                    break;
                alpha = Double.max(alpha, minimaxVal);
            }
        } else {
            minimaxVal = Double.POSITIVE_INFINITY;
            for (Coup cfils : coups) {
                pointeur.jouer(cfils);
                valeur = parcourirArbre(profondeur - 1, alpha, beta);
                if (profondeur == PROFONDEUR_MAX && valeur < minimaxVal)
                    this.coup = cfils;
                minimaxVal = Double.min(minimaxVal, valeur);
                cfils.annuler(pointeur);
                if (minimaxVal < alpha || -minimaxVal > HVAL_THRESHOLD)
                    break;
                beta = Double.min(beta, minimaxVal);
            }
        }
        return minimaxVal;
    }

    public Coup calculerCoup(Jeu jeu) {
        pointeur = new JeuGraphe(jeu);
        coup = null;
        if (DEBUG)
            System.out.println("Minimax réfléchissionne...");
        parcourirArbre(PROFONDEUR_MAX, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        if (DEBUG)
            System.out.println("Minimax terminé.");
        return coup;
    }
}
