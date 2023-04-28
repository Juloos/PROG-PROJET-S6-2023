package Modele;

import static Global.Config.*;
import java.util.HashMap;
import java.util.Set;

public class Joueur implements Comparable<Joueur> {
    public final int id;
    private int score;
    private int tuiles;
    private final HashMap<Coord, Boolean> pions;
    private boolean termine;

    public Joueur(int id) {
        this.id = id;
        score = 0;
        tuiles = 0;
        pions = new HashMap<>();
        termine = false;
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

    public void ajouterTuile(int score) {
        this.score += score;
        this.tuiles += 1;
    }

    public void ajouterPion(Coord c) {
        if (pions.containsKey(c))
            throw new IllegalArgumentException("Pion déjà présent");
        if (pions.size() >= NB_PIONS)
            throw new IllegalArgumentException("Trop de pions");
        pions.put(c, false);
    }

    public void deplacerPion(Coord source, Coord cible) {
        if (!pions.containsKey(source))
            throw new IllegalArgumentException("Pion inexistant");
        if (pions.containsKey(cible))
            throw new IllegalArgumentException("Pion déjà présent");
        pions.remove(source);
        pions.put(cible, false);
    }

    public void bloquerPion(Coord c) {
        if (!pions.containsKey(c))
            throw new IllegalArgumentException("Pion inexistant");
        pions.put(c, true);
    }

    public boolean estPionBloque(Coord c) {
        if (!pions.containsKey(c))
            throw new IllegalArgumentException("Pion inexistant");
        return pions.get(c);
    }

    public boolean peutJouer() {
        return pions.containsValue(false) || pions.size() < NB_PIONS;
    }

    public boolean estTermine() {
        return termine;
    }

    public void terminer() {
        if (peutJouer())
            throw new IllegalArgumentException("Joueur peut encore jouer.");
        termine = true;
    }

    public boolean estPion(Coord c) {
        return pions.containsKey(c);
    }

    @Override
    public int compareTo(Joueur j) {
        return j.score != score ? score - j.score : tuiles - j.tuiles;
    }
}
