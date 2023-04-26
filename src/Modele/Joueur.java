package Modele;
import Global.Config;
import java.util.ArrayList;
import java.util.HashMap;

public class Joueur {
    public int score;
    public int tuiles;
    public HashMap<Coord, Boolean> pions;

    public Joueur(){
        score = 0;
        tuiles = 0;
        pions = new HashMap<>();
    }

    public boolean peutJouer(){
        return pions.containsValue(false);
    }
    public boolean estPion(Coord c) {
        return pions.containsKey(c);
    }


}
