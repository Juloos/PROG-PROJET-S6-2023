package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUpConfirmation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addComponentListener;

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
        ihm.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new GridBagLayout());
        // Chargement de l'image de fond
        ImageIcon icon = new ImageIcon("res/background.png");
        this.backgroundImage = icon.getImage();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 30, 0);
        constraints.gridx = 0;
        constraints.gridy = 2;
        Color color = Color.decode("#0000BB");

        // Le menu pour ouvrir le menu de création d'une nouvelle partie
        nouvellePartie = new Button("Nouvelle partie");
        nouvellePartie.setForeground(color);
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
        panel.repaint();
        panel.revalidate();
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                resized();
            }
        });
    }

    @Override
    public void resized() {
        System.out.println("on resized");
        int width = panel.getWidth();
        int height = panel.getHeight();
        int buttonWidth = (int) (width * 0.3);
        int buttonHeight = (int) (height * 0.06);
        Font buttonFont = new Font("Forte", Font.PLAIN, (int) (height * 0.04));

        nouvellePartie.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        nouvellePartie.setFont(buttonFont);

        chargerPartie.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        chargerPartie.setFont(buttonFont);

        options.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        options.setFont(buttonFont);

        quitter.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        quitter.setFont(buttonFont);

        panel.revalidate();
    }
}
