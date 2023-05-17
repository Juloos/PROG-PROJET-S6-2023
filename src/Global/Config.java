package Global;

import IHM.TypeIHM;
import Modele.IA.*;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;

public class Config {
    public final static TypeIHM TYPE_IHM = TypeIHM.AUCUNE;
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
    public final static int NB_PARTIES = 10;
    public final static int NB_MONTECARLOS = 10;
    public final static int NB_GENERATIONS = 10;
    public final static double MUTATION_COEFF = 0.1;

    public final static Heuristique IA_LEGENDAIRE_HEURISTIQUE = new Hmax(new double[]{0.22062366288461388, 0.9490822014681433, 0.6742276501914184, 0.5415444295910274, 0.03399646655215105, -0.7855275169660632, -0.17027415628645004, 0.1516308514741891, -0.011197727283639436});
    public final static int IA_LEGENDAIRE_PROFONDEUR = 2;
    public final static double IA_LEGENDAIRE_THRESHOLD = Double.POSITIVE_INFINITY;

    public final static Heuristique IA_DIFFICILE_HEURISTIQUE = new H3(10);
    public final static int IA_DIFFICILE_PROFONDEUR = 1;
    public final static double IA_DIFFICILE_THRESHOLD = Double.POSITIVE_INFINITY;

    public final static Heuristique IA_MOYEN_HEURISTIQUE = new H2();
    public final static int IA_MOYEN_PROFONDEUR = 4;
    public final static double IA_MOYEN_THRESHOLD = Double.POSITIVE_INFINITY;

    public final static Heuristique IA_FACILE_HEURISTIQUE = new H1();
    public final static int IA_FACILE_PROFONDEUR = 4;
    public final static double IA_FACILE_THRESHOLD = Double.POSITIVE_INFINITY;
}
