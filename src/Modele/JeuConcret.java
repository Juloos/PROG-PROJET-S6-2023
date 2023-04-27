package Modele;

import java.util.Stack;

import static Global.Config.NB_PIONS;

public class JeuConcret extends Jeu {
    Stack<Coup> passe;
    Stack<Coup> future;

    public JeuConcret() {
        super();
        passe = new Stack<>();
        future = new Stack<>();
    }

    public boolean estCoupPossible(Coup c) {
        if (c.joueur != joueurCourant)
            return false;
        switch (c.type) {
            case AJOUT:
                return plateau.get(c.cible) == 1 && !estPion(c.cible) && joueurs[c.joueur].getPions().size() < NB_PIONS;
            case DEPLACEMENT:
                return peutJouer(c.joueur) && joueurs[c.joueur].estPion(c.source) && deplacementsPion(c.source).contains(c.cible);
            case TERMINAISON:
                return !peutJouer(c.joueur);
        }
        return false;
    }

    public void jouer(Coup c) {
        if (!estCoupPossible(c))
            throw new IllegalArgumentException("Ce coup n'est pas possible");
        switch (c.type) {
            case AJOUT:
                ajouterPion(c.cible);
                break;
            case DEPLACEMENT:
                c.putOldVal(c.source, plateau.get(c.source));
                deplacerPion(c.source, c.cible);
                break;
            case TERMINAISON:
                for (Coord key : joueurs[c.joueur].getPions())
                    c.putOldVal(key, plateau.get(key));
                terminerJoueur(c.joueur);
                break;
        }
        passe.push(c);
        future.clear();  // on doit vider la pile si l'on fait de nouveau coup après avoir reculer
    }
}