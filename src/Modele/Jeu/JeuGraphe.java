package Modele.Jeu;

import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
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
        Coup coup;
        if (getJoueur().getPions().size() < nbPions) {
            for (Coord c : placememntPionValide())
                coups.add(new CoupAjout(c, joueurCourant));
        } else if (peutJouer()) {
            for (Coord s : joueurs[joueurCourant].getPions())
                for (Coord d : deplacementsPion(s))
                    if ((coup = new CoupDeplacement(s.clone(), d, joueurCourant)).estJouable(this))
                        coups.add(coup);
        } else if (!getJoueur().estTermine())
            coups.add(new CoupTerminaison(joueurCourant));
        return coups;
    }
}
