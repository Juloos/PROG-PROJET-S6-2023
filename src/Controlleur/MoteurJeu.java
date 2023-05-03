package Controlleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import Modele.Coup;
import Modele.Jeu;
import Modele.Joueur;

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

    public Jeu getJeu() {
        return jeu;
    }

    public Joueur getJoueurActif() {
        return null;
    }

    public void joueurCoup(Coup coup) {

    }

    public void annulerCoup() {

    }

    public void refaireCoup() {

    }
}
