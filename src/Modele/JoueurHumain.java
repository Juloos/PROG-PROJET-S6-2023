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

    public String getNom() {
        return nom;
    }
}
