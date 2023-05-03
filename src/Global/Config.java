package Global;

public class Config {
    public static final boolean DEBUG = false;
    public final static int TAILLE_PLATEAU_X = 8;
    public final static int TAILLE_PLATEAU_Y = 8;
    public final static int NB_PIONS = 9;
    public final static int NB_JOUEUR = 2;
    public static final int NB_MAX_JOUEUR = 4;
    public final static String[] COULEURS = {"\033[0m", "\033[34m", "\033[31m", "\033[32m", "\033[33m"};  // index 0: reset, indexe i+1: couleur du joueur i
}
