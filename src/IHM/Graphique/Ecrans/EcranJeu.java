package IHM.Graphique.Ecrans;

import Controleur.MoteurJeu;
import Global.Config;
import IHM.Graphique.Composants.InfoJoueur;
import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Couleurs;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUp;
import IHM.Graphique.PopUp.PopUpMenu;
import Modele.Actions.ActionAnnuler;
import Modele.Actions.ActionCoup;
import Modele.Actions.ActionRefaire;
import Modele.Coord;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Jeux.Jeu;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EcranJeu extends Ecran implements MouseListener, MouseMotionListener {

    final IHMGraphique ihm;

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
    // La tuile sélectionnée par le joueur
    Coord selection;
    PlateauGraphique plateauGraphique;
    int joueurActif;

    public EcranJeu(IHMGraphique ihm) {
        super("Partie en cours");
        this.ihm = ihm;
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        ihm.getFrame().addMouseListener(this);
        ihm.getFrame().addMouseMotionListener(this);
    }

    @Override
    public void close(IHMGraphique ihm) {
        super.close(ihm);
        ihm.getFrame().removeMouseListener(this);
        ihm.getFrame().removeMouseMotionListener(this);
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
        panelInf.setOpaque(false);

        message = new JTextArea();
        message.setEditable(false);
        message.setLineWrap(true);
        message.setFont(new Font("Arial", Font.PLAIN, 25));
        message.setOpaque(false);
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
        annulerRefaire.setOpaque(false);
        annulerRefaire.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        annulerRefaire.add(annuler);
        annulerRefaire.add(refaire);

        panelInf.add(annulerRefaire);

        options = new JButtonIcon(new ImageIcon("res/icones/gear.png"), 70);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().pauseGame(true);
                PopUp p = new PopUpMenu(ihm);
                p.init(ihm);
                p.setVisible(true);
            }
        });

        JPanel horizontal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontal.setOpaque(false);
        horizontal.add(options);
        panelInf.add(horizontal);
        menu.add(panelInf, BorderLayout.SOUTH);
        menu.setOpaque(false);
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

    /**
     * Lorsqu'un joueur clique sur la fenêtre
     *
     * @param mouseEvent : l'événement du clic de la souris
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        MoteurJeu moteurJeu = ihm.getMoteurJeu();

        // On récupère la coordonnée de la tuile sélectionnée (peut être invalide)
        Coord coord = plateauGraphique.getClickedTuile(mouseEvent.getX(), mouseEvent.getY());
        moteurJeu.debug("Tuile clickée " + coord);

        if (moteurJeu.estPhasePlacementPions()) {
            // Si le jeu est dans la phase placement des pions
            ihm.setActionJoueur(new ActionCoup(new CoupAjout(coord, moteurJeu.getJoueurActif().id)));
        } else {
            // Si le jeu est dans la phase jeu (déplacement des pions jusqu'à fin de la partie)
            if (selection == null) {
                // Le joueur choisi lequel de ses pions, il veut déplacer
                selection = coord;
                moteurJeu.debug("Le joueur actif choisi un de ses pions");

                if (moteurJeu.getJeu().joueurDePion(selection) == moteurJeu.getJoueurActif().id) {
                    // On affiche en surbrillance toutes les tuiles sur lesquelles le pion sélectionné peut aller
                    moteurJeu.debug("Affichage des tuiles accessible pour le pion choisi");
                    plateauGraphique.setTuilesSurbrillance(moteurJeu.getJeu().deplacementsPion(selection));
                } else {
                    // Le joueur n'a pas choisi un de ses pions
                    moteurJeu.debug("Le joueur n'a pas choisi un de ses pions");
                    selection = null;
                    plateauGraphique.setTuilesSurbrillance(null);
                }

            } else {
                // Le joueur choisi sur quelle tuile il veut déplacer le pion qu'il a sélectionné
                Coord cible = coord;
                moteurJeu.debug("Le joueur choisi où il veut déplacer son pion");

                if (moteurJeu.getJeu().getPlateau().estCoordValide(cible) && !moteurJeu.getJeu().estPion(cible)) {
                    // Le pion va se déplacer
                    moteurJeu.debug("On essaye de déplacer le pion");
                    ihm.setActionJoueur(new ActionCoup(new CoupDeplacement(selection, cible, moteurJeu.getJoueurActif().id)));
                    plateauGraphique.setTuilesSurbrillance(null);
                    selection = null;
                } else if (moteurJeu.getJeu().joueurDePion(cible) == moteurJeu.getJoueurActif().id) {
                    // Le joueur a choisi un autre de ses pions
                    selection = cible;
                    moteurJeu.debug("Le joueur a choisi un autre de ses pions");

                    ArrayList<Coord> coords = moteurJeu.getJeu().deplacementsPion(selection);
                    coords.add(selection);
                    plateauGraphique.setTuilesSurbrillance(coords);
                } else {
                    selection = null;
                    plateauGraphique.setTuilesSurbrillance(null);
                }
            }
        }
    }

    /**
     * Lorsque la souris bouge sur la fenêtre
     *
     * @param mouseEvent : l'évenement du mouvement de la souris
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        MoteurJeu moteurJeu = ihm.getMoteurJeu();
        // Si la partie n'est pas en pause
        if (moteurJeu.getJeu() != null && moteurJeu.getJoueurActif() instanceof JoueurHumain && moteurJeu.estPhasePlacementPions()) {
            // On modifie la position du pion a placer
            plateauGraphique.setPlacementPingouin(mouseEvent.getX(), mouseEvent.getY());
        } else {
            // On n'affiche pas le pion flottant
            plateauGraphique.setPlacementPingouin(-1, -1);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }
}
