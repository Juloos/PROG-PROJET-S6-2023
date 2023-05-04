package Modele;

import Controleur.MoteurJeu;

import java.util.HashMap;

public class JoueurHumain extends Joueur {
    String nom;

    public JoueurHumain(int id) {
        super(id);
        this.nom = Integer.toString(id);
    }

    public JoueurHumain(int id, String nom) {
        super(id);
        this.nom = nom;
    }

    public JoueurHumain(int id, int score, int tuiles, HashMap<Coord, Boolean> pions, boolean termine, String nom) {
        super(id, score, tuiles, pions, termine);
        this.nom = nom;
    }

    @Override
    public Coup reflechir(MoteurJeu mt) {
        mt.getIHM().attendreActionJoueur();
        return null;
    }

    @Override
    public JoueurHumain clone() {
        return new JoueurHumain(id, score, tuiles, new HashMap<>(pions), termine, nom);
    }

    public String getNom() {
        return nom;
    }
}
