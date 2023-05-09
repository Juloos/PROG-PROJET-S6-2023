package Global;

import IHM.TypeIHM;

public class Config {
    public final static TypeIHM TYPE_IHM = TypeIHM.CONSOLE;
    public final static boolean DEBUG = false;
    public final static int TAILLE_PLATEAU_X = 8;
    public final static int TAILLE_PLATEAU_Y = 8;
    public final static int NB_PIONS = 4;
    public final static int NB_JOUEUR = 4;
    public final static int NB_MIN_JOUEUR = 2;
    public final static int NB_MAX_JOUEUR = 4;

    public static final double ANIMATION_DURATION = 2.0;
    public final static String[] COULEURS = {"\033[0m", "\033[34m", "\033[31m", "\033[32m", "\033[33m"};  // index 0: reset, indexe i+1: couleur du joueur i
}
