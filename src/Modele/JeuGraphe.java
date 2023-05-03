package Modele;

import java.util.ArrayList;

public class JeuGraphe extends Jeu {
    public int valeur;
    public final JeuGraphe parent;
    public final ArrayList<JeuGraphe> fils;

    public JeuGraphe(Jeu j) {
        super(new Joueur[j.joueurs.length], j.plateau.clone());
        for (int i = 0; i < nbJoueurs; i++)
            joueurs[i] = j.joueurs[i].clone();
        joueurCourant = j.joueurCourant;
        valeur = 0;
        parent = null;
        fils = new ArrayList<>();
    }

    private void ajouterFils(Coup c) {
        JeuGraphe j = new JeuGraphe(this);
        j.jouer(c);
        fils.add(j);
    }

    public void calculerFils() {
        Coup coup;
        if (joueurs[joueurCourant].getPions().size() < nbPions) {
            for (int r = 0; r < plateau.rMax; r++)
                for (int q = 0; q < plateau.qMax; q++)
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
