package Global;

public class Config {
    public static final boolean DEBUG = true;
    public final static int TAILLE_PLATEAU_X = 4;
    public final static int TAILLE_PLATEAU_Y = 4;
    public final static int NB_PIONS = 3;
    public final static int NB_JOUEUR = 4;
    public final static String[] COULEURS = {"\033[0m", "\033[34m", "\033[31m", "\033[32m", "\033[33m"};  // indexe 0: reset, indexe i+1: couleur du joueur i
}
