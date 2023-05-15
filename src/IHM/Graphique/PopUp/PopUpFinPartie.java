package IHM.Graphique.PopUp;

import IHM.Graphique.Couleurs;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.IHMGraphique;
import Modele.Joueurs.Joueur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpFinPartie extends PopUp {

    public PopUpFinPartie() {
        super("Partie terminée");
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ImageIcon background = new ImageIcon("res/background_victory.jpeg");
        this.backgroundImage = background.getImage();
        JLabel joueurGagnant = new JLabel();

        String text = "";
        JPanel trophies = new JPanel();
        trophies.setLayout(new BoxLayout(trophies, BoxLayout.X_AXIS));
        JPanel nameWinners = new JPanel();
        nameWinners.setLayout(new BoxLayout(nameWinners, BoxLayout.Y_AXIS));
        for (int i : ihm.getMoteurJeu().getJeu().getWinner()) {
            text += ihm.getMoteurJeu().getJeu().getJoueur(i).getNom()  + "   ";
            if (i == 0) {
                ImageIcon red = new ImageIcon("res/victoire/victory_red.png");
                red.setImage(red.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT));
                JLabel redTrophy = new JLabel(red);
                trophies.add(redTrophy);
            }
            if (i == 1) {
                ImageIcon blue = new ImageIcon("res/victoire/victory_blue.png");
                blue.setImage(blue.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT));
                JLabel blueTrophy = new JLabel(blue);
                trophies.add(blueTrophy);
            }
            if (i == 2) {
                ImageIcon green = new ImageIcon("res/victoire/victory_green.png");
                green.setImage(green.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT));
                JLabel greenTrophy = new JLabel(green);
                trophies.add(greenTrophy);
            }
            if (i == 3) {
                ImageIcon yellow = new ImageIcon("res/victoire/victory_yellow.png");
                yellow.setImage(yellow.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT));
                JLabel yellowTrophy = new JLabel(yellow);
                trophies.add(yellowTrophy);
            }
        }
        trophies.setBackground(Couleurs.TRANSPARENT);
        trophies.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(trophies);

        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        joueurGagnant.setText(text);
        joueurGagnant.setFont(new Font("Impact", Font.BOLD, 30));
        nameWinners.add(joueurGagnant);
        nameWinners.setBackground(Couleurs.TRANSPARENT);
        nameWinners.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameWinners);

        panel.add(Box.createRigidArea(new Dimension(0, 90)));

        JButton rejouer = new JButton("Rejouer");
        rejouer.setPreferredSize(new Dimension(200, 50));
        rejouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Joueur[] joueurs = new Joueur[ihm.getMoteurJeu().getJeu().getNbJoueurs()];
                for (int i = 0; i < ihm.getMoteurJeu().getJeu().getNbJoueurs(); i ++){
                    joueurs[i] = ihm.getMoteurJeu().getJeu().getJoueur(i).resetJoueur();
                    joueurs[i].setNom(ihm.getMoteurJeu().getJeu().getJoueur(i).getNom());
                }
                ihm.getMoteurJeu().lancerPartie(joueurs);
            }
        });
        rejouer.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(rejouer);


        JButton retourAccueil = new JButton("Retourner à l'accueil");
        retourAccueil.setPreferredSize(new Dimension(200, 50));
        retourAccueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.fermerFenetres();
                ihm.ouvrirFenetre(new EcranAccueil());
                ihm.updateAffichage();
            }
        });
        retourAccueil.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(retourAccueil);
        panel.repaint();
        panel.revalidate();
    }
    @Override
    public void resized() {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setPreferredSize(new Dimension(frame.getWidth() / 4, frame.getHeight() / 10));
            } else if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setFont(new Font("Impact", Font.BOLD, (int) (frame.getWidth() / 30.0)));
            } else if (component instanceof JPanel) {
                JPanel innerPanel = (JPanel) component;
                if (innerPanel.getComponentCount() == 1 && innerPanel.getComponent(0) instanceof JLabel) {
                    JLabel trophyLabel = (JLabel) innerPanel.getComponent(0);
                    trophyLabel.setPreferredSize(new Dimension(frame.getWidth() / 5, frame.getHeight() / 3));
                } else {
                    int width = frame.getWidth() / 5;
                    int height = frame.getHeight() / 3;
                    Dimension trophySize = new Dimension(width, height);
                    for (Component innerComponent : innerPanel.getComponents()) {
                        if (innerComponent instanceof JLabel) {
                            JLabel trophy = (JLabel) innerComponent;
                            trophy.setPreferredSize(trophySize);
                        }
                    }
                }
            }
        }
        panel.revalidate();
    }
}
