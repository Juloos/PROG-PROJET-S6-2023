package IHM.Graphique.Composants;

import Modele.Coord;
import Modele.Plateau;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class PlateauGraphique extends JComponent {

    final int BORDURES_X = 2, BORDURES_Y = 1;
    Image[] sprites;
    double TAILLE_CASES, Y_OFFSET;

    Plateau plateau;

    public PlateauGraphique() {
        super();

        this.plateau = null;

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

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        if (plateau != null) {
            final int NB_ROWS = plateau.getNbRows();
            TAILLE_CASES = (Math.min(getHeight(), getWidth()) * 4.0 / (3.0 * NB_ROWS)) - 1.0;
            Y_OFFSET = TAILLE_CASES / NB_ROWS;

            for (int r = 0; r < NB_ROWS; r++) {
                for (int q = 0; q < (r % 2 == 0 ? plateau.getNbColumns() - 1 : plateau.getNbColumns()); q++) {
                    final int TYPE_TUILE = plateau.get(new Coord(q, r));

                    final int x = XHexToPixel(q, r);
                    final int y = YHexToPixel(r);

                    Image img = sprites[TYPE_TUILE];

                    drawable.drawImage(img, x, y, (int) TAILLE_CASES, (int) TAILLE_CASES, null);
                }
            }
        }
    }

    private int XHexToPixel(int q, int r) {
        return (int) ((TAILLE_CASES / 2.0) + ((TAILLE_CASES / 2.2) * Math.sqrt(3.0) * (q - 0.5 * (r & 1))));
    }

    private int YHexToPixel(int r) {
        return (int) (Y_OFFSET + ((TAILLE_CASES / 2.2) * (3.0 / 2.0) * r));
    }
}
