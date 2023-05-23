package Modele.Joueurs;

import Controleur.MoteurJeu;
import Modele.Actions.Action;
import Modele.Coord;

import java.util.HashMap;

public class JoueurHumain extends Joueur {
    String nom;

    public JoueurHumain(int id) {
        super(id);
        this.nom = "Joueur " + (1 + id);
    }

    public JoueurHumain(int id, int score, int tuiles, HashMap<Coord, Boolean> pions, boolean termine) {
        super(id, score, tuiles, pions, termine);
        this.nom = "Joueur " + (1 + id);
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
    public JoueurHumain resetJoueur() {
        return new JoueurHumain(this.id, this.nom);
    }

    public synchronized void setAction(MoteurJeu mt, Action action) {
        mt.appliquerAction(action);
    }

    @Override
    public void reflechir(MoteurJeu mt) {
        System.out.println("Le joueur humain réfléchit");
        mt.getIHM().attendreActionJoueur(this);
    }

    @Override
    public JoueurHumain clone() {
        return new JoueurHumain(id, score, tuiles, new HashMap<>(pions), termine, nom);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
