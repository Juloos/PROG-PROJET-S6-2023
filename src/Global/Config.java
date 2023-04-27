package Global;

public class Config {
    public final static int TAILLE_PLATEAU_X = 8;
    public final static int TAILLE_PLATEAU_Y = 8;
    public final static int NB_PIONS = 4;
    public final static int NB_JOUEUR = 2;
    public final static String[] COULEURS = {"\033[0m", "\033[34m", "\033[31m"};  // indexe 0: reset, indexe i+1: couleur du joueur i
}
