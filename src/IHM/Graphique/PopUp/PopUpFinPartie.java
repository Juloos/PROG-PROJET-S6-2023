package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.Couleurs;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images;
import Modele.Joueurs.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pop-up affiché lorsqu'une partie est terminée
 */
public class PopUpFinPartie extends PopUp {

    public PopUpFinPartie(IHMGraphique ihm) {
        super(ihm, "Partie terminée", 700, 500);
    }

    /* Méthodes héritées */
    @Override
    public void init(IHMGraphique ihm) {
        this.backgroundImage = Images.chargerImage("/fondsEcrans/background_victory.jpeg");

        setLayout(new BorderLayout());

        JPanel trophies = new JPanel(new FlowLayout());
        for (int i : ihm.getMoteurJeu().getJeu().getWinner()) {
            String text = ihm.getMoteurJeu().getJeu().getJoueur(i).getNom() + "   ";
            ImageIcon icon = null;
            int size = 145;

            if (i == 0) {
                icon = new ImageIcon(Images.resizeImage(Images.chargerImage("/victoire/victory_red.png"), size, size));
            } else if (i == 1) {
                icon = new ImageIcon(Images.resizeImage(Images.chargerImage("/victoire/victory_blue.png"), size, size));
            } else if (i == 2) {
                icon = new ImageIcon(Images.resizeImage(Images.chargerImage("/victoire/victory_green.png"), size, size));
            } else if (i == 3) {
                icon = new ImageIcon(Images.resizeImage(Images.chargerImage("/victoire/victory_yellow.png"), size, size));
            }

            Winner winner = new Winner(text, icon);
            winner.setAlignmentX(Component.CENTER_ALIGNMENT);
            trophies.add(winner);
        }
        trophies.setBackground(Couleurs.TRANSPARENT);
        trophies.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(trophies, BorderLayout.NORTH);

        JPanel bouttons = new JPanel();
        bouttons.setOpaque(false);

        Button rejouer = new Button("Rejouer");
        rejouer.setPreferredSize(new Dimension(200, 50));
        rejouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Joueur[] joueurs = new Joueur[ihm.getMoteurJeu().getJeu().getNbJoueurs()];
                for (int i = 0; i < ihm.getMoteurJeu().getJeu().getNbJoueurs(); i++) {
                    joueurs[i] = ihm.getMoteurJeu().getJeu().getJoueur(i).resetJoueur();
                    joueurs[i].setNom(ihm.getMoteurJeu().getJeu().getJoueur(i).getNom());
                }
                close();
                ihm.getMoteurJeu().lancerPartie(joueurs);
            }
        });
        rejouer.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouttons.add(rejouer);


        Button retourAccueil = new Button("Retourner à l'accueil");
        retourAccueil.setPreferredSize(new Dimension(200, 50));
        retourAccueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.fermerFenetres();
                close();
                ihm.ouvrirFenetre(new EcranAccueil());
                ihm.updateAffichage(false);
            }
        });
        retourAccueil.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouttons.add(retourAccueil);

        add(bouttons, BorderLayout.SOUTH);
    }

    private class Winner extends JPanel {

        public Winner(String nom, ImageIcon icon) {
            super();
            setBackground(Couleurs.TRANSPARENT);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            JLabel name = new JLabel(nom);
            name.setFont(new Font("Impact", Font.BOLD, 40));

            JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);

            add(iconLabel);
            add(Box.createRigidArea(new Dimension(0, 40)));
            add(name);
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }
    }
}
