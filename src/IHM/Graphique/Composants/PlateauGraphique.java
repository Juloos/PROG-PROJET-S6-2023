package IHM.Graphique.Composants;

import IHM.Graphique.Images.Images;
import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupDeplacement;
import Modele.Jeux.JeuConcret;
import Modele.Plateau;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PlateauGraphique extends JComponent {

    int BORDURES_X = 2, BORDURES_Y = 5;
    double TAILLE_CASES, Y_OFFSET, ESPACEMENT_TUILES;
    List<Coord> tuilesSurbrillance, pionsSurbrillance;
    private int arrow_X, arrow_Y, arrow_Width, arrow_Height;
    private JeuConcret jeu;
    private int placementPingouinX, placementPingouinY;

    private volatile boolean animationEnCours;

    private HashMap<Coord, Color> tuilesSurbrillances;

    public PlateauGraphique() {
        super();
        this.jeu = null;
        ESPACEMENT_TUILES = 2.2;
        tuilesSurbrillances = new HashMap<>();
    }

    public synchronized void setJeu(JeuConcret jeu) {
        this.jeu = jeu != null ? new JeuConcret(jeu) : null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        if (jeu != null) {
            Plateau plateau = jeu.getPlateau();
            final int NB_ROWS = plateau.getNbRows();
            TAILLE_CASES = Math.floor((Math.min(getHeight(), getWidth() - (getWidth() / 7)) * 4.0 / (3.0 * NB_ROWS)) - 1.0);
            Y_OFFSET = TAILLE_CASES / NB_ROWS;
            BORDURES_X = (int) Math.floor((getWidth() - plateau.getNbColumns() * (TAILLE_CASES / ESPACEMENT_TUILES)) / TAILLE_CASES);
            BORDURES_Y = (int) Math.floor((getHeight() - plateau.getNbRows() * (TAILLE_CASES / ESPACEMENT_TUILES)) / TAILLE_CASES);

            final Coord placementPingouin = placementPingouinX >= 0 && placementPingouinY >= 0 ? getClickedTuile(placementPingouinX, placementPingouinY) : null;

            for (int r = -BORDURES_Y; r < NB_ROWS + BORDURES_Y; r++) {
                for (int q = -BORDURES_X; q < (r % 2 == 0 ? plateau.getNbColumns() - 1 + BORDURES_X : plateau.getNbColumns() + BORDURES_X); q++) {
                    Coord coord = new Coord(q, r);
                    final int TYPE_TUILE = plateau.get(coord);

                    final int x = XHexToPixel(q, r);
                    final int y = YHexToPixel(r);

                    Image img;
                    if (jeu.estPion(coord) && TYPE_TUILE > 0) {
                        img = Images.getInstance().getTuile(true, TYPE_TUILE, jeu.joueurDePion(coord));
                    } else if (coord.equals(placementPingouin) && TYPE_TUILE == 1) {
                        img = Images.getInstance().getTuile(true, 1, jeu.getJoueur().getID());
                    } else {
                        img = Images.getInstance().getTuile(false, TYPE_TUILE);
                    }

                    drawable.drawImage(img, x, y, (int) TAILLE_CASES, (int) TAILLE_CASES, null);

                    if (tuilesSurbrillances.containsKey(coord)) {
                        ajouterSurbrillance(drawable, q, r, tuilesSurbrillances.get(coord));
                    }
                }
            }

            if (placementPingouin != null && jeu.getPlateau().get(placementPingouin) != 1) {
                drawable.drawImage(Images.getInstance().getPingouin(jeu.getJoueur().getID()), (int) Math.round(placementPingouinX - (TAILLE_CASES / 2.0)),
                        (int) Math.round(placementPingouinY - (TAILLE_CASES / 1.25)),
                        (int) TAILLE_CASES,
                        (int) TAILLE_CASES,
                        null);
            }

            Coup coup = jeu.dernierCoupJoue();
            if (coup instanceof CoupDeplacement) {
                CoupDeplacement deplacement = (CoupDeplacement) coup;

                int decalage = deplacement.source.getDecalage(deplacement.destination);

                int x = XHexToPixel(deplacement.source.q, deplacement.source.r) + (int) (TAILLE_CASES / 2.0);
                int y = YHexToPixel(deplacement.source.r) + (int) (TAILLE_CASES / 2.0);

                final int size = (int) (TAILLE_CASES / 3.0);

                double rotation = 0.0;
                switch (decalage) {
                    case Coord.HAUT_GAUCHE:
                        rotation = Math.toRadians(-120.0);
                        break;
                    case Coord.BAS_DROITE:
                        rotation = Math.toRadians(60.0);
                        break;
                    case Coord.DROITE:
                        rotation = 0.0;
                        break;
                    case Coord.GAUCHE:
                        rotation = Math.toRadians(180.0);
                        break;
                    case Coord.HAUT_DROITE:
                        rotation = Math.toRadians(-60.0);
                        break;
                    case Coord.BAS_GAUCHE:
                        rotation = Math.toRadians(120.0);
                        break;
                }

                final int offset_x = -size * 3 / 2, offset_y = -size / 2;

                drawable.rotate(rotation, x, y);
                drawable.drawImage(Images.getInstance().getFlecheDeplacement(true),
                        x,
                        y + offset_y,
                        size,
                        size,
                        null);
                drawable.rotate(-rotation, x, y);

                x = XHexToPixel(deplacement.destination.q, deplacement.destination.r) + (int) (TAILLE_CASES / 2.0);
                y = YHexToPixel(deplacement.destination.r) + (int) (TAILLE_CASES / 2.0);

                drawable.rotate(rotation, x, y);
                drawable.drawImage(Images.getInstance().getFlecheDeplacement(false),
                        x + offset_x,
                        y + offset_y,
                        size,
                        size,
                        null);
                drawable.rotate(-rotation, x, y);
            }

            drawable.drawImage(Images.getInstance().getFlecheJoueurActif(), getWidth() - arrow_Width - 10, arrow_Y, arrow_Width, arrow_Height, null);
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

    public synchronized void viderTuilesSurbrillance() {
        tuilesSurbrillances.clear();
    }

    public synchronized void ajouterTuilesSurbrillance(Set<Coord> coords, Color couleur) {
        for (Coord coord : coords) {
            ajouterTuilesSurbrillance(coord, couleur);
        }
        repaint();
    }

    public synchronized void ajouterTuilesSurbrillance(List<Coord> coords, Color couleur) {
        for (Coord coord : coords) {
            ajouterTuilesSurbrillance(coord, couleur);
        }
        repaint();
    }

    public synchronized void ajouterTuilesSurbrillance(Coord coord, Color couleur) {
        tuilesSurbrillances.put(coord, couleur);
    }

    public synchronized void setPositionFlecheJoueurActif(int x, int y) {
        this.arrow_X = x;
        this.arrow_Y = y;
        repaint();
    }

    public synchronized void setDimensionFlecheJoueurActif(int width, int height) {
        this.arrow_Width = width;
        this.arrow_Height = height;
        repaint();
    }

    public synchronized void setPlacementPingouin(int x, int y) {
        this.placementPingouinX = x;
        this.placementPingouinY = y;
        repaint();
    }
}
