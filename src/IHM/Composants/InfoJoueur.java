package IHM.Composants;

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

    public InfoJoueur(Joueur joueur) {
        super();
        this.joueur = joueur;
        setLayout(new BorderLayout());

        JLabel nom = new JLabel("Joueur " + (joueur.getID() + 1));
        nom.setFont(getFont(35));
        add(nom, BorderLayout.NORTH);

        JPanel infos = new JPanel(new GridLayout(1, 0));
        infos.setBackground(Color.BLUE);
        infos.setBackground(new Color(0, 0, 0, 0));
        infos.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        nbPoissons = new JLabel("Nombre de poissons : " + joueur.getScore());
        nbPoissons.setFont(getFont(25));
        nbPoissons.setIcon(resizeIcon(FISH, 60, 60));
        infos.add(nbPoissons);

        add(Box.createHorizontalGlue());

        nbTuiles = new JLabel("Nombre de tuiles : " + joueur.getTuiles());
        nbTuiles.setIcon(resizeIcon(ICE, 60, 60));
        nbTuiles.setFont(getFont(25));
        infos.add(nbTuiles);

        add(infos, BorderLayout.CENTER);

        setBorder(new EmptyBorder(0, 20, 0, 0));
    }

    public void update(boolean estActif) {
        nbPoissons.setText("x : " + joueur.getScore());
        nbTuiles.setText("x " + joueur.getTuiles());

        Border lineBottom = BorderFactory.createMatteBorder(0, -1, 5, -1, Color.BLACK);
        Border padding = BorderFactory.createEmptyBorder(15, 20, 0, 0);

        setBorder(BorderFactory.createCompoundBorder(lineBottom, padding));

        setBackground(estActif ? Color.red : new Color(0, 0, 0, 0));
    }


    public int getJoueurID() {
        return joueur.getID();
    }

    private Font getFont(int taille) {
        return new Font("Arial", Font.PLAIN, taille);
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }
}
