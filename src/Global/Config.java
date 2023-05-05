package Global;

import IHM.TypeIHM;
import IA.*;

public class Config {
    public final static TypeIHM TYPE_IHM = TypeIHM.AUCUNE;
    public final static int NB_PARTIES = 1;
    public final static int NB_TOURS_PREVISION = 3;
    public final static Heuristique HEURISTIQUE = new H2();
    public final static boolean DEBUG = true;
    public final static int TAILLE_PLATEAU_X = 8;
    public final static int TAILLE_PLATEAU_Y = 8;
    public final static int NB_PIONS = 9;
    public final static int NB_JOUEUR = 2;
    public final static int NB_MAX_JOUEUR = 4;
//    public final static String[] COULEURS = {"\033[0m", "\033[34m", "\033[31m", "\033[32m", "\033[33m"};  // index 0: reset, indexe i+1: couleur du joueur i
    public final static String[] COULEURS = {"", "0*", "1*", "2*", "3*"};
}
