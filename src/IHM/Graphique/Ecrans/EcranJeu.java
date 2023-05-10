package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.InfoJoueur;
import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUpMenu;
import Modele.Actions.ActionAnnuler;
import Modele.Actions.ActionRefaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EcranJeu extends Ecran {

    // Les menus des informations des joueurs
    List<InfoJoueur> joueurs;
    // Le menu affiché sur la droite de l'écran
    JPanel menu;
    // Le label pour afficher les messages de l'IHM
    // Le label pour afficher ce que doit faire le joueur actif
    JLabel message, infoTour;
    // Les boutons pour :
    // - mettre le jeu en pause et ouvrir le menu de pause
    // - annuler le dernier coup joué
    // - refaire le dernier coup annulé
    JButton options, annuler, refaire;

    public EcranJeu() {
        super("Partie en cours");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new BorderLayout());

        joueurs = new ArrayList<>();

        menu = new JPanel(new GridLayout(0, 1));

        for (int i = 0; i < ihm.getMoteurJeu().getJeu().getNbJoueurs(); i++) {
            InfoJoueur joueur = new InfoJoueur(ihm.getMoteurJeu().getJeu().getJoueur(i));
            joueurs.add(joueur);
            menu.add(joueur);
        }

        message = new JLabel("", SwingConstants.CENTER);
        menu.add(message);

        infoTour = new JLabel("", SwingConstants.CENTER);
        infoTour.setFont(new Font("", Font.PLAIN, 20));
        menu.add(infoTour);

        JPanel annulerRefaire = new JPanel(new GridLayout(1, 0));

        annuler = new JButtonIcon(new ImageIcon("res/arrow_left.png"), 100);
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionAnnuler());
            }
        });

        refaire = new JButtonIcon(new ImageIcon("res/arrow_right.png"), 100);
        refaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionRefaire());
            }
        });

        annulerRefaire.add(annuler);
        annulerRefaire.add(refaire);
        menu.add(annulerRefaire);

        JPanel optionsPanel = new JPanel(new BorderLayout());

        options = new JButtonIcon(new ImageIcon("res/gear.png"), 70);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().pauseGame(true);
                ihm.ouvrirFenetre(new PopUpMenu());
            }
        });

        JPanel horizontal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontal.add(options);
        optionsPanel.add(horizontal, BorderLayout.SOUTH);
        menu.add(optionsPanel);


        panel.add(menu, BorderLayout.EAST);
        panel.add(ihm.getPlateauGraphique(), BorderLayout.CENTER);

        update(ihm);
    }

    @Override
    public void update(IHMGraphique ihm) {
        resized();

        if (ihm.getMoteurJeu().estPhasePlacementPions()) {
            infoTour.setText("Joueur " + (ihm.getMoteurJeu().getJoueurActif().getID() + 1) + " veuillez placer un pion");
        } else {
            infoTour.setText("Joueur " + (ihm.getMoteurJeu().getJoueurActif().getID() + 1) + " veuillez déplacer un de vos pions");
        }

        for (InfoJoueur joueur : joueurs) {
            joueur.update(joueur.getJoueurID() == ihm.getMoteurJeu().getJoueurActif().getID());
        }
        panel.repaint();
        panel.revalidate();
    }

    @Override
    public void afficherMessage(String message) {
        this.message.setText(message);
    }

    @Override
    public void resized() {
        final int menuWidth = panel.getWidth() * 2 / 7;
        menu.setPreferredSize(new Dimension(menuWidth, panel.getHeight()));
        panel.revalidate();
    }
}
