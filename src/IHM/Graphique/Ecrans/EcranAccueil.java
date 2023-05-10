package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUpConfirmation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranAccueil extends Ecran {

    Button nouvellePartie;
    Button chargerPartie;
    Button options;
    Button quitter;

    public EcranAccueil() {
        super("Accueil");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        int initialButtonWidth = (int) Math.round(0.2 * ihm.getFrame().getWidth());
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new GridBagLayout());
        // Chargement de l'image de fond
        ImageIcon icon = new ImageIcon("res/background.png");
        this.backgroundImage = icon.getImage();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(0, 0, 30, 0);
        Color color = Color.decode("#0000BB");

        // Le menu pour ouvrir le menu de création d'une nouvelle partie
        nouvellePartie = new Button("Nouvelle partie");
        nouvellePartie.setForeground(color);
        nouvellePartie.setFont(new Font("Forte", Font.PLAIN, 25));
        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranCreationPartie());
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nouvellePartie, constraints);

        // Le bouton pour ouvrir le menu de chargement d'une partie
        chargerPartie = new Button("Charger partie");
        chargerPartie.setForeground(color);
        chargerPartie.setFont(new Font("Forte", Font.PLAIN, 25));
        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranChargerPartie());
            }
        });
        constraints.gridy = 1;
        panel.add(chargerPartie, constraints);

        // Le bouton pour ouvrir le menu des options
        options = new Button("Options");
        options.setForeground(color);
        options.setFont(new Font("Forte", Font.PLAIN, 25));
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });
        constraints.gridy = 2;
        panel.add(options, constraints);

        // Le bouton pour quitter le jeu
        quitter = new Button("Quitter");
        quitter.setForeground(color);
        quitter.setFont(new Font("Forte", Font.PLAIN, 25));
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpConfirmation(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.getMoteurJeu().terminer();
                    }
                }));
            }
        });
        constraints.gridy = 3;
        panel.add(quitter, constraints);
    }

    @Override
    public void resized() {
        super.resized();
//        int newButtonWidth = (int) Math.round(0.2 * panel.getWidth());
//        nouvellePartie.setPreferredSize(new Dimension(newButtonWidth, nouvellePartie.getHeight()));
//        chargerPartie.setPreferredSize(new Dimension(newButtonWidth, chargerPartie.getHeight()));
//        options.setPreferredSize(new Dimension(newButtonWidth, options.getHeight()));
//        quitter.setPreferredSize(new Dimension(newButtonWidth, quitter.getHeight()));
    }
}
