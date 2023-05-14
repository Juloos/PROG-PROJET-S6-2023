package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranJeu;
import IHM.Graphique.PopUp.PopUpFinPartie;
import IHM.Graphique.Sprites.Sprites;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Actions.ActionCoup;
import Modele.Coord;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Jeux.JeuConcret;
import Modele.Joueurs.JoueurHumain;
import com.sun.istack.internal.NotNull;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class IHMGraphique extends IHM implements MouseListener, MouseMotionListener {

    // La représentation graphique du plateau
    final PlateauGraphique plateauGraphique;
    // Le thread pour charger les sprites du jeu
    Thread spritesThread;
    // Pile des fenêtres qui ont été ouvertes, la fenêtre ouverte est au sommet de la pile
    Stack<Fenetre> fenetres;
    JFrame frame;
    Clip clip;
    // La tuile sélectionnée par le joueur
    Coord selection;
    // L'action que le joueur actif veut faire
    Action actionJoueur;

    public IHMGraphique(MoteurJeu moteurJeu) {
        super(moteurJeu);
        fenetres = new Stack<>();
        plateauGraphique = new PlateauGraphique();
    }

    /* Getters */
    public synchronized JFrame getFrame() {
        return frame;
    }

    public PlateauGraphique getPlateauGraphique() {
        return plateauGraphique;
    }

    /* Méthodes héritées de la classe IHM */
    @Override
    public synchronized void updateAffichage(JeuConcret jeu) {
        try {
            // Mise à jour de la fenêtre courante
            fenetres.peek().update(jeu);
            
            // Mise à jour du plateau graphique
            plateauGraphique.setJeu(jeu);
            if (moteurJeu.estPhasePlacementPions()) {
                // On affiche en surbrillance les tuiles sur lesquelles un pion peut être placées
                plateauGraphique.setTuilesSurbrillance(jeu.placementsPionValide());
            } else {
                // On affiche en surbrillance les pions du joueur actif
                plateauGraphique.setTuilesSurbrillance(null);

                Set<Coord> pions = jeu.getJoueur().getPions();
                List<Coord> coords = new ArrayList<>();
                coords.addAll(pions);

                plateauGraphique.setPionsSurbrillance(coords);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public Action attendreActionJoueur() {
        actionJoueur = null;
        selection = null;

        // Tant qu'on a pas d'action de la part du joueur et que le partie est toujours en cours
        while (actionJoueur == null && moteurJeu.partieEnCours()) ;

        return actionJoueur;
    }

    @Override
    public void afficherMessage(String message) {
        fenetres.peek().afficherMessage(message);
    }

    @Override
    public void attendreCreationPartie() {
    }

    @Override
    public void debutDePartie() {
        // On enlève toutes les tuiles en surbrillance
        plateauGraphique.setPionsSurbrillance(null);
        ouvrirFenetre(new EcranJeu());
    }

    @Override
    public void finDePartie() {
        moteurJeu.debug("Ouverture du pop up de fin de partie");
        ouvrirFenetre(new PopUpFinPartie());
    }

    @Override
    public synchronized void pause() {
        System.out.println("Mise en pause du jeu");
        super.pause();
        plateauGraphique.setPlacementPingouin(-1, -1);
    }

    @Override
    public void terminer() {
        getMoteurJeu().debug("Fermeture de toutes les fenêtres");
        fermerFenetres();
        frame.dispose();

        spritesThread.interrupt();

        getMoteurJeu().debug("JFrame fermée");
    }

    @Override
    public void run() {
        super.run();

        frame = new JFrame("");
//        try {
//            // chargement du fichier audio
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("res/sons/soundtrack.wav")); // "res/Wallpaper.wav
//            // création du Clip
//            clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.loop(Clip.LOOP_CONTINUOUSLY); // boucle infinie
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        setVolume(0);

        // On charge les sprites des tuiles dans un thread
        spritesThread = new Thread(Sprites.getInstance());
        spritesThread.start();

        // La fenêtre par défaut est l'écran d'accueil
        ouvrirFenetre(new EcranAccueil());

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                // Méthode appelée chaque fois que la fenêtre change de dimensions
                fenetres.peek().resized();
            }
        });

        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);

        frame.setSize(1500, 900);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    /* Méthodes d'instance */

    /* Méthodes pour gérer les fenêtres */

    /**
     * Ferme toutes les fenêtres de l'IHM
     */
    public synchronized void fermerFenetres() {
        for (Fenetre fenetre : fenetres) {
            fenetre.close(this);
        }
        fenetres.clear();
    }

    /**
     * Retourne à la fenêtre précédente et ferme la fenêtre courante
     */
    public synchronized void retournerPrecedenteFenetre() {
        // Fermeture de la fenêtre courante
        fenetres.peek().close(this);
        fenetres.pop();

        // Ouverture de la fenêtre précédente
        fenetres.peek().open(this);

        // Mise à jour de la frame
        frame.validate();
    }

    /**
     * Ouvre une nouvelle fenêtre
     *
     * @param fenetre La nouvelle fenêtre à ouvrir
     */
    @NotNull()
    public synchronized void ouvrirFenetre(Fenetre fenetre) {
        if (!fenetres.empty()) {
            // On ferme la fenêtre précédente lorsqu'il y en a une
            fenetres.peek().close(this);
        }

        // Ouverture de la nouvelle fenêtre
        fenetres.push(fenetre);
        fenetre.open(this);
        moteurJeu.debug("Ouverture d'une nouvelle fenêtre");

        // Mise à jour de la frame
        frame.validate();
    }

    /* */

    /**
     * Met à jour l'affichage de la fenêtre courante
     */
    public synchronized void updateAffichage() {
        fenetres.peek().update(this);
        fenetres.peek().resized();
    }

    public void setVolume(float volume) {
        if (clip != null) {
            // Récupération du contrôle de volume du clip
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Conversion de la valeur de volume donnée en dB (décibels)
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            // Réglage du contrôle de volume
            gainControl.setValue(dB);
        }
    }

    /* Méthodes d'événements */

    /**
     * Lorsqu'un joueur clique sur la fenêtre
     *
     * @param mouseEvent : l'événement du clic de la souris
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // On récupère la coordonnée de la tuile sélectionnée (peut être invalide)
        try {
            if (isPaused) {
                // On ne fait rien si la partie est en pause
                throw new Exception("Jeu en pause");
            }

            Coord coord = plateauGraphique.getClickedTuile(mouseEvent.getX(), mouseEvent.getY());
            moteurJeu.debug("Tuile clickée " + coord);

            if (getMoteurJeu().estPhasePlacementPions()) {
                // Si le jeu est dans la phase placement des pions
                actionJoueur = new ActionCoup(new CoupAjout(coord, getMoteurJeu().getJoueurActif().id));
            } else if (actionJoueur == null) {
                // Si le jeu est dans la phase jeu (déplacement des pions jusqu'à fin de la partie)
                if (selection == null) {
                    // Le joueur choisi lequel de ses pions, il veut déplacer
                    selection = coord;
                    moteurJeu.debug("Le joueur actif choisi un de ses pions");

                    if (getMoteurJeu().getJeu().joueurDePion(selection) == getMoteurJeu().getJoueurActif().id) {
                        // On affiche en surbrillance toutes les tuiles sur lesquelles le pion sélectionné peut aller
                        moteurJeu.debug("Affichage des tuiles accessible pour le pion choisi");
                        plateauGraphique.setTuilesSurbrillance(getMoteurJeu().getJeu().deplacementsPion(selection));
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

                    if (getMoteurJeu().getJeu().getPlateau().estCoordValide(cible) && !getMoteurJeu().getJeu().estPion(cible)) {
                        // Le pion va se déplacer
                        moteurJeu.debug("On essaye de déplacer le pion");
                        actionJoueur = new ActionCoup(new CoupDeplacement(selection, cible, getMoteurJeu().getJoueurActif().id));
                        plateauGraphique.setTuilesSurbrillance(null);
                    } else if (getMoteurJeu().getJeu().joueurDePion(cible) == getMoteurJeu().getJoueurActif().id) {
                        // Le joueur a choisi un autre de ses pions
                        selection = cible;
                        moteurJeu.debug("Le joueur a choisi un autre de ses pions");

                        ArrayList<Coord> coords = getMoteurJeu().getJeu().deplacementsPion(selection);
                        coords.add(selection);
                        plateauGraphique.setTuilesSurbrillance(coords);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
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

    /**
     * Lorsque la souris bouge sur la fenêtre
     *
     * @param mouseEvent : l'évenement du mouvement de la souris
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (!isPaused) {
            // Si la partie n'est pas en pause
            if (moteurJeu.getJeu() != null && moteurJeu.getJoueurActif() instanceof JoueurHumain && moteurJeu.estPhasePlacementPions()) {
                // On modifie la position du pion a placer
                plateauGraphique.setPlacementPingouin(mouseEvent.getX(), mouseEvent.getY());
            } else {
                // On n'affiche pas le pion flottant
                plateauGraphique.setPlacementPingouin(-1, -1);
            }
        }
    }
}
