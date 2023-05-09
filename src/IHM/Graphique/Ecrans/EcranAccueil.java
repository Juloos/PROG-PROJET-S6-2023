package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUpConfirmation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranAccueil extends Ecran {
    public EcranAccueil() {
        super("Accueil");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new GridBagLayout());
        // Chargement de l'image de fond
        ImageIcon icon = new ImageIcon("res\\background.png");
        this.backgroundImage = icon.getImage();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 10;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(0, 0, 30, 0);
        Color color = Color.decode("#0000BB");
        JButton nouvellePartie = new JButton("Nouvelle partie");

        // Le menu pour ouvrir le menu de création d'une nouvelle partie
        nouvellePartie.setForeground(Color.WHITE);
        nouvellePartie.setFont(new Font("Forte", Font.PLAIN, 50));
        nouvellePartie.setBackground(color);
        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranCreationPartie());
            }
        });

        // Le bouton pour ouvrir le menu de chargement d'une partie
        JButton chargerPartie = new JButton("Charger partie");
        chargerPartie.setBackground(color);
        chargerPartie.setForeground(Color.WHITE);
        chargerPartie.setFont(new Font("Forte", Font.PLAIN, 50));
        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranChargerPartie());
            }
        });

        // Le bouton pour ouvrir le menu des options
        JButton options = new JButton("Options");
        options.setBackground(Color.WHITE);
        options.setForeground(color);
        options.setFont(new Font("Forte", Font.PLAIN, 50));
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });

        // Le bouton pour quitter le jeu
        JButton quitter = new JButton("Quitter");
        quitter.setBackground(Color.WHITE);
        quitter.setForeground(color);
        quitter.setFont(new Font("Forte", Font.PLAIN, 50));
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpConfirmation(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.getMoteurJeu().fin();
                    }
                }));
            }
        });

        // Ajout des éléments de l'écran dans son panel
        panel.add(nouvellePartie, constraints);
        constraints.gridy = 9;
        panel.add(chargerPartie, constraints);
        constraints.gridy = 10;
        panel.add(options, constraints);
        constraints.gridy = 11;
        panel.add(quitter, constraints);
        panel.repaint();
        panel.revalidate();
    }
}
