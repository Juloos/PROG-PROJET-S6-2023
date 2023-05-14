package Global;

import IHM.TypeIHM;
import Modele.IA.*;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;

public class Config {
    public final static TypeIHM TYPE_IHM = TypeIHM.GRAPHIQUE;
    public final static Joueur[] JOUEURS_TOURNOI = new Joueur[]{new JoueurIA(0, IA.Difficulte.DIFFICILE), new JoueurIA(1)};
    public final static int NB_PARTIES = 10;
    public final static double HVAL_THRESHOLD = 1000;
    public final static boolean DEBUG = true;
    public final static int TAILLE_PLATEAU_X = 8;
    public final static int TAILLE_PLATEAU_Y = 8;
    public final static int NB_PIONS_TOTAL = 9;
    public final static int NB_JOUEUR = 2;
    public final static int NB_MIN_JOUEUR = 2;
    public final static int NB_MAX_JOUEUR = 4;
    public final static long ANIMATION_DURATION = 1000;
    public final static String[] COULEURS = {"\033[0m", "\033[34m", "\033[31m", "\033[32m", "\033[33m"};  // index 0: reset, indexe i+1: couleur du joueur i
//    public final static String[] COULEURS = {"", "0*", "1*", "2*", "3*"};

    public final static Heuristique IA_DIFFICILE_HEURISTIQUE = new H3(100);
    public final static int IA_DIFFICILE_PROFONDEUR = 1;
    public final static double IA_DIFFICILE_THRESHOLD = Double.POSITIVE_INFINITY;

    public final static Heuristique IA_MOYEN_HEURISTIQUE = new H2();
    public final static int IA_MOYEN_PROFONDEUR = 4;
    public final static double IA_MOYEN_THRESHOLD = Double.POSITIVE_INFINITY;

    public final static Heuristique IA_FACILE_HEURISTIQUE = new H1();
    public final static int IA_FACILE_PROFONDEUR = 4;
    public final static double IA_FACILE_THRESHOLD = Double.POSITIVE_INFINITY;
}
