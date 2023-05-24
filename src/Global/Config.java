package Global;

import IHM.TypeIHM;
import Modele.IA.*;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;

public class Config {
    public final static TypeIHM TYPE_IHM = TypeIHM.GRAPHIQUE;
    public final static int NB_HEURISTIQUES = 9;
    public final static boolean DEBUG = false;
    public final static int TAILLE_PLATEAU_X = 8;
    public final static int TAILLE_PLATEAU_Y = 8;
    public final static int NB_PIONS_TOTAL = 9;
    public final static int NB_JOUEUR = 2;
    public final static int NB_MIN_JOUEUR = 2;
    public final static int NB_MAX_JOUEUR = 4;
    public final static long ANIMATION_DURATION = 1000;
    public final static String[] COULEURS = {"\033[0m", "\033[34m", "\033[31m", "\033[32m", "\033[33m"};  // index 0: reset, indexe i+1: couleur du joueur i
    // public final static String[] COULEURS = {"", "0*", "1*", "2*", "3*"};

    public final static Joueur[] JOUEURS_MATCH = new Joueur[]{new JoueurIA(0, IA.Difficulte.DIFFICILE), new JoueurIA(1, IA.Difficulte.LEGENDAIRE)};
    public final static int NB_PARTIES = 100;

    public final static int NB_MONTECARLOS = 10;
    public final static int NB_GENERATIONS = 100;
    public final static double MUTATION_COEFF = 1;

    public final static Heuristique IA_LEGENDAIRE_HEURISTIQUE = new H11();
    public final static int IA_LEGENDAIRE_PROFONDEUR = 6;
    public final static double IA_LEGENDAIRE_THRESHOLD = 2000;

    public final static Heuristique IA_DIFFICILE_HEURISTIQUE = new H3(100);
    public final static int IA_DIFFICILE_PROFONDEUR = 1;
    public final static double IA_DIFFICILE_THRESHOLD = 0.9;

    public final static Heuristique IA_MOYEN_HEURISTIQUE = new Hmax(new double[] {Double.MAX_VALUE, 0.06955607480490178, 0.4267307988925493, 0.43383110742734443, 0.7971510983516115, -0.35545197800538664, 0.7528940417041758, -0.0528198767603858, 0.611908449134964});
    public final static int IA_MOYEN_PROFONDEUR = 4;
    public final static double IA_MOYEN_THRESHOLD = 5000;

    public final static Heuristique IA_FACILE_HEURISTIQUE = new H5();
    public final static int IA_FACILE_PROFONDEUR = 4;
    public final static double IA_FACILE_THRESHOLD = 30;
}
