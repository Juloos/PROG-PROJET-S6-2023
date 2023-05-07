package Modele.IA;

import Modele.ArbreCoups;
import Modele.Coups.*;
import Modele.Jeux.*;
import Modele.Joueurs.*;

import static Global.Config.*;

import java.util.ArrayList;
import java.util.Random;

public class IADifficile implements IA {
    Random rand;
    Joueur joueur;
    ArbreCoups graphe;
    JeuGraphe pointeur;

    public IADifficile(Joueur j) {
        joueur = j;
        rand = new Random();
        graphe = null;
        pointeur = null;
    }

    public IADifficile(Joueur j, int seed) {
        joueur = j;
        rand = new Random(seed);
        graphe = null;
        pointeur = null;
    }

    private void parcourirArbre(ArbreCoups noeud, int profondeur) {
        if (profondeur != 0) {
            for (Coup c : pointeur.coupsPossibles()) {
                ArbreCoups fils = new ArbreCoups(noeud, c);
                noeud.fils.add(fils);
                pointeur.jouer(c);
                parcourirArbre(fils, profondeur - 1);
                c.annuler(pointeur);
            }

            if (noeud.fils.isEmpty())
                noeud.valeur = HEURISTIQUE.evaluer(pointeur, joueur.id);
            else if (pointeur.getJoueur().id == joueur.id)
                noeud.valeur = noeud.fils.stream().mapToDouble(f -> f.valeur).max().getAsDouble();
            else
                noeud.valeur = noeud.fils.stream().mapToDouble(f -> f.valeur).min().getAsDouble();
        } else
            noeud.valeur = HEURISTIQUE.evaluer(pointeur, joueur.id);
    }

    private void calculerArbre(Jeu j) {
        graphe = new ArbreCoups();
        pointeur = new JeuGraphe(j);
        if (DEBUG)
            System.out.println("Je réfléchissionne");
        parcourirArbre(graphe, NB_COUPS_PREDICTION);
    }

    @Override
    public Coup reflechir(JeuConcret j) {
        if (graphe == null)
            calculerArbre(j);
        else {
            ArrayList<Coup> hist = j.historique(joueur.id);
            do {
                if (graphe != null)
                    graphe = graphe.getFils(hist.get(0));
                pointeur.jouer(hist.get(0));
                hist.remove(0);
            } while (pointeur.getJoueur().id != joueur.id);
            if (graphe == null || graphe.fils.isEmpty())
                calculerArbre(pointeur);
        }

        ArrayList<Coup> meilleursCoups = new ArrayList<>();
        for (ArbreCoups fils : graphe.fils)
            if (fils.valeur == graphe.valeur)
                meilleursCoups.add(fils.getCoup());

        return meilleursCoups.get(rand.nextInt(meilleursCoups.size()));
    }

    @Override
    public Difficulte getDifficulte() {
        return Difficulte.DIFFICILE;
    }
}
