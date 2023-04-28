package Modele;

public interface Coup {
    public void jouer(Jeu j);
    public boolean estJouable(Jeu j);
    public void annuler(Jeu j);
    public int getJoueur();
    @Override
    public String toString();
}
