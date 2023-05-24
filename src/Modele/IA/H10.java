package Modele.IA;

import Modele.Coord;
import Modele.Jeux.Jeu;

import java.util.ArrayList;

public class H10 implements Heuristique {
    int nbAppels = 0;

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        int nbpionpdvJoueur = j.getNbPions();
        int nbpionadversairemin = j.getNbPions();
        for (int joueur = 0; joueur < j.getNbJoueurs(); joueur++) {
            int nbpion = 0;
            for (Coord pions : j.getJoueur(joueur).getPions()) {
                if (!j.estPionBloque(pions)) {
                    nbpion++;
                }
            }
            if (joueur == pdvJoueur) {
                nbpionpdvJoueur = nbpion;
            } else if (nbpion < nbpionadversairemin) {
                nbpionadversairemin = nbpion;
            }
        }
        return nbpionpdvJoueur - nbpionadversairemin;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}