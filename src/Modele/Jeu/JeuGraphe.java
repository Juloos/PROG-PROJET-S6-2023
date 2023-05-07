package Modele.Jeu;

import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Joueurs.Joueur;

import java.util.ArrayList;

public class JeuGraphe extends Jeu {
    //    public final JeuGraphe parent;
    public final ArrayList<JeuGraphe> fils;
    public int valeur;
    public Coup coup;

    public JeuGraphe(Jeu j) {
        super(new Joueur[j.joueurs.length], j.plateau.clone());
        for (int i = 0; i < nbJoueurs; i++)
            joueurs[i] = j.joueurs[i].clone();
        joueurCourant = j.joueurCourant;
        valeur = 0;
        coup = null;
//        if (j instanceof JeuGraphe)
//            parent = (JeuGraphe) j;
//        else
//            parent = null;
        fils = new ArrayList<>();
    }

    @Override
    public Jeu clone() {
        return null;
    }

    public JeuGraphe(Jeu j, Coup coup) {
        this(j);
        this.coup = coup;
    }

    private void ajouterFils(Coup c) {
        JeuGraphe j = new JeuGraphe(this, c);
        j.jouer(c);
        fils.add(j);
    }

    public void calculerFils() {
        Coup coup;
        if (joueurs[joueurCourant].getPions().size() < nbPions) {
            for (int r = 0; r < plateau.getNbRows(); r++)
                for (int q = 0; q < plateau.getNbColumns(); q++)
                    if ((coup = new CoupAjout(new Coord(q, r), joueurCourant)).estJouable(this))
                        ajouterFils(coup);
        } else {
            for (Coord s : joueurs[joueurCourant].getPions())
                for (Coord d : deplacementsPion(s))
                    if ((coup = new CoupDeplacement(s.clone(), d, joueurCourant)).estJouable(this))
                        ajouterFils(coup);
        }
    }
}
