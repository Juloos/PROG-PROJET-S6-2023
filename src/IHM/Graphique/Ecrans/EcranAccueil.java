package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUp;
import IHM.Graphique.PopUp.PopUpConfirmation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        /*ihm.getFrame().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resized();
            }
        });*/
    }

    @Override
    public void creation(IHMGraphique ihm) {
        // Chargement de l'image de fond
        ImageIcon icon = new ImageIcon("res/fondsEcrans/background.png");
        this.backgroundImage = icon.getImage();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel bouttons = new JPanel();
        bouttons.setLayout(new BoxLayout(bouttons, BoxLayout.Y_AXIS));
        bouttons.setOpaque(false);
        bouttons.add(Box.createRigidArea(new Dimension(0, 300)));

        // Le menu pour ouvrir le menu de création d'une nouvelle partie
        ImageIcon iconNouvellePartie = new ImageIcon("res/boutons/nouvelle_partie.png");
        nouvellePartie = new JButtonIcon(iconNouvellePartie, 370, 130);
        nouvellePartie.setContentAreaFilled(false);
        nouvellePartie.setAlignmentX(Component.CENTER_ALIGNMENT);

        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranCreationPartie());
            }
        });

        // Le bouton pour ouvrir le menu de chargement d'une partie
        chargerPartie = new JButtonIcon(new ImageIcon("res/boutons/charger_partie.png"), 340, 100);
        chargerPartie.setContentAreaFilled(false);
        chargerPartie.setAlignmentX(Component.CENTER_ALIGNMENT);
        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranChargerPartie());
            }
        });

        // Le bouton pour ouvrir le menu des options
        options = new JButtonIcon(new ImageIcon("res/boutons/option.png"), 270, 80);
        options.setContentAreaFilled(false);
        options.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });

        // Le bouton pour quitter le jeu
        quitter = new JButtonIcon(new ImageIcon("res/boutons/quitter.png"), 270, 80);
        quitter.setContentAreaFilled(false);
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

        rules = new JButtonIcon(new ImageIcon("res/boutons/Regles.png"), 100, 150);
        rules.setContentAreaFilled(false);
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
        horizontal.setOpaque(false);
        horizontal.add(rules);

        panel.add(bouttons);
        panel.add(horizontal);

        }
    }

    /*@Override
    public void resized() {
        int width = panel.getWidth();
        int height = panel.getHeight();

        nouvellePartie.setPreferredSize(new Dimension((int) (width * 0.23), (int) (height * 0.13)));
        // Redimensionner les images des boutons en fonction de la taille de la fenêtre
        ImageIcon iconNouvellePartie = new ImageIcon("res/boutons/Nouvelle_partie.png");
        nouvellePartie.setIcon(resizeIcon(iconNouvellePartie, (int) (width * 0.23), (int) (height * 0.13)));

        chargerPartie.setPreferredSize(new Dimension((int) (width * 0.23), (int) (height * 0.11)));
        ImageIcon iconChargerPartie = new ImageIcon("res/boutons/charger_partie.png");
        chargerPartie.setIcon(resizeIcon(iconChargerPartie, (int) (width * 0.23), (int) (height * 0.11)));

        options.setPreferredSize(new Dimension((int) (width * 0.17), (int) (height * 0.13)));
        ImageIcon iconOptions = new ImageIcon("res/boutons/option.png");
        options.setIcon(resizeIcon(iconOptions, (int) (width * 0.3), (int) (height * 0.13)));

        quitter.setPreferredSize(new Dimension((int) (width * 0.3), (int) (height * 0.13)));
        ImageIcon iconQuitter = new ImageIcon("res/boutons/Quitter.png");
        quitter.setIcon(resizeIcon(iconQuitter, (int) (width * 0.3), (int) (height * 0.13)));

        ImageIcon iconRules = new ImageIcon("res/rules.png");
        rules.setIcon(resizeIcon(iconRules, (int) (width * 0.1), (int) (height * 0.1)));

        panel.repaint();
        panel.revalidate();
    }

    // Méthode utilitaire pour redimensionner une icône
    private Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }*/

