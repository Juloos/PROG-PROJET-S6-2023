package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.Couleurs;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUp;
import IHM.Graphique.PopUp.PopUpConfirmation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EcranAccueil extends Ecran {

    Button nouvellePartie;
    Button chargerPartie;
    Button options;
    Button quitter;
    JButtonIcon rules;

    public EcranAccueil() {
        super("Accueil");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        ihm.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        // Chargement de l'image de fond
        ImageIcon icon = new ImageIcon("res/fondsEcrans/background.png");
        this.backgroundImage = icon.getImage();

        panel.setLayout(new GridLayout(3, 3));

        JPanel bouttons = new JPanel(new GridLayout(0, 1));
        bouttons.setBackground(Couleurs.TRANSPARENT);

        // Le menu pour ouvrir le menu de création d'une nouvelle partie
        nouvellePartie = new Button("Nouvelle partie");
        nouvellePartie.setForeground(Couleurs.COULEUR_FOND);
        nouvellePartie.setFont(new Font("Forte", Font.PLAIN, 25));

        // Centrer le texte sur l'icône
        nouvellePartie.setHorizontalTextPosition(JButton.CENTER);
        nouvellePartie.setVerticalTextPosition(JButton.CENTER);

        nouvellePartie.setContentAreaFilled(false);

        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranCreationPartie());
            }
        });

        // Le bouton pour ouvrir le menu de chargement d'une partie
        chargerPartie = new Button("Charger partie");
        chargerPartie.setForeground(Couleurs.COULEUR_FOND);
        chargerPartie.setFont(new Font("Forte", Font.PLAIN, 25));
        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranChargerPartie());
            }
        });

        // Le bouton pour ouvrir le menu des options
        options = new Button("Options");
        options.setForeground(Couleurs.COULEUR_FOND);
        options.setFont(new Font("Forte", Font.PLAIN, 25));
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });

        // Le bouton pour quitter le jeu
        quitter = new Button("Quitter");
        quitter.setForeground(Couleurs.COULEUR_FOND);
        quitter.setFont(new Font("Forte", Font.PLAIN, 25));
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PopUp popUp = new PopUpConfirmation(ihm, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.getMoteurJeu().terminer();
                    }
                });
                popUp.init(ihm);
                popUp.setVisible(true);
            }
        });

        rules = new JButtonIcon(new ImageIcon("res/rules.png"), 100, 100);
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new java.io.File("res/rules.pdf"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        bouttons.add(nouvellePartie);
        bouttons.add(chargerPartie);
        bouttons.add(options);
        bouttons.add(quitter);

        JPanel horizontal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontal.setBackground(Couleurs.TRANSPARENT);
        horizontal.add(rules);

        for (int i = 0; i < 9; i++) {
            if (i == 8) {
                JPanel rulesPanel = new JPanel(new BorderLayout());
                rulesPanel.setBackground(Couleurs.TRANSPARENT);
                rulesPanel.add(horizontal, BorderLayout.SOUTH);
                panel.add(rulesPanel);
            } else if (i == 4) {
                panel.add(bouttons);
            } else {
                JPanel vide = new JPanel();
                vide.setBackground(Couleurs.TRANSPARENT);
                panel.add(vide);
            }
        }
    }

    @Override
    public void resized() {
        int width = panel.getWidth();
        int height = panel.getHeight();
        int buttonWidth = (int) (width * 0.3);
        int buttonHeight = (int) (height * 0.06);
        Font buttonFont = new Font("Forte", Font.PLAIN, (int) (height * 0.05));

        nouvellePartie.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        nouvellePartie.setFont(buttonFont);

        chargerPartie.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        chargerPartie.setFont(buttonFont);

        options.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        options.setFont(buttonFont);

        quitter.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        quitter.setFont(buttonFont);
    }
}
