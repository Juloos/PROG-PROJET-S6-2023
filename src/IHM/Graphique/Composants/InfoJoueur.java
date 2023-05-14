package IHM.Graphique.Composants;

import IHM.Graphique.Couleurs;
import Modele.Joueurs.Joueur;

import javax.swing.*;
import java.awt.*;

public class InfoJoueur extends JPanel {
    private static final ImageIcon FISH = new ImageIcon("res/fish.png");
    private static final ImageIcon ICE = new ImageIcon("res/ice.png");
    JLabel nom, nbPoissons, nbTuiles;

    public InfoJoueur(Joueur joueur) {
        super();

        setLayout(new BorderLayout());

        nom = new JLabel(joueur.getNom());
        nom.setFont(new Font("Arial", Font.PLAIN, 35));
        nom.setForeground(Couleurs.COULEURS_JOUEURS[joueur.getID()]);
        add(nom, BorderLayout.NORTH);

        JPanel infos = new JPanel(new GridLayout(1, 0));
        infos.setBackground(Color.BLUE);
        infos.setBackground(new Color(0, 0, 0, 0));
        infos.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        nbPoissons = new JLabel("x 0");
        nbPoissons.setFont(new Font("Arial", Font.BOLD, 25));
        nbPoissons.setIcon(resizeIcon(FISH, 70, 70));
        infos.add(nbPoissons);

        add(Box.createHorizontalGlue());

        nbTuiles = new JLabel("x 0");
        nbTuiles.setIcon(resizeIcon(ICE, 30, 30));
        nbTuiles.setFont(new Font("Arial", Font.PLAIN, 20));
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

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }
}
