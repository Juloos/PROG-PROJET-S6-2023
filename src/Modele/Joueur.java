package Modele;
import Global.Config;
import java.util.ArrayList;
import java.util.HashMap;

public class Joueur {
    public int score;
    public int tuiles;
    public HashMap<Coord,Boolean> pions;

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

    public void ajouterPion(Coord c){
        if (pions.size() >= Config.NB_PIONS)
            throw new RuntimeException("J1 a déjà placé tout ses pions.");

        if(!estPion(c)){
            pions.put(c,false);
        }else{
            throw new RuntimeException("Impossible de placer le pion à l'emplacement " + c + ".");
        }

    }

}
