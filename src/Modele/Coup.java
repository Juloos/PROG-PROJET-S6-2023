package Modele;

public interface Coup {
    public void jouer(Jeu j);
    public boolean estJouable(Jeu j);
    public int getJoueur();
}
