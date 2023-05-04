package Modele;

import Controleur.MoteurJeu;

import java.util.HashMap;

public class JoueurHumain extends Joueur {
    String nom;

    public JoueurHumain(int id) {
        super(id);
        this.nom = Integer.toString(id);
    }
    public JoueurHumain(int id, int score, int tuiles, HashMap<Coord,Boolean> pions, boolean termine){
        super(id, score, tuiles, pions, termine);
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

    public JoueurHumain clone() {
        return new JoueurHumain(id, score, tuiles, new HashMap<>(pions), termine, nom);
    }

    @Override
    public String toString() {
        String dataHash = "";
        Coord[] tempL = pions.keySet().toArray(new Coord[pions.size()]);
        for (int j = 0; j < pions.size(); j++) {
            dataHash += " " + tempL[j].q + " " + tempL[j].r;
        }
        return id + " " + 1 + " " + nom + " " + score + " " + tuiles + " " + pions.size() + pions;
    }

    public String getNom() {
        return nom;
    }
}
