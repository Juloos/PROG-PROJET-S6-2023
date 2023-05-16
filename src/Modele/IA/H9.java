package Modele.IA;

import Modele.Coord;
import Modele.Jeux.Jeu;

import java.util.ArrayList;

public class H9 implements Heuristique {
    int nbAppels = 0;

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        int nbpoissonpdv = 0;
        int nbpoissonadversairemax = 0;
        for (int joueur = 0; joueur < j.getNbJoueurs(); joueur++) {
            ArrayList<Coord> dejavisiter = new ArrayList<>();
            for (Coord pion : j.getJoueur(joueur).getPions()) {
                for (Coord c : j.deplacementsPion(pion)) {
                    if (!dejavisiter.contains(c)) {
                        dejavisiter.add(c);
                    }
                }
            }
            int nbpoissonjoueur = 0;
            for (Coord n : dejavisiter) {
                nbpoissonjoueur += j.getPlateau().get(n);
            }
            if (joueur == pdvJoueur) {
                nbpoissonpdv = nbpoissonjoueur;
            } else {
                if (nbpoissonjoueur > nbpoissonadversairemax) {
                    nbpoissonadversairemax = nbpoissonjoueur;
                }
            }
        }
        return nbpoissonpdv - nbpoissonadversairemax;
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}