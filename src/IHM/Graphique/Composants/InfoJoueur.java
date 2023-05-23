package IHM.Graphique.Composants;

import IHM.Graphique.Couleurs;
import IHM.Graphique.Images;
import Modele.Joueurs.Joueur;

import javax.swing.*;
import java.awt.*;

/**
 * Contient les informations de partie d'un joueur
 */
public class InfoJoueur extends JPanel {
    private static final Image FISH = Images.chargerImage("/icones/fish.png");
    private static final Image ICE = Images.chargerImage("/icones/ice.png");
    JLabel nom, nbPoissons, nbTuiles;

    public InfoJoueur(Joueur joueur) {
        super();

        setLayout(new BorderLayout());
        setOpaque(false);

        nom = new JLabel(joueur.getNom());
        nom.setFont(new Font("Arial", Font.BOLD, 25));
        nom.setForeground(Couleurs.COULEURS_JOUEURS[joueur.getID()]);
        add(nom, BorderLayout.NORTH);

        JPanel infos = new JPanel(new GridLayout(1, 0));
        infos.setOpaque(false);
        infos.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        nbPoissons = new JLabel("x 0");
        nbPoissons.setFont(new Font("Arial", Font.BOLD, 36));
        nbPoissons.setIcon(resizeIcon(FISH, 70, 70));
        infos.add(nbPoissons);

        add(Box.createHorizontalGlue());

        nbTuiles = new JLabel("x 0");
        nbTuiles.setIcon(resizeIcon(ICE, 30, 30));
        nbTuiles.setFont(new Font("Arial", Font.BOLD, 15));
        infos.add(nbTuiles);

        add(infos, BorderLayout.CENTER);

        nom.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 0));
        infos.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));
    }

    /**
     * Met à jour l'affichage du score du joueur
     *
     * @param score  : le score du joueur mis à jour
     * @param tuiles : le nombre de tuiles collectées par le joueur mis à jour
     */
    public void update(int score, int tuiles) {
        nbPoissons.setText("x " + score);
        nbTuiles.setText("x " + tuiles);
        repaint();
    }

    /**
     * Méthode appelée lorsque la fenêtre change de dimensions
     */
    public void resize() {
        nbPoissons.setPreferredSize(new Dimension(getWidth() / 2, getHeight() / 2));

        final int size = (int) (getHeight() * 0.45);
        if (size > 0) {
            nbPoissons.setIcon(resizeIcon(FISH, size, size));
        }
    }

    private ImageIcon resizeIcon(Image image, int width, int height) {
        return new ImageIcon(Images.resizeImage(image, width, height));
    }
}
