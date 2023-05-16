package Modele.Jeux;

import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Coups.CoupTerminaison;
import Modele.Joueurs.Joueur;

import java.util.ArrayList;

public class JeuGraphe extends Jeu {
    public JeuGraphe(Jeu j) {
        super(new Joueur[j.joueurs.length], j.plateau.clone());
        for (int i = 0; i < nbJoueurs; i++)
            joueurs[i] = j.joueurs[i].clone();
        joueurCourant = j.joueurCourant;
    }

    public ArrayList<Coup> coupsPossibles() {
        ArrayList<Coup> coups = new ArrayList<>();
        if (getJoueur().getPions().size() < nbPions) {
            for (Coord c : placementsPionValide())
                coups.add(new CoupAjout(c, joueurCourant));
        } else if (peutJouer()) {
            for (Coord s : joueurs[joueurCourant].getPions())
                if (!estPionIsole(s))
                    for (Coord d : deplacementsPion(s))
                        coups.add(new CoupDeplacement(s.clone(), d, joueurCourant));
        } else if (!getJoueur().estTermine())
            coups.add(new CoupTerminaison(joueurCourant));
        return coups;
    }

    public boolean estPionIsole(Coord c) {
        return false;
    }



    public Coup remplirIlot(ArrayList<Coord> pions) {
        Coup maxcoup = null;
        int max = 0;
        int k;
        for (Coord c : pions) {
            for (Coord j : deplacementsPion(c)) {
                CoupDeplacement cou = new CoupDeplacement(c, j, getJoueur(joueurCourant).getScore(), joueurCourant);
                jouer(cou);
                pions.remove(c);
                pions.add(j);
                if ((k = remplir(pions)) > max) {
                    maxcoup = cou;
                    max = k;
                }
                pions.remove(j);
                pions.add(c);
                cou.annuler(this);
            }
        }
        return maxcoup;
    }
    public int remplir(ArrayList<Coord> pions) {
        int valeur = 0;
        boolean fils = false;
        for (Coord c : pions){
            if (deplacementsPion(c)==null){
                fils = true;
            }
        }
        if (fils){
            return getJoueur(joueurCourant).getScore();
        }
        for (Coord c : pions) {
            for (Coord j : deplacementsPion(c)) {
                CoupDeplacement cou = new CoupDeplacement(c, j, getJoueur(joueurCourant).getScore(), joueurCourant);
                jouer(cou);
                pions.remove(c);
                pions.add(j);
                int k = remplir(pions);
                if (k > valeur) {
                    valeur = k;
                }
                pions.add(c);
                pions.remove(j);
                cou.annuler(this);
            }
        }
        return valeur;
    }
}
