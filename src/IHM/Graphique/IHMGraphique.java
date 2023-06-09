package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Animations.Animation;
import IHM.Graphique.Animations.AnimationCoupTerminaison;
import IHM.Graphique.Animations.AnimationDeplacementPion;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Ecrans.Ecran;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranJeu;
import IHM.Graphique.PopUp.PopUp;
import IHM.Graphique.PopUp.PopUpFinPartie;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Coord;
import Modele.Coups.CoupDeplacement;
import Modele.Coups.CoupTerminaison;
import Modele.Jeux.JeuConcret;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Classe de l'IHM graphique pour jouer au jeu avec une fenêtre graphique
 */
public class IHMGraphique extends IHM {

    // La représentation graphique du plateau
    final PlateauGraphique plateauGraphique;
    // Le thread pour charger les sprites du jeu
    Thread spritesThread;
    // Pile des écrans qui ont été ouverts, l'écran ouverte est au sommet de la pile
    Stack<Ecran> fenetres;
    JFrame frame;
    Clip clip;
    JoueurHumain joueurActif;
    boolean animationEnCours;
    Timer timerAffichageMessage;

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

    public synchronized boolean getAnimationEnCours() {
        return animationEnCours;
    }

    /* Méthodes héritées de la classe IHM */
    @Override
    public synchronized void updateAffichage(boolean jouerAnimation) {
        joueurActif = null;
        JeuConcret jeu = moteurJeu.getJeu();

        try {
            Animation animation = null;
            if (jouerAnimation) {
                if (jeu.dernierCoupJoue() instanceof CoupDeplacement) {
                    CoupDeplacement deplacement = (CoupDeplacement) jeu.dernierCoupJoue();
                    animation = new AnimationDeplacementPion(this, deplacement);
                } else if (jeu.dernierCoupJoue() instanceof CoupTerminaison) {
                    Joueur joueur = jeu.getJoueur(jeu.getDernierJoueurMort());
                    animation = new AnimationCoupTerminaison(this, joueur.getNom(), joueur.getPions());
                }
            }

            if (animation != null) {
                animationEnCours = true;
                animation.execute();
            } else {
                // Mise à jour de la fenêtre de jeu
                fenetres.peek().update(jeu);
                fenetres.peek().update(this);

                // Mise à jour du plateau graphique
                plateauGraphique.setJeu(jeu);
                plateauGraphique.viderTuilesSurbrillance();

                if (moteurJeu.estPhasePlacementPions()) {
                    // On affiche en surbrillance les tuiles sur lesquelles un pion peut être placées
                    plateauGraphique.ajouterTuilesSurbrillance(jeu.placementsPionValide(), Couleurs.SURBRILLANCE);
                } else {
                    // On affiche en surbrillance les pions du joueur actif
                    ArrayList<Coord> pions = new ArrayList<>(moteurJeu.getJoueurActif().getPions());
                    pions.removeIf(pion -> moteurJeu.getJeu().estPionBloque(pion));

                    plateauGraphique.ajouterTuilesSurbrillance(pions, Couleurs.SURBRILLANCE_PION);
                }
                plateauGraphique.repaint();

                moteurJeu.finUpdateAffichage();
            }
            animationEnCours = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attendreActionJoueur(JoueurHumain joueur) {
        this.joueurActif = joueur;
    }

    @Override
    public void afficherMessage(String message, int duration) {
        fenetres.peek().afficherMessage(message);
        if (timerAffichageMessage != null) {
            timerAffichageMessage.stop();
        }

        if (duration > 0) {
            timerAffichageMessage = new Timer(duration, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    fenetres.peek().afficherMessage("");
                    timerAffichageMessage = null;
                }
            });
            timerAffichageMessage.start();
        }
    }

    @Override
    public void attendreCreationPartie() {
    }

    @Override
    public void debutDePartie() {
        // On enlève toutes les tuiles en surbrillance
        plateauGraphique.viderTuilesSurbrillance();
        ouvrirFenetre(new EcranJeu(this));
    }

    @Override
    public void finDePartie() {
        moteurJeu.debug("Ouverture du pop up de fin de partie");
        plateauGraphique.viderTuilesSurbrillance();
        plateauGraphique.repaint();
        PopUp p = new PopUpFinPartie(this);
        p.init(this);
        p.setVisible(true);
    }

    @Override
    public synchronized void pause() {
        super.pause();
        plateauGraphique.setPlacementPingouin(-1, -1);
        fenetres.peek().pause();
    }

    @Override
    public synchronized void resume() {
        super.resume();
        if (moteurJeu.estPhasePlacementPions()) {
            plateauGraphique.ajouterTuilesSurbrillance(moteurJeu.getJeu().placementsPionValide(), Couleurs.SURBRILLANCE);
        }
        fenetres.peek().resume();
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
        frame = new JFrame("");
//        try {
//            // chargement du fichier audio
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sons/soundtrack.wav"));
//            // création du Clip
//            clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.loop(Clip.LOOP_CONTINUOUSLY); // boucle infinie
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        setVolume(0);

        // On charge les sprites des tuiles dans un thread
        spritesThread = new Thread(Images.getInstance());
        spritesThread.start();

        // La fenêtre par défaut est l'écran d'accueil
        ouvrirFenetre(new EcranAccueil());

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                // Méthode appelée chaque fois que la fenêtre change de dimensions
                fenetres.peek().resized(frame.getSize());
            }
        });

        frame.setSize(1500, 900);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    /* Méthodes d'instance */

    /**
     * Définit quelle actif le joueur actif veut faire
     *
     * @param actionJoueur : l'action que le joueur veut faire
     */
    public synchronized void jouerAction(Action actionJoueur) {
        if (joueurActif != null) {
            joueurActif.setAction(moteurJeu, actionJoueur);
        }
    }

    /**
     * Ferme toutes les fenêtres de l'IHM
     */
    public synchronized void fermerFenetres() {
        for (Ecran ecran : fenetres) {
            ecran.close(this);
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
        fenetres.peek().resized(frame.getSize());

        // Mise à jour de la frame
        frame.validate();
    }

    /**
     * Ouvre une nouvelle fenêtre
     *
     * @param ecran La nouvelle fenêtre à ouvrir
     */
    public synchronized void ouvrirFenetre(Ecran ecran) {
        if (!fenetres.empty()) {
            // On ferme la fenêtre précédente lorsqu'il y en a une
            fenetres.peek().close(this);
        }

        // Ouverture de la nouvelle fenêtre
        fenetres.push(ecran);
        ecran.open(this);
        ecran.resized(frame.getSize());

        // Mise à jour de la frame
        frame.validate();
    }

    /**
     * Permet de changer le volume de la musique
     *
     * @param volume : le volume de la musique
     */
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
}
