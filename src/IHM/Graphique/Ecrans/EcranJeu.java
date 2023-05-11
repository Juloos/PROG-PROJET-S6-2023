package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.InfoJoueur;
import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUpMenu;
import Modele.Actions.ActionAnnuler;
import Modele.Actions.ActionRefaire;
import Modele.Jeux.Jeu;
import Modele.Joueurs.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranJeu extends Ecran {


    // Les menus des informations des joueurs
    InfoJoueur[] joueurs;
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

    PlateauGraphique plateauGraphique;
    int joueurActif;

    public EcranJeu() {
        super("Partie en cours");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        this.plateauGraphique = ihm.getPlateauGraphique();

        panel.setLayout(new BorderLayout());

        menu = new JPanel(new GridLayout(0, 1));

        joueurs = new InfoJoueur[ihm.getMoteurJeu().getJeu().getNbJoueurs()];
        Joueur[] jeuJoueurs = ihm.getMoteurJeu().getJeu().getJoueurs();
        for (int i = 0; i < joueurs.length; i++) {
            InfoJoueur infoJoueur = new InfoJoueur(jeuJoueurs[i], i == joueurs.length - 1);
            joueurs[i] = infoJoueur;
            menu.add(infoJoueur);
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
                ihm.ouvrirFenetre(new PopUpMenu());
                ihm.getMoteurJeu().pauseGame(true);
            }
        });

        JPanel horizontal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontal.add(options);
        optionsPanel.add(horizontal, BorderLayout.SOUTH);
        menu.add(optionsPanel);

        panel.add(menu, BorderLayout.EAST);
        panel.add(ihm.getPlateauGraphique(), BorderLayout.CENTER);
    }

    @Override
    public void update(IHMGraphique ihm) {
        ihm.getMoteurJeu().debug("Mise à jour de l'écran de jeu");
    }

    @Override
    public void update(Jeu jeu) {
        super.update(jeu);
        System.out.println("Mise à jour de l'écran de jeu via jeu");
        Joueur[] joueursJeu = jeu.getJoueurs();
        joueurActif = jeu.getJoueur().getID();
        for (int i = 0; i < joueurs.length; i++) {
            InfoJoueur joueur = joueurs[i];
            joueur.update(joueursJeu[i].getID() == joueurActif);

            if (joueursJeu[i].getID() == joueurActif) {
                plateauGraphique.setPositionFlecheJoueurActif(menu.getX() - 110, joueur.getY(), 100, 100);
            }
        }
    }

    @Override
    public void close(IHMGraphique ihm) {
        super.close(ihm);
    }

    @Override
    public void afficherMessage(String mess) {
        this.message.setText(mess);
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                message.setText("");
            }
        });
        timer.start();
    }

    @Override
    public void resized() {
        final int menuWidth = panel.getWidth() * 2 / 7;
        menu.setPreferredSize(new Dimension(menuWidth, panel.getHeight()));

        plateauGraphique.setPositionFlecheJoueurActif(menu.getX() - 110, joueurs[joueurActif].getY(), 100, 100);
    }
}
