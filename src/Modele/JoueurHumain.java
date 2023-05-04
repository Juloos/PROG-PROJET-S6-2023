package Modele;

import Controleur.MoteurJeu;

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

    @Override
    public Coup reflechir(MoteurJeu mt) {
        mt.getIHM().attendreActionJoueur();
        return null;
    }

    public JoueurHumain clone() {
        JoueurHumain j = new JoueurHumain(id, nom);
        j.score = score;
        j.tuiles = tuiles;
        j.termine = termine;
        return j;
    }

    public String getNom() {
        return nom;
    }
}
