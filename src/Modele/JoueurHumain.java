package Modele;

import IHM.IHM;

public class JoueurHumain extends Joueur {
    String nom;

    public JoueurHumain(int id) {
        super(id);
        this.nom = Integer.toString(id);
    }

    @Override
    public Coup reflechir(MoteurJeu mt) {
        // TODO: Romain doit coder cette partie
        return null;
    }

    public JoueurHumain(int id, String nom) {
        super(id);
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
