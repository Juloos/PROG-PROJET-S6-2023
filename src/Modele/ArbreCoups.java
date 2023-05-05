package Modele;

import java.util.ArrayList;

public class ArbreCoups {
    Coup coup;
    ArbreCoups parent;
    public ArrayList<ArbreCoups> fils;
    public double valeur;

    public ArbreCoups() {
        this(null, null);
    }

    public ArbreCoups(ArbreCoups parent, Coup coup) {
        this.parent = parent;
        this.coup = coup;
        this.fils = new ArrayList<>();
        this.valeur = 0;
    }
}
