package IHM.Graphique.Composants;

import IHM.Graphique.Couleurs;
import IHM.Graphique.Images.Images;
import Modele.Joueurs.Joueur;

import javax.swing.*;
import java.awt.*;

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

    public void update(int score, int tuiles) {
        nbPoissons.setText("x " + score);
        nbTuiles.setText("x " + tuiles);
        repaint();
    }

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
