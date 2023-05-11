package IHM.Graphique.Composants;

import IHM.Graphique.Couleurs;
import Modele.Joueurs.Joueur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoJoueur extends JPanel {
    private static final ImageIcon ARROW_PLAYER_ACTIVE = new ImageIcon("res/arrow_player.png");
    private static final ImageIcon FISH = new ImageIcon("res/fish.png");
    private static final ImageIcon ICE = new ImageIcon("res/ice.png");
    private final boolean isLast;
    private final Joueur joueur;
    JLabel nom, nbPoissons, nbTuiles;

    public InfoJoueur(Joueur joueur, boolean isLast) {
        super();
        this.isLast = isLast;
        this.joueur = joueur;

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
        nbPoissons.setIcon(resizeIcon(FISH, 80, 80));
        infos.add(nbPoissons);

        add(Box.createHorizontalGlue());

        nbTuiles = new JLabel("x 0");
        nbTuiles.setIcon(resizeIcon(ICE, 30, 30));
        nbTuiles.setFont(new Font("Arial", Font.PLAIN, 20));
        infos.add(nbTuiles);

        add(infos, BorderLayout.CENTER);

        setBorder(new EmptyBorder(0, 20, 0, 0));
    }

    public void update(boolean isActive) {
        nbPoissons.setText("x " + joueur.getScore());
        nbTuiles.setText("x " + joueur.getTuiles());

        setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 0));
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }
}
