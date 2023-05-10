package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Animations.Animation;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.PopUp.PopUpFinPartie;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Actions.ActionCoup;
import Modele.Coord;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Jeux.Jeu;
import Modele.Joueurs.JoueurHumain;
import com.sun.istack.internal.NotNull;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

public class IHMGraphique extends IHM implements MouseListener, MouseMotionListener {

    final UpdateAffichage updateAffichage;
    // La représentation graphique du plateau
    final PlateauGraphique plateauGraphique;
    // Pile des fenêtres qui ont été ouverte, la fenêtre ouverte est au sommet de la pile
    Stack<Fenetre> fenetres;
    JFrame frame;
    Clip clip;
    // La tuile sélectionnée par le joueur
    Coord selection;
    // L'action que le joueur actif veut faire
    Action actionJoueur;
    // L'animation qui doit se jouer avant de mettre à jour l'affichage
    volatile Animation animation;
    volatile boolean animationEnCours, miseAJourAffichage;

    public IHMGraphique(MoteurJeu moteurJeu) {
        super(moteurJeu);

        fenetres = new Stack<>();

        frame = new JFrame("");
//        try {
//            // chargement du fichier audio
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("Wallpaper.wav")); // "res/Wallpaper.wav
//            // création du Clip
//            clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.loop(Clip.LOOP_CONTINUOUSLY); // boucle infinie
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        plateauGraphique = new PlateauGraphique();
        Thread pgt = new Thread(plateauGraphique);
        pgt.start();

        updateAffichage = new UpdateAffichage(this);
        updateAffichage.start();

        ouvrirFenetre(new EcranAccueil());

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                fenetres.peek().resized();
            }
        });

        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /* Méthodes héritées de la classe IHM */
    @Override
    public synchronized void updateAffichage(Jeu jeu) {
        // Sert uniquement à mettre à jour l'affichage de l'IHM
        System.out.println("Je veux mettre à jour");
        updateAffichage.update(jeu, animation);
    }

    public void pauseGame() {
    }

    @Override
    public void pause() {
        System.out.println("IHM mise en pause");
    }

    @Override
    public void resume() {
        System.out.println("IHM est reparti");
    }

    @Override
    public Action attendreActionJoueur() {
        actionJoueur = null;
        selection = null;

        while (actionJoueur == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

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
    public void terminer() {
        getMoteurJeu().debug("Fermeture de toutes les fenêtres");
        fermerFenetres();
        frame.dispose();

        updateAffichage.interrupt();

        getMoteurJeu().debug("JFrame fermée");
    }

    /* Getters */
    public synchronized JFrame getFrame() {
        return frame;
    }

    public PlateauGraphique getPlateauGraphique() {
        return plateauGraphique;
    }

    /* Setters */

    /**
     * Définit la future animation à jouer lors de la prochaine mise à jour de l'IHM
     *
     * @param animation : l'animation à jouer
     */
    public synchronized void setAnimation(Animation animation) {
        this.animation = animation;
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

    /* Méthodes d'instance */

    /**
     * Détruit toutes les fenêtres de l'IHM
     */
    public synchronized void fermerFenetres() {
        for (Fenetre fenetre : fenetres) {
            fenetre.close(this);
        }
        fenetres.clear();
    }

    /**
     * Retourne à la fenêtre précédente et détruit la fenêtre courante
     */
    public synchronized void retournerPrecedenteFenetre() {
        // Fermeture de la fenêtre courante
        fenetres.peek().close(this);
        fenetres.pop();

        // Ouverture de la fenêtre précédente
        fenetres.peek().open(this);
        fenetres.peek().resized();

        // Mise à jour de la frame
        frame.revalidate();
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

        System.out.println("Ouverture d'une nouvelle fenêtre");
        // Ouverture de la nouvelle fenêtre
        fenetres.push(fenetre);
        fenetre.open(this);
        fenetres.peek().resized();

        // Mise à jour de la frame
        frame.revalidate();
    }

    /**
     * Lorsqu'un joueur clique sur la fenêtre
     *
     * @param mouseEvent : l'événement du clic de la souris
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // On récupère la coordonnée de la tuile sélectionnée (peut être invalide)
        try {
            if (true) {
                System.out.println("Click");
                Coord coord = plateauGraphique.getClickedTuile(mouseEvent.getX(), mouseEvent.getY());

                if (getMoteurJeu().getJeu().getPlateau().estCoordValide(coord)) {
                    // Si c'est une coordonnée valide sur le plateau
                    if (getMoteurJeu().estPhasePlacementPions()) {
                        // Si le jeu est dans la phase placement des pions
                        selection = coord;
                        actionJoueur = new ActionCoup(new CoupAjout(selection, getMoteurJeu().getJoueurActif().id));
                    } else if (actionJoueur == null) {
                        // Si le jeu est dans la phase jeu (déplacement des pions jusqu'à fin de la partie)
                        if (selection == null) {
                            // Le joueur choisi lequel de ses pions, il veut déplacer
                            selection = coord;

                            if (getMoteurJeu().getJeu().getPlateau().estCoordValide(selection) && getMoteurJeu().getJeu().estPion(selection)
                                    && getMoteurJeu().getJeu().joueurDePion(selection) == getMoteurJeu().getJoueurActif().id) {
                                // On affiche en surbrillance toutes les tuiles sur lesquelles le pion sélectionné peut aller
                                ArrayList<Coord> coords = getMoteurJeu().getJeu().deplacementsPion(selection);
                                coords.add(selection);
                                plateauGraphique.setTuilesSurbrillance(coords);
                                plateauGraphique.repaint();
                            } else {
                                // Le joueur n'a pas choisi un de ses pions
                                selection = null;
                            }

                        } else {
                            // Le joueur choisi sur quelle tuile il veut déplacer le pion qu'il a sélectionné
                            Coord cible = coord;

                            if (getMoteurJeu().getJeu().getPlateau().estCoordValide(cible) && !getMoteurJeu().getJeu().estPion(cible)) {
                                // Le pion va se déplacer
                                actionJoueur = new ActionCoup(new CoupDeplacement(selection, cible, getMoteurJeu().getJoueurActif().id));
                            } else if (getMoteurJeu().getJeu().joueurDePion(cible) == getMoteurJeu().getJoueurActif().id) {
                                // Le joueur a choisi un autre de ses pions
                                selection = cible;

                                ArrayList<Coord> coords = getMoteurJeu().getJeu().deplacementsPion(selection);
                                coords.add(selection);
                                plateauGraphique.setTuilesSurbrillance(coords);
                                plateauGraphique.repaint();
                            }
                        }
                    }
                } else {
                    afficherMessage("Coordonnées invalide");
                }
            }
        } catch (Exception e) {
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

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (moteurJeu.getJeu() != null && moteurJeu.getJoueurActif() instanceof JoueurHumain && moteurJeu.estPhasePlacementPions()) {
            plateauGraphique.setPlacementPingouin(mouseEvent.getX(), mouseEvent.getY());
        } else {
            plateauGraphique.setPlacementPingouin(-1, -1);
        }
    }

    private class UpdateAffichage extends Thread {

        final IHMGraphique ihm;
        volatile Jeu jeu;
        volatile Animation animation;

        public UpdateAffichage(IHMGraphique ihm) {
            super();
            this.ihm = ihm;
            this.jeu = null;
            this.animation = null;
        }

        public synchronized void update(Jeu jeu, Animation animation) {
            System.out.println("On met jeu à jour");
            this.jeu = jeu;
            this.animation = animation;
        }

        @Override
        public void run() {
            while (true) {
                while (jeu == null) ;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Appel à mise à jour");
                if (animation != null) {
                    System.out.println("Début animation");
                    plateauGraphique.setTuilesSurbrillance(null);
                    animationEnCours = true;
                    animation.play();
                    animationEnCours = false;
                    animation = null;
                    System.out.println("Fin animation");
                    getMoteurJeu().debug("Fin animation niveau IHM");
                }
                System.out.println("Mise à jour");

                fenetres.peek().update(ihm);
                plateauGraphique.setJeu(jeu);
                if (jeu.estTermine()) {
                    ouvrirFenetre(new PopUpFinPartie());
                } else {
                    if (getMoteurJeu().estPhasePlacementPions()) {
                        plateauGraphique.setTuilesSurbrillance(jeu.placementsPionValide());
                    } else {
                        plateauGraphique.setTuilesSurbrillance(null);
                    }
                }
                getMoteurJeu().debug("Mise à jour affichage");
                plateauGraphique.repaint();
                System.out.println("Fin mise à jour");

                jeu = null;
                animation = null;
            }
        }
    }
}
