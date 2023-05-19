package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images.Images;
import IHM.Graphique.PopUp.PopUp;
import IHM.Graphique.PopUp.PopUpConfirmation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class EcranAccueil extends Ecran {

    JButtonIcon nouvellePartie;
    JButtonIcon chargerPartie;
    JButtonIcon options;
    JButtonIcon quitter;
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
        this.backgroundImage = Images.chargerImage("/fondsEcrans/background.png");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel bouttons = new JPanel();
        bouttons.setLayout(new BoxLayout(bouttons, BoxLayout.Y_AXIS));
        bouttons.setOpaque(false);

        // Le menu pour ouvrir le menu de création d'une nouvelle partie
        nouvellePartie = new JButtonIcon(Images.chargerImage("/boutons/Nouvelle_partie.png"), 370, 130);
        nouvellePartie.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        nouvellePartie.setAlignmentX(Component.CENTER_ALIGNMENT);
        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranCreationPartie());
            }
        });

        // Le bouton pour ouvrir le menu de chargement d'une partie
        chargerPartie = new JButtonIcon(Images.chargerImage("/boutons/charger_partie.png"), 340, 100);
        chargerPartie.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chargerPartie.setAlignmentX(Component.CENTER_ALIGNMENT);
        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranChargerPartie());
            }
        });

        // Le bouton pour ouvrir le menu des options
        options = new JButtonIcon(Images.chargerImage("/boutons/option.png"), 270, 80);
        options.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        options.setContentAreaFilled(false);
        options.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });

        // Le bouton pour quitter le jeu
        quitter = new JButtonIcon(Images.chargerImage("/boutons/quitter.png"), 270, 80);
        quitter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        quitter.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        rules = new JButtonIcon(Images.chargerImage("/boutons/Regles.png"), 100, 150);
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File("res/rules.pdf"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel horizontal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontal.setOpaque(false);
        horizontal.add(rules);

        bouttons.add(Box.createVerticalGlue());
        bouttons.add(nouvellePartie);
        bouttons.add(chargerPartie);
        bouttons.add(Box.createVerticalGlue());
        bouttons.add(options);
        bouttons.add(Box.createVerticalGlue());
        bouttons.add(quitter);

        panel.add(Box.createRigidArea(new Dimension(0, panel.getHeight() / 3)));
        panel.add(bouttons);
        panel.add(horizontal);
    }

    @Override
    public void resized() {
        int width = panel.getWidth();
        int height = panel.getHeight();

        // Redimensionner les images des boutons en fonction de la taille de la fenêtre
        nouvellePartie.setDimension((int) (width * 0.25), (int) (height * 0.12));
        chargerPartie.setDimension((int) (width * 0.25), (int) (height * 0.12));
        options.setDimension((int) (width * 0.25), (int) (height * 0.12));
        quitter.setDimension((int) (width * 0.25), (int) (height * 0.12));
        rules.setDimension((int) (width * 0.06), (int) (height * 0.15));

        panel.remove(0);
        panel.add(Box.createRigidArea(new Dimension(0, panel.getHeight() / 3)), 0);
    }
}

