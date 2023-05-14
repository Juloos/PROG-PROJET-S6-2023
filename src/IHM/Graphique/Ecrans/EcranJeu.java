package IHM.Graphique.Ecrans;

import Global.Config;
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
    public void creation(IHMGraphique ihm) {
        this.plateauGraphique = ihm.getPlateauGraphique();

        panel.setLayout(new BorderLayout());

        joueurs = new InfoJoueur[Config.NB_MAX_JOUEUR];

        menu = new JPanel(new GridLayout(0, 1));

        joueurs = new InfoJoueur[ihm.getMoteurJeu().getJeu().getNbJoueurs()];
        Joueur[] jeuJoueurs = ihm.getMoteurJeu().getJeu().getJoueurs();
        for (int i = 0; i < joueurs.length; i++) {
            InfoJoueur infoJoueur = new InfoJoueur(jeuJoueurs[i]);
            joueurs[i] = infoJoueur;
            menu.add(infoJoueur);
        }

        message = new JLabel("", SwingConstants.CENTER);
        menu.add(message);

        infoTour = new JLabel("", SwingConstants.CENTER);
        infoTour.setFont(new Font("", Font.PLAIN, 20));
        menu.add(infoTour);

        JPanel annulerRefaire = new JPanel(new GridLayout(1, 0));

        annuler = new JButtonIcon(new ImageIcon("res/fleches/arrow_left.png"), 100);
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionAnnuler());
            }
        });

        refaire = new JButtonIcon(new ImageIcon("res/fleches/arrow_right.png"), 100);
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

        panel.add(ihm.getPlateauGraphique(), BorderLayout.CENTER);
        panel.add(menu, BorderLayout.EAST);

        final int menuWidth = ihm.getFrame().getWidth() * 2 / 7;
        menu.setPreferredSize(new Dimension(menuWidth, ihm.getFrame().getHeight()));

        plateauGraphique.setDimensionFlecheJoueurActif(100, 100);
    }

    @Override
    public void update(IHMGraphique ihm) {
        plateauGraphique.setPositionFlecheJoueurActif(menu.getX() - 110, joueurs[joueurActif].getY());
    }

    @Override
    public void update(Jeu jeu) {
        this.joueurActif = jeu.getJoueur().getID();

        int index = joueurActif == 0 ? (joueurs.length - 1) : (joueurActif - 1);
        joueurs[index].update(jeu.getJoueur(index).getScore(), jeu.getJoueur(index).getTuiles());
    }

    @Override
    public void afficherMessage(String mess) {
        this.message.setText(mess);
    }

    @Override
    public void resized() {
        final int menuWidth = panel.getWidth() * 2 / 7;
        menu.setPreferredSize(new Dimension(menuWidth, panel.getHeight()));

        int flecheJoueurActifSize = panel.getHeight() / 8;
        plateauGraphique.setPositionFlecheJoueurActif(menu.getX() - flecheJoueurActifSize - 10, joueurs[joueurActif].getY());
        plateauGraphique.setDimensionFlecheJoueurActif(flecheJoueurActifSize, flecheJoueurActifSize);
    }
}
