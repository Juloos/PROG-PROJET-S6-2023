package Modele.Coups;

import Modele.Jeu.Jeu;

public interface Coup {
    public void jouer(Jeu j);

    public boolean estJouable(Jeu j);

    public void annuler(Jeu j);

    public int getJoueur();

    public String getSaveString();

    public String getMessageErreur();

    @Override
    public String toString();
}
