package IHM.Graphique.Composants;

import Modele.Joueurs.Joueur;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoJoueur extends JPanel {

    private static final ImageIcon FISH = new ImageIcon("res/fish.png");
    private static final ImageIcon ICE = new ImageIcon("res/ice.png");
    JLabel nbPoissons, nbTuiles;
    Joueur joueur;

    public InfoJoueur() {
        super();
        setLayout(new BorderLayout());

        JLabel nom = new JLabel("");
        nom.setFont(new Font("Arial", Font.PLAIN, 35));
        add(nom, BorderLayout.NORTH);

        JPanel infos = new JPanel(new GridLayout(1, 0));
        infos.setBackground(Color.BLUE);
        infos.setBackground(new Color(0, 0, 0, 0));
        infos.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        nbPoissons = new JLabel("");
        nbPoissons.setFont(new Font("Arial", Font.BOLD, 25));
        nbPoissons.setIcon(resizeIcon(FISH, 80, 80));
        infos.add(nbPoissons);

        add(Box.createHorizontalGlue());

        nbTuiles = new JLabel("");
        nbTuiles.setIcon(resizeIcon(ICE, 30, 30));
        nbTuiles.setFont(new Font("Arial", Font.PLAIN, 20));
        infos.add(nbTuiles);

        add(infos, BorderLayout.CENTER);

        setBorder(new EmptyBorder(0, 20, 0, 0));
    }

    public void update(Joueur joueur) {
        nbPoissons.setText("x " + joueur.getScore());
        nbTuiles.setText("x " + joueur.getTuiles());

        Border lineBottom = BorderFactory.createMatteBorder(0, -1, 5, -1, Color.BLACK);
        Border padding = BorderFactory.createEmptyBorder(15, 20, 0, 0);

        setBorder(BorderFactory.createCompoundBorder(lineBottom, padding));
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }
}
