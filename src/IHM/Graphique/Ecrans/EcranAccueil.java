package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images;
import IHM.Graphique.PopUp.PopUp;
import IHM.Graphique.PopUp.PopUpConfirmation;
import IHM.Graphique.PopUp.PopUpReglesJeu;

import javax.swing.*;
import java.awt.*;

/**
 * Classe du menu d'accueil
 */
public class EcranAccueil extends Ecran {

    JButtonIcon nouvellePartie;
    JButtonIcon chargerPartie;
    JButtonIcon options;
    JButtonIcon quitter;
    JButtonIcon rules;

    public EcranAccueil() {
        super("Accueil");
    }

    /* Méthodes héritées */
    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        ihm.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        ihm.getFrame().setMinimumSize(new Dimension(1400, 800));
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
        nouvellePartie.addActionListener(actionEvent -> ihm.ouvrirFenetre(new EcranCreationPartie()));

        // Le bouton pour ouvrir le menu de chargement d'une partie
        chargerPartie = new JButtonIcon(Images.chargerImage("/boutons/charger_partie.png"), 340, 120);
        chargerPartie.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chargerPartie.setAlignmentX(Component.CENTER_ALIGNMENT);
        chargerPartie.addActionListener(actionEvent -> ihm.ouvrirFenetre(new EcranChargerPartie()));

        // Le bouton pour ouvrir le menu des options
        options = new JButtonIcon(Images.chargerImage("/boutons/option.png"), 270, 110);
        options.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        options.setContentAreaFilled(false);
        options.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.addActionListener(actionEvent -> ihm.ouvrirFenetre(new EcranOptions()));

        // Le bouton pour quitter le jeu
        quitter = new JButtonIcon(Images.chargerImage("/boutons/quitter.png"), 270, 110);
        quitter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        quitter.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitter.addActionListener(actionEvent -> {
            PopUp popUp = new PopUpConfirmation(ihm, actionEvent1 -> ihm.getMoteurJeu().terminer());
            popUp.init(ihm);
            popUp.setVisible(true);
        });

        rules = new JButtonIcon(Images.chargerImage("/icones/regles.png"), 150, 150);
        rules.addActionListener(e -> {
            PopUp p = new PopUpReglesJeu(ihm);
            p.init(ihm);
            p.setVisible(true);
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

        panel.add(Box.createRigidArea(new Dimension(0, ihm.getFrame().getHeight() / 3)), 0);
        panel.add(bouttons);
        panel.add(horizontal);

        panel.validate();
    }

    @Override
    public void resized(Dimension frameDimension) {
        double width = frameDimension.getWidth();
        double height = frameDimension.getHeight();

        panel.remove(0);
        panel.add(Box.createRigidArea(new Dimension(0, (int) (height / 3.2))), 0);

        // Redimensionner les images des boutons en fonction de la taille de la fenêtre
        nouvellePartie.setDimension((int) (width * 0.25), (int) (height * 0.12));
        chargerPartie.setDimension((int) (width * 0.25), (int) (height * 0.12));
        options.setDimension((int) (width * 0.24), (int) (height * 0.12));
        quitter.setDimension((int) (width * 0.24), (int) (height * 0.12));

        int rulesSize = (int) (Math.min(width, height) * 0.15);
        rules.setDimension(rulesSize, rulesSize);

        System.out.println("Taille du bouton quitter : " + quitter.getSize());

        panel.repaint();
        panel.revalidate();
    }
}

