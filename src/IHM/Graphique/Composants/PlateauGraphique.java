package IHM.Graphique.Composants;

import IHM.Graphique.Couleurs;
import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupDeplacement;
import Modele.Jeux.Jeu;
import Modele.Jeux.JeuConcret;
import Modele.Plateau;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class PlateauGraphique extends JComponent implements Runnable {

    int BORDURES_X = 2, BORDURES_Y = 1;
    Image[][] sprites;
    Image MOVE_ARROW_SOURCE, MOVE_ARROW_DEST;
    double TAILLE_CASES, Y_OFFSET, ESPACEMENT_TUILES;
    List<Coord> tuilesSurbrillance, pionsSurbrillance;
    Image ARROW_PLAYER_ACTIVE;
    int arrow_X, arrow_Y, arrow_Width, arrow_Height;
    private JeuConcret jeu;
    private volatile int placementPingouinX, placementPingouinY;

    public PlateauGraphique() {
        super();
        this.jeu = null;
        ESPACEMENT_TUILES = 2.2;
        sprites = new Image[5][4];
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

    private synchronized Jeu getJeu() {
        return jeu;
    }

    public synchronized void setJeu(JeuConcret jeu) {
        this.jeu = new JeuConcret(jeu);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        if (jeu != null) {
            Plateau plateau = jeu.getPlateau();
            final int NB_ROWS = plateau.getNbRows();
            TAILLE_CASES = Math.floor((Math.min(getHeight(), getWidth() - (getWidth() / 7)) * 4.0 / (3.0 * NB_ROWS)) - 1.0);
            Y_OFFSET = TAILLE_CASES / NB_ROWS;
            BORDURES_X = (int) Math.floor((getWidth() - plateau.getNbColumns() * (TAILLE_CASES / ESPACEMENT_TUILES)) / TAILLE_CASES);

            final Coord placementPingouin = placementPingouinX >= 0 && placementPingouinY >= 0 ? getClickedTuile(placementPingouinX, placementPingouinY) : null;

            for (int r = -1; r < NB_ROWS + 2; r++) {
                for (int q = -BORDURES_X; q < (r % 2 == 0 ? plateau.getNbColumns() - 1 + BORDURES_X : plateau.getNbColumns() + BORDURES_X); q++) {
                    Coord coord = new Coord(q, r);
                    final int TYPE_TUILE = plateau.estCoordValide(coord) ? plateau.get(coord) : 0;

                    final int x = XHexToPixel(q, r);
                    final int y = YHexToPixel(r);

                    Image img;
                    if (jeu.estPion(coord) && TYPE_TUILE > 0) {
                        img = sprites[jeu.joueurDePion(coord) + 1][TYPE_TUILE];
                    } else if (coord.equals(placementPingouin) && TYPE_TUILE == 1) {
                        img = sprites[jeu.getJoueur().getID() + 1][TYPE_TUILE];
                    } else {
                        img = sprites[0][TYPE_TUILE];
                    }

                    drawable.drawImage(img, x, y, (int) TAILLE_CASES, (int) TAILLE_CASES, null);

                    if (tuilesSurbrillance != null && tuilesSurbrillance.contains(coord)) {
                        ajouterSurbrillance(drawable, q, r, jeu.estPion(coord) ? Couleurs.SURBRILLANCE_PION : Couleurs.SURBRILLANCE);
                    } else if (pionsSurbrillance != null && pionsSurbrillance.contains(coord)) {
                        ajouterSurbrillance(drawable, q, r, Couleurs.SURBRILLANCE_PION);
                    }
                }
            }

            if (placementPingouin != null && jeu.getPlateau().get(placementPingouin) != 1) {
                drawable.drawImage(sprites[jeu.getJoueur().getID() + 1][0], (int) Math.round(placementPingouinX - (TAILLE_CASES / 2.0)),
                        (int) Math.round(placementPingouinY - (TAILLE_CASES / 1.25)),
                        (int) TAILLE_CASES,
                        (int) TAILLE_CASES,
                        null);
            }

            drawable.drawImage(ARROW_PLAYER_ACTIVE, arrow_X, arrow_Y, arrow_Width, arrow_Height, null);

            Coup coup = jeu.dernierCoupJoue();
            if (coup instanceof CoupDeplacement) {
                CoupDeplacement deplacement = (CoupDeplacement) coup;

                int x = XHexToPixel(deplacement.source.q, deplacement.source.r) + (int) (TAILLE_CASES / 2.0);
                int y = YHexToPixel(deplacement.source.r) + (int) (TAILLE_CASES / 2.0);

                drawable.drawImage(MOVE_ARROW_SOURCE, x, y, (int) (TAILLE_CASES / 3.0), (int) (TAILLE_CASES / 3.0), null);

                x = XHexToPixel(deplacement.destination.q, deplacement.destination.r) + (int) (TAILLE_CASES / 2.0);
                y = YHexToPixel(deplacement.destination.r) + (int) (TAILLE_CASES / 2.0);

                drawable.drawImage(MOVE_ARROW_DEST, x, y, (int) (TAILLE_CASES / 3.0), (int) (TAILLE_CASES / 3.0), null);
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
                    (int) (x + (TAILLE_CASES / ESPACEMENT_TUILES) * Math.cos(angle_rad)),
                    (int) (y + (TAILLE_CASES / ESPACEMENT_TUILES) * Math.sin(angle_rad))
            );
        }

        Color saveColor = g.getColor();
        g.setColor(color);
        g.fillPolygon(hexagone);
        g.setColor(saveColor);
    }

    private int XHexToPixel(int q, int r) {
        return (int) ((TAILLE_CASES / 2.0) + ((TAILLE_CASES / ESPACEMENT_TUILES) * Math.sqrt(3.0) * (q - 0.5 * (r & 1))));
    }

    private int YHexToPixel(int r) {
        return (int) (Y_OFFSET + ((TAILLE_CASES / ESPACEMENT_TUILES) * (3.0 / 2.0) * r));
    }

    public Coord getClickedTuile(int x, int y) {
        double r = (2.0 / 3.0) * (ESPACEMENT_TUILES / TAILLE_CASES) * (y - Y_OFFSET);
        int rInt = (int) (r - (r % 1 < 0.5 ? 1.0 : 0.0));
        double q = (ESPACEMENT_TUILES / TAILLE_CASES) * (1.0 / Math.sqrt(3.0)) * (x - (TAILLE_CASES / 2.0)) + 0.5 * (rInt & 1);

        return new Coord((int) q, rInt);
    }

    public synchronized void setTuilesSurbrillance(List<Coord> tuilesSurbrillance) {
        this.tuilesSurbrillance = tuilesSurbrillance;
        repaint();
    }

    public synchronized void setPionsSurbrillance(List<Coord> pions) {
        pionsSurbrillance = pions;
        repaint();
    }

    public synchronized void setPositionFlecheJoueurActif(int x, int y, int width, int height) {
        this.arrow_X = x;
        this.arrow_Y = y;
        this.arrow_Width = width;
        this.arrow_Height = height;
        repaint();
    }

    public synchronized void setPlacementPingouin(int x, int y) {
        this.placementPingouinX = x;
        this.placementPingouinY = y;
        repaint();
    }

    /**
     * Méthode de Thread utilisée pour charger les sprites
     */
    @Override
    public void run() {
        String chemin = "res/";
        int i = 0;
        for (String fileName : new String[]{"PingouinRouge.png", "PingouinBleu.png", "PingouinVert.png", "PingouinJaune.png"}) {
            sprites[i + 1][0] = chargerImage(chemin + fileName);
            i++;
        }

        chemin = "res/tuiles/";

        i = 0;
        for (String fileName : new String[]{"eau.png", "1Poisson.png", "2Poissons.png", "3Poissons.png"}) {
            sprites[0][i] = chargerImage(chemin + fileName);
            i++;
        }

        i = 0;
        for (String fileName : new String[]{"1PingouinRouge.png", "2PingouinRouge.png", "3PingouinRouge.png",
                "1PingouinBleu.png", "2PingouinBleu.png", "3PingouinBleu.png",
                "1PingouinVert.png", "2PingouinVert.png", "3PingouinVert.png",
                "1PingouinJaune.png", "2PingouinJaune.png", "3PingouinJaune.png"}) {
            sprites[(i / 3) + 1][(i % 3) + 1] = chargerImage(chemin + fileName);
            i++;
        }

        try {
            InputStream in = new FileInputStream("res/arrow_player.png");
            ARROW_PLAYER_ACTIVE = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            InputStream in = new FileInputStream("res/arrow_move_source.png");
            MOVE_ARROW_SOURCE = ImageIO.read(in);

            in = new FileInputStream("res/arrow_move_dest.png");
            MOVE_ARROW_DEST = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
