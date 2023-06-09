package Modele.IA;

import Modele.Jeux.*;

public class H2 implements Heuristique {
    int nbAppels = 0;

    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        nbAppels++;
        int premierAutreJoueur = (pdvJoueur == 0 ? 1 : 0);
        for (int i = 0; i < j.getNbJoueurs(); i++)
            if (j.getJoueur(i).id != pdvJoueur && j.getJoueur(i).compareTo(j.getJoueur(premierAutreJoueur)) > 0)
                premierAutreJoueur = i;
        return j.getJoueur(pdvJoueur).compareTo(j.getJoueur(premierAutreJoueur));
    }

    @Override
    public int getNbAppels() {
        return nbAppels;
    }
}
