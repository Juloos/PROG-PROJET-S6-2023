package Modele.Joueurs;

import Controleur.MoteurJeu;
import Modele.Actions.Action;
import Modele.Coord;
import Modele.Jeux.Jeu;

import java.util.HashMap;
import java.util.Set;

import static Global.Config.TAILLE_PLATEAU_X;
import static Global.Config.TAILLE_PLATEAU_Y;

public abstract class Joueur implements Cloneable, Comparable<Joueur> {
    public final int id;
    final HashMap<Coord, Boolean> pions;
    int score;
    int tuiles;
    boolean termine;

    String nom;

    volatile Action action;

    public Joueur(int id) {
        this.id = id;
        score = 0;
        tuiles = 0;
        pions = new HashMap<>();
        termine = false;
        nom = "Joueur " + id;
    }

    public Joueur(int id, int score, int tuiles, HashMap<Coord, Boolean> pions) {
        this.id = id;
        this.score = score;
        this.tuiles = tuiles;
        this.pions = pions;
    }

    public abstract Action reflechir(MoteurJeu mt);

    @Override
    public abstract Joueur clone();

    public int getID() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getTuiles() {
        return tuiles;
    }

    public Set<Coord> getPions() {
        return pions.keySet();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void ajouterTuile(int score) {
        this.score += score;
        this.tuiles += 1;
    }

    public void supprimerTuile() {
        this.tuiles--;
    }

    public void decrementerScore(int val) {
        this.score -= val;
    }

    public void reAnimer() {
        this.termine = false;
    }

    public void ajouterPion(Coord c) {
        if (pions.containsKey(c))
            throw new IllegalArgumentException("Pion déjà présent");
        pions.put(c, false);
    }

    public void replacerPion(Coord source, boolean val) {
        pions.replace(source, val);
    }

    public void supprimerPion(Coord c) {
        if (!pions.containsKey(c))
            throw new IllegalArgumentException("Pion n'est pas présent");
        pions.remove(c);
    }


    public synchronized void deplacerPion(Coord source, Coord destination) {
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

    public void debloquerPion(Coord c) {
        if (!pions.containsKey(c))
            throw new IllegalArgumentException("Pion inexistant");
        pions.put(c, false);
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

    public String toString() {
        String dataHash = "";
        Coord[] tempL = pions.keySet().toArray(new Coord[pions.size()]);
        for (int j = 0; j < pions.size(); j++) {
            dataHash += " " + tempL[j].q + " " + tempL[j].r;
        }
        return 0 + " " + id + " " + score + " " + tuiles + " " + pions.size() + dataHash;
    }

    @Override
    public int compareTo(Joueur j) {
        return TAILLE_PLATEAU_X * TAILLE_PLATEAU_Y * (score - j.score) + (tuiles - j.tuiles);
    }
}
