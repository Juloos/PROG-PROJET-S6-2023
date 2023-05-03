package Controlleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import Modele.Coord;
import Modele.Jeu;

public class MoteurJeu {

    IHM ihm;

    Jeu jeu;

    public MoteurJeu() {
        switch (Config.typeIHM) {
            case CONSOLE:
                ihm = new IHMConsole(this);
                break;
            case GRAPHIQUE:
                ihm = new IHMGraphique(this);
        }
    }

    public void ajouterPion(Coord coord, int numJoueur) {

    }

    public void deplacerPion(Coord source, Coord dest, int numJoueur) {

    }

    public void annulerCoup() {

    }

    public void refaireCoup() {
        
    }
}
