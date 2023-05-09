package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Animations.Animation;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Ecrans.EcranJeu;
import IHM.Graphique.PopUp.PopUpFinPartie;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Actions.ActionCoup;
import Modele.Coord;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Jeux.Jeu;
import com.sun.istack.internal.NotNull;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Stack;

public class IHMGraphique extends IHM implements MouseListener {

    // Pile des fenêtres qui ont été ouverte, la fenêtre ouverte est au sommet de la pile
    Stack<Fenetre> fenetres;
    JFrame frame;
    Clip clip;
    // La représentation graphique du plateau
    PlateauGraphique plateauGraphique;
    // La tuile sélectionnée par le joueur
    Coord selection;
    // L'action que le joueur actif veut faire
    Action actionJoueur;
    // L'animation qui doit se jouer avant de mettre à jour l'affichage
    Animation animation;

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

        ouvrirFenetre(new EcranJeu());

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                fenetres.peek().resized();
            }
        });

        frame.addMouseListener(this);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /* Méthodes héritées de la classe IHM */
    @Override
    public synchronized void updateAffichage(Jeu jeu) {
        if (animation != null) {
            plateauGraphique.setTuilesSurbrillance(null);
            jouerAnimation(animation);
            animation = null;
        }

        fenetres.peek().update(this);

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
        plateauGraphique.repaint();
    }

    @Override
    public Action attendreActionJoueur() {
        actionJoueur = null;
        selection = null;

        while (actionJoueur == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return actionJoueur;
    }

    @Override
    public void afficherMessage(String message) {
        Thread thread = new Thread(new AfficherMessage(this, message));
        thread.start();
    }

    @Override
    public void attendreCreationPartie() {
    }

    /**
     * Fonction appelée lors du lancement de l'IHM
     */
    @Override
    public void run() {
        frame.setVisible(true);
    }

    /* Getters */
    public synchronized JFrame getFrame() {
        return frame;
    }

    public synchronized PlateauGraphique getPlateauGraphique() {
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
     * Joue l'animation en paramètre si une partie est en cours
     *
     * @param animation : l'animation à jouée
     */
    public synchronized void jouerAnimation(Animation animation) {
        animation.run();
    }

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
    public synchronized void mouseClicked(MouseEvent mouseEvent) {
        // On récupère la coordonnée de la tuile sélectionnée (peut être invalide)
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

    /**
     * Classe servant à afficher un message sur l'IHM sans la bloquer
     */
    private class AfficherMessage implements Runnable {
        IHMGraphique ihm;
        String message;

        public AfficherMessage(IHMGraphique ihm, String message) {
            this.ihm = ihm;
            this.message = message;
        }

        @Override
        public void run() {

        }
    }
}
