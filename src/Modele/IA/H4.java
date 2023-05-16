package Modele.IA;

import Modele.Coord;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuGraphe;
import Modele.Joueurs.Joueur;

public class H4 implements Heuristique {
    @Override
    public double evaluer(Jeu j, int pdvJoueur) {
        double valeur = 0;
        JeuGraphe jg = new JeuGraphe(j);
        for (Joueur joueur : jg.getJoueurs())
            if (!joueur.estTermine())
                for (Coord pion : joueur.getPions())
                    if (jg.estPionIsole(pion))
                        valeur += jg.getIlot(pion).stream().mapToDouble(c -> j.getPlateau().get(c)).sum() * (joueur.id == pdvJoueur ? 1 : -1);
        return valeur;
    }
}