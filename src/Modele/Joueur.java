package Modele;

import Controleur.MoteurJeu;

import java.util.HashMap;
import java.util.Set;

import static Global.Config.*;

public abstract class Joueur implements Comparable<Joueur> {
    public final int id;
    final HashMap<Coord, Boolean> pions;
    int score;
    int tuiles;
    boolean termine;

    public Joueur(int id) {
        this.id = id;
        score = 0;
        tuiles = 0;
        pions = new HashMap<>();
        termine = false;
    }

    public Joueur(int id, int score, int tuiles, HashMap<Coord, Boolean> pions, boolean termine) {
        this.id = id;
        this.score = score;
        this.tuiles = tuiles;
        this.pions = pions;
        this.termine = termine;
    }

    public abstract Action reflechir(MoteurJeu mt);

    @Override
    public abstract Joueur clone();

    public int getScore() {
        return score;
    }

    public int getTuiles() {
        return tuiles;
    }

    public Set<Coord> getPions() {
        return pions.keySet();
    }

    public void ajouterTuile(int score) {
        this.score += score;
        this.tuiles += 1;
    }

    public void ajouterPion(Coord c) {
        if (pions.containsKey(c))
            throw new IllegalArgumentException("Pion déjà présent");
        pions.put(c, false);
    }

    public void supprimerPion(Coord c) {
        if (!pions.containsKey(c))
            throw new IllegalArgumentException("Pion n'est pas présent");
        pions.remove(c);
    }


    public void deplacerPion(Coord source, Coord destination) {
        if (!pions.containsKey(source))
            throw new IllegalArgumentException("Pion inexistant");
        if (pions.containsKey(destination))
            throw new IllegalArgumentException("Pion déjà présent");
        pions.remove(source);
        pions.put(destination, false);
    }

    public void bloquerPion(Coord c) {
        if (!pions.containsKey(c))
            throw new IllegalArgumentException("Pion inexistant");
        pions.put(c, true);
    }

    public boolean peutJouer(Jeu j) {
        return pions.containsValue(false) || pions.size() < j.getNbPions();
    }

    public boolean estTermine() {
        return termine;
    }

    public void terminer() {
        termine = true;
    }

    public boolean estPion(Coord c) {
        return pions.containsKey(c);
    }

    @Override
    public int compareTo(Joueur j) {
        return TAILLE_PLATEAU_X * TAILLE_PLATEAU_Y * (score - j.score) + (tuiles - j.tuiles);
    }
}
