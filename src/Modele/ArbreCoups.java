package Modele;

import Modele.Coups.Coup;

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

    public Coup getCoup() {
        return coup;
    }

    public ArbreCoups getParent() {
        return parent;
    }

    public ArbreCoups getFils(Coup c) {
        for (ArbreCoups fils : fils)
            if (fils.coup.equals(c))
                return fils;
        return null;
    }
}
