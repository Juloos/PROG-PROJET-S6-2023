package Modele;

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

    public String getNom() {
        return nom;
    }
}
