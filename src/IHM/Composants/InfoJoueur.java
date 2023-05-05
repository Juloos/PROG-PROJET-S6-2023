package IHM.Composants;

import Modele.Joueurs.Joueur;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoJoueur extends JPanel {

    JLabel nbPoissons, nbTuiles;

    Joueur joueur;

    public InfoJoueur(Joueur joueur) {
        super();
        this.joueur = joueur;
        setLayout(new GridLayout(0, 1));
//        setBorder(BorderFactory.createMatteBorder(-1, -1, 100, -1, Color.BLACK));
//        setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));

        JLabel nom = new JLabel("Joueur " + (joueur.getID() + 1));
        add(nom);

        nbPoissons = new JLabel("Nombre de poissons : " + joueur.getScore());
        add(nbPoissons);

        nbTuiles = new JLabel("Nombre de tuiles : " + joueur.getTuiles());
        add(nbTuiles);

        setBorder(new EmptyBorder(0, 20, 0, 0));
    }

    public void update(boolean estActif) {
        nbPoissons.setText("Nombre de poissons : " + joueur.getScore());
        nbTuiles.setText("Nombre de tuiles : " + joueur.getTuiles());

        Border lineBottom = BorderFactory.createMatteBorder(0, -1, 5, -1, Color.BLACK);
        Border paddingLeft = BorderFactory.createEmptyBorder(0, 20, 0, 0);

        setBorder(BorderFactory.createCompoundBorder(lineBottom, paddingLeft));

        setBackground(estActif ? Color.red : new Color(0, 0, 0, 0));
    }

    public int getJoueurID() {
        return joueur.getID();
    }
}
