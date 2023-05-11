package IHM.Graphique.PopUp;

import IHM.Colors;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.IHMGraphique;

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
        panel.setLayout(new BorderLayout());
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
                ImageIcon red = new ImageIcon("res/victory_red.png");
                red.setImage(red.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT));
                JLabel redTrophy = new JLabel(red);
                trophies.add(redTrophy);
                // Ajouter un JLabel vide à gauche de l'image suivante
                trophies.add(Box.createRigidArea(new Dimension(30, 0)));
                trophies.add(redTrophy);

                // Ajouter un JLabel vide à droite de l'image actuelle
                trophies.add(Box.createRigidArea(new Dimension(30, 0)));
            }
            if (i == 1) {
                ImageIcon blue = new ImageIcon("res/victory_blue.png");
                blue.setImage(blue.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT));
                JLabel blueTrophy = new JLabel(blue);
                trophies.add(blueTrophy);
                // Ajouter un JLabel vide à gauche de l'image suivante
                trophies.add(Box.createRigidArea(new Dimension(30, 0)));
                trophies.add(blueTrophy);

                // Ajouter un JLabel vide à droite de l'image actuelle
                trophies.add(Box.createRigidArea(new Dimension(30, 0)));
            }
            if (i == 2) {
                ImageIcon green = new ImageIcon("res/victory_green.png");
                green.setImage(green.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT));
                JLabel greenTrophy = new JLabel(green);
                trophies.add(greenTrophy);
                // Ajouter un JLabel vide à gauche de l'image suivante
                trophies.add(Box.createRigidArea(new Dimension(30, 0)));
                trophies.add(greenTrophy);

                // Ajouter un JLabel vide à droite de l'image actuelle
                trophies.add(Box.createRigidArea(new Dimension(30, 0)));
            }
            if (i == 3) {
                ImageIcon yellow = new ImageIcon("res/victory_yellow.png");
                yellow.setImage(yellow.getImage().getScaledInstance(125, 125, Image.SCALE_DEFAULT));
                JLabel yellowTrophy = new JLabel(yellow);
                trophies.add(yellowTrophy);
                // Ajouter un JLabel vide à gauche de l'image suivante
                trophies.add(Box.createRigidArea(new Dimension(30, 0)));
                trophies.add(yellowTrophy);

                // Ajouter un JLabel vide à droite de l'image actuelle
                trophies.add(Box.createRigidArea(new Dimension(30, 0)));
            }
        }
        trophies.add(Box.createRigidArea(new Dimension(30, 0)));
        trophies.setBackground(Colors.TRANSPARENT);
        panel.add(trophies, BorderLayout.NORTH);

        joueurGagnant.setText(text);
        joueurGagnant.setFont(new Font("Forte", Font.BOLD, 30));
        nameWinners.add(joueurGagnant);
        nameWinners.setBackground(Colors.TRANSPARENT);
        nameWinners.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameWinners, BorderLayout.CENTER);

        JButton retourAccueil = new JButton("Retourner à l'accueil");
        retourAccueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.fermerFenetres();
                ihm.ouvrirFenetre(new EcranAccueil());
                ihm.updateAffichage();
            }
        });
        panel.add(retourAccueil, BorderLayout.SOUTH);
        panel.repaint();
        panel.revalidate();
    }
    @Override
    public void resized() {
        super.resized();
        panel.repaint();
        panel.revalidate();
    }
}
