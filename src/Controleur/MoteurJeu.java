package Controleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import Modele.*;

public class MoteurJeu implements Runnable {

    IHM ihm;

    Jeu jeu;

    int nbPionsPlaces;

    public MoteurJeu() {
        switch (Config.typeIHM) {
            case CONSOLE:
                ihm = new IHMConsole(this);
                break;
            case GRAPHIQUE:
                ihm = new IHMGraphique(this);
        }

        Joueur[] joueurs = new Joueur[]{new JoueurHumain(0), new JoueurHumain(1)};
        jeu = new JeuConcret(joueurs);
    }

    public IHM getIHM() {
        return ihm;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public Joueur getJoueurActif() {
        return jeu.getJoueur();
    }

    public boolean estPhasePlacementPions() {
        return nbPionsPlaces < jeu.getNbJoueurs() * jeu.getNbPions();
    }

    public void jouerCoup(Coup coup) {
        if (coup.estJouable(jeu)) {
            coup.jouer(jeu);
            nbPionsPlaces++;
            ihm.updateAffichage(jeu);
        } else {
            ihm.afficherMessage("Coup injouable");
        }
    }

    public void annulerCoup() {
        System.out.println("Annulation du dernier coup joué");
    }

    public void refaireCoup() {
        System.out.println("Refaison du dernier coup annulé");
    }

    public void sauvegarder() {

    }

    @Override
    public void run() {
        ihm.updateAffichage(jeu);

        nbPionsPlaces = 0;
        while (nbPionsPlaces < jeu.getNbJoueurs() * jeu.getNbPions()) {
            jeu.getJoueur().reflechir(this);
        }

        while (!jeu.estTermine()) {
            jeu.getJoueur().reflechir(this);
        }
    }
}
