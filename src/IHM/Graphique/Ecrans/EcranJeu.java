package IHM.Graphique.Ecrans;

import Global.Config;
import IHM.Graphique.Composants.InfoJoueur;
import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Couleurs;
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
    JPanel menu, panelInf;
    // Pour afficher les messages de l'IHM
    JTextArea message;
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
        ImageIcon icon = new ImageIcon("res/fondsEcrans/background_jeu.jpeg");
        this.backgroundImage = icon.getImage();

        this.plateauGraphique = ihm.getPlateauGraphique();

        panel.setLayout(new BorderLayout());
        panel.setBackground(Couleurs.TRANSPARENT);

        menu = new JPanel(new BorderLayout());
        menu.setBackground(Couleurs.BACKGROUND_ECRAN);

        JPanel panelJoueur = new JPanel(new GridLayout(Config.NB_MAX_JOUEUR, 1));
        panelJoueur.setBackground(Couleurs.TRANSPARENT);

        joueurs = new InfoJoueur[ihm.getMoteurJeu().getJeu().getNbJoueurs()];
        Joueur[] jeuJoueurs = ihm.getMoteurJeu().getJeu().getJoueurs();
        for (int i = 0; i < joueurs.length; i++) {
            InfoJoueur infoJoueur = new InfoJoueur(jeuJoueurs[i]);
            joueurs[i] = infoJoueur;
            panelJoueur.add(infoJoueur);
        }
        menu.add(panelJoueur, BorderLayout.CENTER);

        panelInf = new JPanel(new GridLayout(0, 1));
        panelInf.setBackground(Couleurs.TRANSPARENT);

        message = new JTextArea();
        message.setEditable(false);
        message.setLineWrap(true);
        message.setFont(new Font("Arial", Font.PLAIN, 20));
        message.setBackground(Couleurs.TRANSPARENT);
        message.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelInf.add(message);

        annuler = new JButtonIcon(new ImageIcon("res/icones//arrow_left.png"), 100);
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionAnnuler());
            }
        });

        refaire = new JButtonIcon(new ImageIcon("res/icones//arrow_right.png"), 100);
        refaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionRefaire());
            }
        });

        JPanel annulerRefaire = new JPanel(new GridLayout(1, 0));
        annulerRefaire.setBackground(Couleurs.TRANSPARENT);
        annulerRefaire.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        annulerRefaire.add(annuler);
        annulerRefaire.add(refaire);

        panelInf.add(annulerRefaire);

        options = new JButtonIcon(new ImageIcon("res/icones/gear.png"), 70);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpMenu());
                ihm.getMoteurJeu().pauseGame(true);
            }
        });

        JPanel horizontal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontal.setBackground(Couleurs.TRANSPARENT);
        horizontal.add(options);
        panelInf.add(horizontal);

        menu.add(panelInf, BorderLayout.SOUTH);
        panel.add(ihm.getPlateauGraphique(), BorderLayout.CENTER);
        panel.add(menu, BorderLayout.EAST);

        final int menuWidth = ihm.getFrame().getWidth() * 2 / 7;
        menu.setPreferredSize(new Dimension(menuWidth, ihm.getFrame().getHeight()));

        panelInf.setPreferredSize(new Dimension(menuWidth, ihm.getFrame().getHeight() / 3));

        plateauGraphique.setDimensionFlecheJoueurActif(100, 100);
    }

    @Override
    public void update(IHMGraphique ihm) {
        plateauGraphique.setPositionFlecheJoueurActif(menu.getX() - 110, joueurs[joueurActif].getY());
    }

    @Override
    public void update(Jeu jeu) {
        this.joueurActif = jeu.getJoueur().getID();

        Joueur[] js = jeu.getJoueurs();
        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i].update(js[i].getScore(), js[i].getTuiles());
        }

        panel.repaint();
    }

    @Override
    public void afficherMessage(String mess) {
        this.message.setText(mess);
    }

    @Override
    public void resized() {
        final int menuWidth = panel.getParent().getWidth() * 2 / 7;
        menu.setPreferredSize(new Dimension(menuWidth, panel.getParent().getHeight()));

        int flecheJoueurActifSize = panel.getHeight() / 8;
        plateauGraphique.setPositionFlecheJoueurActif(panel.getParent().getWidth() - menuWidth - flecheJoueurActifSize - 10, joueurs[joueurActif].getY());
        plateauGraphique.setDimensionFlecheJoueurActif(flecheJoueurActifSize, flecheJoueurActifSize);
    }
}
