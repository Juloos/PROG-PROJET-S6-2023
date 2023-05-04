package IHM.Graphique.Composants;

import Modele.Coord;
import Modele.Jeu;
import Modele.Plateau;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class PlateauGraphique extends JComponent {

    final int BORDURES_X = 2, BORDURES_Y = 1;
    final Color SURBRIANLLANCE = new Color(0.5f, 0.8f, 0.5f, 0.45f);
    Image[][] sprites;
    double TAILLE_CASES, Y_OFFSET;
    Jeu jeu;

    List<Coord> tuilesSurbrillance;

    public PlateauGraphique() {
        super();

        this.jeu = null;

        sprites = new Image[5][4];
        String chemin = "res/tuiles/";

        int i = 0;
        for (String fileName : new String[]{"eau.png", "1Poisson.png", "2Poissons.png", "3Poissons.png"}) {
            try {
                InputStream in = new FileInputStream(chemin + fileName);

                sprites[0][i] = ImageIO.read(in);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        i = 0;
        for (String fileName : new String[]{"1PingouinRouge.png", "2PingouinRouge.png", "3PingouinRouge.png",
                "1PingouinBleu.png", "2PingouinBleu.png", "3PingouinBleu.png",
                "1PingouinVert.png", "2PingouinVert.png", "3PingouinVert.png",
                "1PingouinJaune.png", "2PingouinJaune.png", "3PingouinJaune.png"}) {
            sprites[(i / 3) + 1][i % 3] = chargerImage(chemin + fileName);
            i++;
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

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        drawable.clearRect(0, 0, getWidth(), getHeight());

        Plateau plateau = jeu.getPlateau();
        if (jeu != null) {
            Random random = new Random();

            final int NB_ROWS = plateau.getNbRows();
            TAILLE_CASES = Math.floor((Math.min(getHeight(), getWidth()) * 4.0 / (3.0 * NB_ROWS)) - 1.0);
            Y_OFFSET = TAILLE_CASES / NB_ROWS;

            for (int r = 0; r < NB_ROWS; r++) {
                for (int q = 0; q < (r % 2 == 0 ? plateau.getNbColumns() - 1 : plateau.getNbColumns()); q++) {
                    Coord coord = new Coord(q, r);
                    final int TYPE_TUILE = plateau.get(coord);

                    final int x = XHexToPixel(q, r);
                    final int y = YHexToPixel(r);

                    Image img;
                    if (jeu.estPion(coord) && TYPE_TUILE > 0) {
                        img = sprites[jeu.joueurDePion(coord) + 1][TYPE_TUILE - 1];
                    } else {
                        img = sprites[0][TYPE_TUILE];
                    }
                    drawable.drawImage(img, x, y, (int) TAILLE_CASES, (int) TAILLE_CASES, null);

                    if (tuilesSurbrillance != null && tuilesSurbrillance.contains(coord)) {
                        ajouterSurbrillance(drawable, q, r, SURBRIANLLANCE);
                    }
                }
            }
        }
    }

    private void ajouterSurbrillance(Graphics2D g, int q, int r, Color color) {
        final int x = XHexToPixel(q, r) + (int) (TAILLE_CASES / 2.0);
        final int y = YHexToPixel(r) + (int) (TAILLE_CASES / 2.0);

        Polygon hexagone = new Polygon();

        for (int i = 0; i < 6; i++) {
            double angle_deg = 60.0 * i - 30.0;
            double angle_rad = Math.PI / 180.0 * angle_deg;
            hexagone.addPoint(
                    (int) (x + (TAILLE_CASES / 2.2) * Math.cos(angle_rad)),
                    (int) (y + (TAILLE_CASES / 2.2) * Math.sin(angle_rad))
            );
        }

        Color saveColor = g.getColor();
        g.setColor(color);
        g.fillPolygon(hexagone);
        g.setColor(saveColor);
    }

    private int XHexToPixel(int q, int r) {
        return (int) ((TAILLE_CASES / 2.0) + ((TAILLE_CASES / 2.2) * Math.sqrt(3.0) * (q - 0.5 * (r & 1))));
    }

    private int YHexToPixel(int r) {
        return (int) (Y_OFFSET + ((TAILLE_CASES / 2.2) * (3.0 / 2.0) * r));
    }

    public Coord getClickedTuile(int x, int y) {
        double r = (2.0 / 3.0) * (2.2 / TAILLE_CASES) * (y - Y_OFFSET);
        int rInt = (int) (r - (r % 1 < 0.5 ? 1.0 : 0.0));
        double q = (2.2 / TAILLE_CASES) * (1.0 / Math.sqrt(3.0)) * (x - (TAILLE_CASES / 2.0)) + 0.5 * (rInt & 1);

        return new Coord((int) q, rInt);
    }

    public void setTuilesSurbrillance(List<Coord> tuilesSurbrillance) {
        this.tuilesSurbrillance = tuilesSurbrillance;
    }
}
