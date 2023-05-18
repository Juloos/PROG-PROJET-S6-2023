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
import Modele.Jeux.JeuConcret;
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
    // - reprendre la partie lorsqu'elle mise en pause par refaire et annuler
    JButtonIcon options, annuler, refaire, reprendre, generer, valider;
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
        // Création des variables
        final int menuWidth = ihm.getFrame().getWidth() * 2 / 7;
        ImageIcon background = new ImageIcon("res/fondsEcrans/background_jeu.jpeg");
        ImageIcon left_button = new ImageIcon("res/icones/arrow_left.png");
        ImageIcon right_button = new ImageIcon("res/icones/arrow_right.png");
        ImageIcon option_button = new ImageIcon("res/icones/gear.png");
        ImageIcon generation_button = new ImageIcon("res/icones/two_circular_arrows.png");
        ImageIcon validate_button = new ImageIcon("res/icones/check.png");
        this.plateauGraphique = ihm.getPlateauGraphique();
        plateauGraphique.setDimensionFlecheJoueurActif(100, 100);

        // Mise en place du background
        this.backgroundImage = background.getImage();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Couleurs.TRANSPARENT);


        // Initialisation du panel infoJoueur
        JPanel panelJoueur = new JPanel(new GridLayout(Config.NB_MAX_JOUEUR, 1));
        panelJoueur.setBackground(Couleurs.TRANSPARENT);

        // Remplissage du panel joueur
        joueurs = new InfoJoueur[ihm.getMoteurJeu().getJeu().getNbJoueurs()];
        Joueur[] jeuJoueurs = ihm.getMoteurJeu().getJeu().getJoueurs();
        for (int i = 0; i < joueurs.length; i++) {
            InfoJoueur infoJoueur = new InfoJoueur(jeuJoueurs[i]);
            joueurs[i] = infoJoueur;
            panelJoueur.add(infoJoueur);
        }


        // Inititalisation du panel de message
        message = new JTextArea();
        message.setEditable(false);
        message.setLineWrap(true);
        message.setFont(new Font("Arial", Font.PLAIN, 25));
        message.setOpaque(false);
        message.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));


        // Initialisation du bouton annuler
        annuler = new JButtonIcon(left_button, 100);
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionAnnuler());
            }
        });

        // Initialisation du bouton refaire
        refaire = new JButtonIcon(right_button, 100);
        refaire = new JButtonIcon(new ImageIcon("res/icones/arrow_right.png"), 100);
        refaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionRefaire());
            }
        });

        // Initialisation du bouton reprendre
        reprendre = new JButtonIcon(new ImageIcon("res/icones/pause.png"), 100);
        reprendre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().pauseGame(!ihm.getMoteurJeu().partieEnPause());
            }
        });

        // Initialisation du panel contenant annuler et refaire
        JPanel annulerRefaire = new JPanel(new GridLayout(1, 0));
        annulerRefaire.setOpaque(false);
        annulerRefaire.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        annulerRefaire.add(annuler);
        annulerRefaire.add(reprendre);
        annulerRefaire.add(refaire);


        // Initialisation du bouton option
        options = new JButtonIcon(option_button, 70);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().pauseGame(true);
                PopUp p = new PopUpMenu(ihm);
                p.init(ihm);
                p.setVisible(true);
            }
        });

        // Gestion du placement du bouton option
        JPanel horizontal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontal.setOpaque(false);
        horizontal.add(options);


        // Initialisation du bouton generer
        generer = new JButtonIcon(generation_button, 100);
        generer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().genePlateau();
                ihm.getMoteurJeu().updateAffichage();
//                ihm.updateAffichage();
            }
        });

        // Initialisation du bouton valider
        valider = new JButtonIcon(validate_button, 100);
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().fixerPlateau();
                panelInf.removeAll();
                panelInf.add(message);
                panelInf.add(annulerRefaire);
                panelInf.add(horizontal);
                panel.repaint();
                panel.revalidate();
            }
        });

        // Initialisation du panel des button de generation de plateau
        JPanel buttonGeneration = new JPanel(new GridLayout(1, 0));
        buttonGeneration.setOpaque(false);
        buttonGeneration.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonGeneration.add(generer);
        buttonGeneration.add(valider);

        // Initialisation du panel des fonctionnelité (annuler, refaire, option et message)
        panelInf = new JPanel(new GridLayout(0, 1));
        panelInf.setPreferredSize(new Dimension(menuWidth, ihm.getFrame().getHeight() / 3));
        panelInf.setOpaque(false);
        panelInf.add(buttonGeneration);
        panelInf.add(horizontal);

        // Initialisation du panel de gauche
        menu = new JPanel(new BorderLayout());
        menu.setBackground(Couleurs.BACKGROUND_ECRAN);
        menu.setPreferredSize(new Dimension(menuWidth, ihm.getFrame().getHeight()));
        menu.setOpaque(false);
        menu.add(panelJoueur, BorderLayout.CENTER);
        menu.add(panelInf, BorderLayout.SOUTH);

        // Initialisation de l'ecran
        panel.add(plateauGraphique, BorderLayout.CENTER);
        panel.add(menu, BorderLayout.EAST);

    }

    @Override
    public void update(IHMGraphique ihm) {
        plateauGraphique.setPositionFlecheJoueurActif(menu.getX() - 110, joueurs[joueurActif].getY());
    }

    @Override
    public void update(JeuConcret jeu) {
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

    @Override
    public void pause() {
        super.pause();
        reprendre.setImageIcon(new ImageIcon("res/icones/play.png"));
    }

    @Override
    public void resume() {
        super.resume();
        reprendre.setImageIcon(new ImageIcon("res/icones/pause.png"));
    }

    /**
     * Lorsqu'un joueur clique sur la fenêtre
     *
     * @param mouseEvent : l'événement du clic de la souris
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        MoteurJeu moteurJeu = ihm.getMoteurJeu();
        int joueurActifID = moteurJeu.getJoueurActif().id;
        
        if (clickEnable) {
            // On récupère la coordonnée de la tuile sélectionnée (peut être invalide)
            Coord coord = plateauGraphique.getClickedTuile(mouseEvent.getX(), mouseEvent.getY());
            moteurJeu.debug("Tuile clickée " + coord);

            if (moteurJeu.estPhasePlacementPions()) {
                // Si le jeu est dans la phase placement des pions
                moteurJeu.debug("On essaie de placer un pion");
                ihm.jouerAction(new ActionCoup(new CoupAjout(coord, joueurActifID)));
            } else {
                // Si le jeu est dans la phase jeu (déplacement des pions jusqu'à fin de la partie)
                if (selection == null) {
                    // Le joueur choisi lequel de ses pions, il veut déplacer
                    selection = coord;
                    moteurJeu.debug("Le joueur actif choisi un de ses pions");

                    if (moteurJeu.getJeu().joueurDePion(selection) == joueurActifID) {
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
                    moteurJeu.debug("Le joueur choisi où il veut déplacer son pion");

                    if (moteurJeu.getJeu().getPlateau().estCoordValide(coord) && !moteurJeu.getJeu().estPion(coord)) {
                        // Le pion va se déplacer
                        moteurJeu.debug("On essaye de déplacer le pion");
                        plateauGraphique.setTuilesSurbrillance(null);
                        ihm.jouerAction(new ActionCoup(new CoupDeplacement(selection, coord, joueurActifID)));
                        selection = null;
                    } else if (moteurJeu.getJeu().joueurDePion(coord) == joueurActifID) {
                        // Le joueur a choisi un autre de ses pions
                        selection = coord;
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
    }

    /**
     * Lorsque la souris bouge sur la fenêtre
     *
     * @param mouseEvent : l'évenement du mouvement de la souris
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        MoteurJeu moteurJeu = ihm.getMoteurJeu();
        if (!moteurJeu.estPlateauFixer()) {
            plateauGraphique.setPlacementPingouin(-1, -1);
        } else {
            if (moteurJeu.getJeu() != null && moteurJeu.getJoueurActif() instanceof JoueurHumain && moteurJeu.estPhasePlacementPions()) {
                // On modifie la position du pion a placer
                plateauGraphique.setPlacementPingouin(mouseEvent.getX(), mouseEvent.getY());
            } else {
                // On n'affiche pas le pion flottant
                plateauGraphique.setPlacementPingouin(-1, -1);
            }
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
