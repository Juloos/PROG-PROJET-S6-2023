package Modele;

public interface Coup {
    public void jouer(Jeu j);
    public boolean estJouable(Jeu j);
    public void annuler(Jeu j);
    public int getJoueur();
    public String getSaveString();
    @Override
    public String toString();
}
