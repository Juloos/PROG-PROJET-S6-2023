package IHM.Graphique;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

public class PlateauGraphique extends JComponent {

    Image[] sprites;

    double TAILLE_CASES_X, TAILLE_CASES_Y, Y_OFFSET;

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

        TAILLE_CASES_X = Math.sqrt(3.0) * 1000.0 / 8.0 * 2.0;
        TAILLE_CASES_Y = 2 * 1000.0 / 8.0 * 2.0;

        System.out.println("Y offset : " + Y_OFFSET);

        drawable.setColor(Color.red);
        for (int i = -5; i < 13; i++) {
            for (int j = -8; j < (i % 2 == 0 ? 19 : 20); j++) {
                int x = XHexToPixel(i, j);
                int y = YHexToPixel(i);

//                drawable.setColor(random.nextBoolean() ? Color.red : Color.blue);
//                drawable.fillRect(x, y, (int) TAILLE_CASES, (int) TAILLE_CASES);

                Image img = (i >= 0 && i < 8 && j >= 0 && j < (i % 2 == 0 ? 7 : 8)) ? sprites[random.nextInt(sprites.length - 1) + 1] : sprites[0];
                drawable.drawImage(img, x, y, (int) TAILLE_CASES_X, (int) TAILLE_CASES_Y, null);
            }
        }
    }

    private int XHexToPixel(int q, int r) {
        return (int) ((TAILLE_CASES_X / 2.0) + (TAILLE_CASES_X / 2.2) * Math.sqrt(3.0) * (r - 0.5 * (q & 1)));
    }

    private int YHexToPixel(int q) {
        return (int) (Y_OFFSET + (TAILLE_CASES_Y / 2.2) * (3.0 / 2.0) * q);
    }
}
