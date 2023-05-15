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



    public Coup remplirIlot(Coord c) {
        Coup maxcoup = null;
        int max = 0;
        int k;
        for (Coord fils : deplacementsPion(c)) {
            CoupDeplacement cou = new CoupDeplacement(c, fils, getJoueur(joueurCourant).getScore(), joueurCourant);
            jouer(cou);
            if ((k=remplir(fils))>max) {
                maxcoup = cou;
                max = k;
            }
            cou.annuler(this);
        }
        return maxcoup;
    }
    public int remplir(Coord c) {
        int valeur = 0;
        if (deplacementsPion(c) == null) {
            return getJoueur(joueurCourant).getScore();
        }
        for (Coord fils : deplacementsPion(c)) {
            CoupDeplacement cou = new CoupDeplacement(c, fils, getJoueur(joueurCourant).getScore(), joueurCourant);
            jouer(cou);
            int k= remplir(fils);
            if (k>valeur) {
                valeur = remplir(fils);
            }
            cou.annuler(this);
        }
        return valeur;
    }
}
