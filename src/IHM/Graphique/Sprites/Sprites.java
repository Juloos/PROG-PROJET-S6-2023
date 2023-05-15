package IHM.Graphique.Sprites;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class Sprites implements Runnable {
    protected static Sprites INSTANCE;
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

    protected Sprites() {
        tuilesSansPingouins = new Image[4];

        tuilesAvecPingouins = new Image[4][3];

        pingouins = new Image[4];
    }

    public synchronized static Sprites getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Sprites();
        }
        return INSTANCE;
    }

    public Image getTuile(boolean avecPingouin, int nbPoissons) {
        return getTuile(avecPingouin, nbPoissons, -1);
    }

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

    public Image getPingouin(int numJoueur) {
        if (numJoueur < 0 || numJoueur > pingouins.length) {
            return null;
        }

        return pingouins[numJoueur];
    }

    public Image getFlecheJoueurActif() {
        return flecheJoueurActif;
    }

    public Image getFlecheDeplacement(boolean source) {
        return source ? flecheDeplacementSource : flecheDeplacementDest;
    }

    @Override
    public void run() {
        // Utilisée pour charger les sprites
        // Chargement des pingouins
        String chemin = "res/pingouins/";
        int i = 0;
        for (String fileName : new String[]{"PingouinRouge.png", "PingouinBleu.png", "PingouinVert.png", "PingouinJaune.png"}) {
            pingouins[i] = chargerImage(chemin + fileName);
            i++;
        }

        // Chargement des tuiles sans pingouins
        chemin = "res/tuiles/";

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
            flecheJoueurActif = chargerImage("res/fleches/arrow_player.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Chargement des flèches d'indication du dernier déplacement
        try {
            flecheDeplacementSource = chargerImage("res/fleches/arrow_move_source.png");

            flecheDeplacementDest = chargerImage("res/fleches/arrow_move_dest.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Image chargerImage(String chemin) {
        try {
            InputStream in = new FileInputStream(chemin);
            return ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
