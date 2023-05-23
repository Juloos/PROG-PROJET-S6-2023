package IHM.Graphique;

import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;

/**
 * Classe permettant de charger les images du jeu
 */
public class Images implements Runnable {
    protected static Images INSTANCE;
    // Les tuiles où il n'y a pas de pingouins dessus
    private final Image[] tuilesSansPingouins;
    // Les tuiles où il y a un pingouin dessus
    private final Image[][] tuilesAvecPingouins;
    // Les sprites des pingouins
    private final Image[] pingouins;
    // La flèche pour indiquer le joueur actif
    private Image flecheJoueurActif;
    // Les flèches pour indiquer le dernier déplacement du joueur
    private Image flecheDeplacementSource, flecheDeplacementDest;

    protected Images() {
        tuilesSansPingouins = new Image[4];

        tuilesAvecPingouins = new Image[4][3];

        pingouins = new Image[4];
    }

    /* Méthodes de classe */

    public synchronized static Images getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Images();
        }
        return INSTANCE;
    }

    /**
     * Charge l'image se trouvant au chemin indiqué
     *
     * @param chemin le chemin de l'image depuis le dossier res
     * @return l'image souhaitée si le chargement a réussi, sinon retourne null
     */
    public static Image chargerImage(String chemin) {
        try {
            URL url = Images.class.getResource(chemin);
            return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println(chemin);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Redimenssionne l'image
     *
     * @param image  l'image a redimenssionée
     * @param width  la nouvelle largeur de l'image
     * @param height la nouvelle hauteur de l'image
     * @return l'image redimenssionnée
     */
    public static Image resizeImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    /* Getters */
    public Image getFlecheJoueurActif() {
        return flecheJoueurActif;
    }

    public Image getFlecheDeplacement(boolean source) {
        return source ? flecheDeplacementSource : flecheDeplacementDest;
    }

    /* Méthodes d'instance */

    /**
     * Retourne l'image de la tuile souhaitée
     *
     * @param avecPingouin vrai s'il y a un pingouin sur la tuile
     * @param nbPoissons   le nombre de poissons sur la tuile
     * @return l'image de la tuile souhaitée
     */
    public Image getTuile(boolean avecPingouin, int nbPoissons) {
        return getTuile(avecPingouin, nbPoissons, -1);
    }

    /**
     * Retourne l'image de la tuile souhaitée
     *
     * @param avecPingouin vrai s'il y a un pingouin sur la tuile
     * @param nbPoissons   le nombre de poissons sur la tuile
     * @param numJoueur    le numéro du joueur
     * @return l'image de la tuile souhaitée
     */
    public Image getTuile(boolean avecPingouin, int nbPoissons, int numJoueur) {
        if ((avecPingouin && nbPoissons == 0) || nbPoissons < 0 || nbPoissons > 3) {
            return null;
        }

        if (avecPingouin) {
            return tuilesAvecPingouins[numJoueur][nbPoissons - 1];
        } else {
            return tuilesSansPingouins[nbPoissons];
        }
    }

    /**
     * Retourne l'image des pingouins du joueur
     *
     * @param numJoueur le numéro du joueur
     * @return l'image des pingouins du joueur
     */
    public Image getPingouin(int numJoueur) {
        if (numJoueur < 0 || numJoueur > pingouins.length) {
            return null;
        }

        return pingouins[numJoueur];
    }

    @Override
    public void run() {
        // Utilisée pour charger les sprites
        // Chargement des pingouins
        String chemin = "/pingouins/";
        int i = 0;
        for (String fileName : new String[]{"PingouinRouge.png", "PingouinBleu.png", "PingouinVert.png", "PingouinJaune.png"}) {
            pingouins[i] = chargerImage(chemin + fileName);
            i++;
        }

        // Chargement des tuiles sans pingouins
        chemin = "/tuiles/";

        i = 0;
        for (String fileName : new String[]{"eau.png", "1Poisson.png", "2Poissons.png", "3Poissons.png"}) {
            tuilesSansPingouins[i] = chargerImage(chemin + fileName);
            i++;
        }

        // Chargement des tuiles avec pingouin
        i = 0;
        for (String fileName : new String[]{"1PingouinRouge.png", "2PingouinRouge.png", "3PingouinRouge.png",
                "1PingouinBleu.png", "2PingouinBleu.png", "3PingouinBleu.png",
                "1PingouinVert.png", "2PingouinVert.png", "3PingouinVert.png",
                "1PingouinJaune.png", "2PingouinJaune.png", "3PingouinJaune.png"}) {
            tuilesAvecPingouins[(i / 3)][(i % 3)] = chargerImage(chemin + fileName);
            i++;
        }

        // Chargement de la flèche du joueur actif
        try {
            flecheJoueurActif = chargerImage("/fleches/arrow_player.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Chargement des flèches d'indication du dernier déplacement
        try {
            flecheDeplacementSource = chargerImage("/fleches/arrow_move_source.png");

            flecheDeplacementDest = chargerImage("/fleches/arrow_move_dest.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
