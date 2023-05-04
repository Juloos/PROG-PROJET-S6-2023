package IA;

import Modele.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static Global.Config.*;

public class IADifficile implements IA {
    Random rand;
    Joueur joueur;
    JeuGraphe graphe;
    int profondeurMax = -1;
    Heuristique eval;

    public IADifficile(Joueur j) {
        joueur = j;
        rand = new Random();
        graphe = null;
        eval = new H1();
    }

    public IADifficile(Joueur j, int seed) {
        joueur = j;
        rand = new Random(seed);
        graphe = null;
        eval = new H1();
    }

    private void traiter(JeuGraphe noeud, int profondeur) {
        if (profondeur == 0 || noeud.fils.isEmpty())
            noeud.valeur = eval.evaluer(noeud, joueur.id);
        else if (noeud.getJoueur().id == joueur.id)
            noeud.valeur = noeud.fils.stream().mapToInt(fils -> fils.valeur).max().orElseGet(() -> Integer.MIN_VALUE);
        else
            noeud.valeur = noeud.fils.stream().mapToInt(fils -> fils.valeur).min().orElseGet(() -> Integer.MAX_VALUE);
    }

    private void parcourirArbre(JeuGraphe noeud, int profondeur) {
        if (profondeur != 0)
            noeud.calculerFils();
        for (JeuGraphe fils : noeud.fils)
            parcourirArbre(fils, profondeur - 1);
        traiter(noeud, profondeur);
    }

    @Override
    public Coup reflechir(Jeu j) {
        graphe = new JeuGraphe(j);
        parcourirArbre(graphe, profondeurMax);
        return graphe.fils.stream().max(Comparator.comparingInt(fils -> fils.valeur)).get().coup;
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.DIFFICILE;
    }
}
