package IHM.Graphique;

import java.awt.*;

/**
 * Classe regroupant les différentes couleurs du jeu
 */
public class Couleurs {

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static final Color BACKGROUND_ECRAN = new Color(1f, 1f, 1f, 0.5f);

    public static final Color COULEUR_FOND = Color.decode("#0000BB");

    public static final Color BLEU_CIEL_CLAIR = Color.decode("#52CAEF");

    public static final Color[] COULEURS_JOUEURS = new Color[]{
            new Color(1.0f, 0f, 0.086f, 1.0f),
            new Color(0.192f, 0.549f, 0.905f, 1.0f),
            new Color(0.03f, 0.843f, 0.345f, 1.0f),
            new Color(0.952f, 0.839f, 0.09f, 1.0f),
    };

    public static final Color SURBRILLANCE = new Color(0.5f, 0.8f, 0.5f, 0.45f);

    public static final Color SURBRILLANCE_PION = new Color(0.8f, 0.25f, 0.8f, 0.65f);

    public static final Color SUGGESTION = new Color(1.0f, 1.0f, 0.25f, 0.5f);
    public static final Color SUGGESTION_DEBUT = new Color(0.5f, 0.5f, 0.25f, 0.5f);
}
