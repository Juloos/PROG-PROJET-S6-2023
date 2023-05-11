package Modele.Joueurs;

import Controleur.MoteurJeu;
import Modele.Actions.Action;
import Modele.Coord;

import java.util.HashMap;

public class JoueurHumain extends Joueur {
    String nom;

    public JoueurHumain(int id) {
        super(id);
        this.nom = "Joueur "+id;
    }
    public JoueurHumain(int id, int score, int tuiles, HashMap<Coord,Boolean> pions){
        super(id, score, tuiles, pions);
        this.nom = "Joueur "+id;
    }

    public JoueurHumain(int id, String nom) {
        super(id);
        this.nom = nom;
    }

    public JoueurHumain(int id, int score, int tuiles, HashMap<Coord, Boolean> pions, String nom) {
        super(id, score, tuiles, pions);
        this.nom = nom;
    }

    @Override
    public Action reflechir(MoteurJeu mt) {
        return mt.getIHM().attendreActionJoueur();
    }

    @Override
    public JoueurHumain clone() {
        return new JoueurHumain(id, score, tuiles, new HashMap<>(pions), nom);
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        String dataHash = "";
        Coord[] tempL = pions.keySet().toArray(new Coord[pions.size()]);
        for (int j = 0; j < pions.size(); j++) {
            dataHash += " " + tempL[j].q + " " + tempL[j].r;
        }
        return 1 + " " + id + " \n" + nom + "\n " + score + " " + tuiles + " " + pions.size() + dataHash;
    }
}
