package IHM.Graphique;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

public class PlateauGraphique extends JComponent {

    final int BORDURES_X = 2, BORDURES_Y = 1;
    Image[] sprites;
    double TAILLE_CASES, Y_OFFSET;

    public PlateauGraphique() {
        super();

        sprites = new Image[4];
        String chemin = "res/tuiles/";

        int i = 0;
        for (String fileName : new String[]{"eau.png", "1Poisson.png", "2Poissons.png", "3Poissons.png"}) {
            try {
                InputStream in = new FileInputStream(chemin + fileName);

                sprites[i] = ImageIO.read(in);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        Random random = new Random();

        final int NB_TUILES_VERT = 8;

        System.out.println(getHeight());
        TAILLE_CASES = (getHeight() * 4.0 / (3.0 * NB_TUILES_VERT)) - 1.0;

        Y_OFFSET = TAILLE_CASES / NB_TUILES_VERT;

        drawable.setColor(Color.red);
        for (int i = -BORDURES_Y; i < NB_TUILES_VERT + BORDURES_Y; i++) {
            for (int j = -BORDURES_X; j < (i % 2 == 0 ? NB_TUILES_VERT + BORDURES_X - 1 : NB_TUILES_VERT + BORDURES_X); j++) {
                int x = XHexToPixel(i, j);
                int y = YHexToPixel(i);

                Image img = (i >= 0 && i < NB_TUILES_VERT && j >= 0 && j < (i % 2 == 0 ? NB_TUILES_VERT - 1 : NB_TUILES_VERT)) ? sprites[random.nextInt(sprites.length - 1) + 1] : sprites[0];

                if (img != null) {
                    drawable.drawImage(img, x, y, (int) TAILLE_CASES, (int) TAILLE_CASES, null);
                }
            }
        }
    }

    private int XHexToPixel(int q, int r) {
        return (int) ((TAILLE_CASES / 2.0) + (TAILLE_CASES / 2.2) * Math.sqrt(3.0) * (r - 0.5 * (q & 1)));
    }

    private int YHexToPixel(int q) {
        return (int) (Y_OFFSET + (TAILLE_CASES / 2.2) * (3.0 / 2.0) * q);
    }
}
