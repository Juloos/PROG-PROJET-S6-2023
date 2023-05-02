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
        panel.setLayout(new GridBagLayout());
        // Chargement de l'image de fond
        ImageIcon icon = new ImageIcon("src\\background.png");
        this.backgroundImage = icon.getImage();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 4;

        panel.setLayout(new GridBagLayout());
        Color color = Color.decode("#13B0E8");
        JButton nouvellePartie = new JButton("Nouvelle partie");
        nouvellePartie.setBackground(color);
        nouvellePartie.setForeground(Color.WHITE);
        nouvellePartie.setFont(new Font("Forte", Font.PLAIN, 17));
        nouvellePartie.setPreferredSize(new Dimension(200, 50));
        nouvellePartie.setBorderPainted(false); //enlever la bordure visible
        nouvellePartie.setContentAreaFilled(false); //enlever la couleur de fond par défaut
        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranCreationPartie());
            }
        });

        JButton chargerPartie = new JButton("Charger partie");
        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranChargerPartie());
            }
        });

        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });
        JButton quitter = new JButton("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpConfirmation(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.retournerPrecedenteFenetre();
                        System.exit(0);
                    }
                }));
            }
        });
        panel.add(nouvellePartie, constraints);
        constraints.gridy = 1;
        panel.add(chargerPartie, constraints);
        constraints.gridy = 2;
        panel.add(options, constraints);
        constraints.gridy = 3;
        panel.add(quitter, constraints);
        panel.revalidate();
        panel.repaint();
    }
}
