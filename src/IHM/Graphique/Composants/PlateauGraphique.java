package IHM.Graphique.Composants;

import IHM.Graphique.Images;
import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupDeplacement;
import Modele.Jeux.JeuConcret;
import Modele.Plateau;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Représentation graphique du plateau de jeu
 */
public class PlateauGraphique extends JComponent {

    private final HashMap<Coord, Color> tuilesSurbrillances;
    int BORDURES_X = 2, BORDURES_Y = 5;
    double TAILLE_CASES, Y_OFFSET, ESPACEMENT_TUILES;
    private int arrow_Y, arrow_Width, arrow_Height;
    /**
     * Le plateau dessiné par le plateau graphique
     */
    private JeuConcret jeu;
    /**
     * Position du pingouin flottant lors de la phase de placements des pingouins
     */
    private int placementPingouinX, placementPingouinY;

    public PlateauGraphique() {
        super();
        this.jeu = null;
        ESPACEMENT_TUILES = 2.18;
        tuilesSurbrillances = new HashMap<>();
    }

    public synchronized void setJeu(JeuConcret jeu) {
        this.jeu = jeu != null ? new JeuConcret(jeu) : null;
    }

    /**
     * Dessine le plateau sur la fenêtre
     *
     * @param g : graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        if (jeu != null) {
            // On a un plateau a dessiner

            // Déclaration et initialisation des variables
            Plateau plateau = jeu.getPlateau();
            final int NB_ROWS = plateau.getNbRows();
            TAILLE_CASES = Math.floor((Math.min(getHeight(), getWidth() - (getWidth() / 7)) * 4.0 / (3.0 * NB_ROWS)) - 1.0);
            Y_OFFSET = TAILLE_CASES / NB_ROWS;
            BORDURES_X = (int) Math.floor((getWidth() - plateau.getNbColumns() * (TAILLE_CASES / ESPACEMENT_TUILES)) / TAILLE_CASES);
            BORDURES_Y = (int) Math.floor((getHeight() - plateau.getNbRows() * (TAILLE_CASES / ESPACEMENT_TUILES)) / TAILLE_CASES);
            Coord placementPingouin = getTuile(placementPingouinX, placementPingouinY);

            // Dessin des tuiles
            for (int r = -BORDURES_Y; r < NB_ROWS + BORDURES_Y; r++) {
                for (int q = -BORDURES_X; q < (r % 2 == 0 ? plateau.getNbColumns() - 1 + BORDURES_X : plateau.getNbColumns() + BORDURES_X); q++) {
                    Coord coord = new Coord(q, r);
                    final int TYPE_TUILE = plateau.get(coord);

                    final int x = XHexToPixel(q, r);
                    final int y = YHexToPixel(r);

                    // Choix de l'image de la tuile
                    Image img;
                    if (jeu.estPion(coord) && TYPE_TUILE > 0) {
                        img = Images.getInstance().getTuile(true, TYPE_TUILE, jeu.joueurDePion(coord));
                    } else if (coord.equals(placementPingouin) && TYPE_TUILE == 1) {
                        img = Images.getInstance().getTuile(true, 1, jeu.getJoueur().getID());
                    } else {
                        img = Images.getInstance().getTuile(false, TYPE_TUILE);
                    }

                    // Dessin de la tuile
                    drawable.drawImage(img, x, y, (int) TAILLE_CASES, (int) TAILLE_CASES, null);

                    if (tuilesSurbrillances.containsKey(coord)) {
                        // Ajout d'une surbrillance sur la tuile si demandée
                        ajouterSurbrillance(drawable, q, r, tuilesSurbrillances.get(coord));
                    }
                }
            }

            if (placementPingouin == null && placementPingouinX >= 0 && placementPingouinY >= 0) {
                drawable.drawImage(Images.getInstance().getPingouin(jeu.getJoueur().getID()), (int) Math.round(placementPingouinX - (TAILLE_CASES / 1.9)),
                        (int) Math.round(placementPingouinY - (TAILLE_CASES / 1.35)),
                        (int) TAILLE_CASES,
                        (int) TAILLE_CASES,
                        null);
            }

            Coup coup = jeu.dernierCoupJoue();
            if (coup instanceof CoupDeplacement) {
                // Dessin des indiquations du dernier déplacement effectué
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

            // Dessin de la flèche pour indiquer le joueur actif
            drawable.drawImage(Images.getInstance().getFlecheJoueurActif(), getWidth() - arrow_Width - 10, arrow_Y, arrow_Width, arrow_Height, null);

            drawable.setColor(Color.BLACK);
        }
    }

    /**
     * Ajout de la surbrillance sur une tuile
     *
     * @param g     graphics
     * @param q     le numéro de colonne de la tuile
     * @param r     le numéro de ligne de la tuile
     * @param color la couleur de la surbrillance de la tuile
     */
    private void ajouterSurbrillance(Graphics2D g, int q, int r, Color color) {
        final int x = XHexToPixel(q, r) + (int) (TAILLE_CASES / 2.0);
        final int y = YHexToPixel(r) + (int) (TAILLE_CASES / 2.0);

        Polygon hexagone = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle_deg = 60.0 * i - 30.0;
            double angle_rad = Math.PI / 180.0 * angle_deg;
            hexagone.addPoint(
                    (int) Math.floor(x + (TAILLE_CASES / ESPACEMENT_TUILES * 1.01) * Math.cos(angle_rad)),
                    (int) Math.floor(y + (TAILLE_CASES / ESPACEMENT_TUILES * 1.01) * Math.sin(angle_rad))
            );
        }

        Color saveColor = g.getColor();
        g.setColor(color);
        g.fillPolygon(hexagone);
        g.setColor(saveColor);
    }

    /**
     * Trouve la position sur l'axe X de la tuile aux coordonées (q, r)
     *
     * @param q le numéro de colonne de la tuile
     * @param r le numéro de ligne de la tuile
     * @return la position de la tuile sur l'axe X
     */
    private int XHexToPixel(int q, int r) {
        return (int) Math.round((TAILLE_CASES / 2.0) + ((TAILLE_CASES / ESPACEMENT_TUILES) * Math.sqrt(3.0) * (q - 0.5 * (r % 2))));
    }

    /**
     * Trouve la position sur l'axe Y de la tuile aux coordonées (q, r)
     *
     * @param r le numéro de ligne de la tuile
     * @return la position de la tuile sur l'axe Y
     */
    private int YHexToPixel(int r) {
        return (int) Math.round(Y_OFFSET + ((TAILLE_CASES / ESPACEMENT_TUILES) * (3.0 / 2.0) * r));
    }

    /**
     * Retourne les coordonnées de la tuile aux coordonées (x, y)
     *
     * @param x position sur l'axe X du clic
     * @param y position sur l'axe Y du clic
     * @return les coordonnées de la tuile cliquée sur le plateau
     */
    public synchronized Coord getTuile(int x, int y) {
        Coord coord = null;
        Point point = new Point(x, y - (int) (TAILLE_CASES / ESPACEMENT_TUILES / 2));
        int r = 0;
        int q = 0;

        while (r < jeu.getPlateau().getNbRows() && coord == null) {
            q = 0;
            while (q < jeu.getPlateau().getNbColumns() && coord == null) {
                final int tuileX = XHexToPixel(q, r) + (int) (TAILLE_CASES / 2.0);
                final int tuileY = YHexToPixel(r) + (int) (TAILLE_CASES / 2.0);

                Polygon hexagone = new Polygon();
                for (int i = 0; i < 6; i++) {
                    double angle_deg = 60.0 * i - 30.0;
                    double angle_rad = Math.PI / 180.0 * angle_deg;
                    hexagone.addPoint(
                            (int) Math.floor(tuileX + (TAILLE_CASES / ESPACEMENT_TUILES) * Math.cos(angle_rad)),
                            (int) Math.floor(tuileY + (TAILLE_CASES / ESPACEMENT_TUILES) * Math.sin(angle_rad))
                    );
                }

                if (hexagone.contains(point)) {
                    coord = new Coord(q, r);
                }
                q++;
            }
            r++;
        }
        return coord;
    }

    /**
     * Enlève la surbrillance sur toutes les tuiles en surbrillance
     */
    public synchronized void viderTuilesSurbrillance() {
        tuilesSurbrillances.clear();
    }

    /**
     * Ajout des tuiles en surbrillance de la même couleur
     *
     * @param coords  la liste des tuiles à mettre en surbrillance
     * @param couleur la couleur de la surbrillance
     */
    public synchronized void ajouterTuilesSurbrillance(List<Coord> coords, Color couleur) {
        for (Coord coord : coords) {
            ajouterTuilesSurbrillance(coord, couleur);
        }
        repaint();
    }

    /**
     * Ajout des tuiles en surbrillance de la même couleur
     *
     * @param coord   la tuile à mettre en surbrillance
     * @param couleur la couleur de la surbrillance
     */
    public synchronized void ajouterTuilesSurbrillance(Coord coord, Color couleur) {
        tuilesSurbrillances.put(coord, couleur);
    }

    /**
     * Met à jour la position sur l'axe Y de la flèche indiquant le joueur actif
     *
     * @param y position sur l'axe Y
     */
    public synchronized void setPositionFlecheJoueurActif(int y) {
        this.arrow_Y = y;
        repaint();
    }

    /**
     * Met à jour les dimensions de la flèche indiquant le joueur actif
     *
     * @param width  la largeur de la flèche
     * @param height la hauteur de la flèche
     */
    public synchronized void setDimensionFlecheJoueurActif(int width, int height) {
        this.arrow_Width = width;
        this.arrow_Height = height;
        repaint();
    }

    /**
     * Met à jour la position du pingouin flottant lors de la phase de placement des pingouins
     *
     * @param x position sur l'axe X
     * @param y position sur l'axe Y
     */
    public synchronized void setPlacementPingouin(int x, int y) {
        this.placementPingouinX = x;
        this.placementPingouinY = y;
        repaint();
    }
}
