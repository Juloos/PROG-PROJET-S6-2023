package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Animations.Animation;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Ecrans.Ecran;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranJeu;
import IHM.Graphique.PopUp.PopUp;
import IHM.Graphique.PopUp.PopUpFinPartie;
import IHM.Graphique.Sprites.Sprites;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Coord;
import Modele.Jeux.JeuConcret;
import com.sun.istack.internal.NotNull;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class IHMGraphique extends IHM {

    // La représentation graphique du plateau
    final PlateauGraphique plateauGraphique;
    // Le thread pour charger les sprites du jeu
    Thread spritesThread;
    // Pile des écrans qui ont été ouverts, l'écran ouverte est au sommet de la pile
    Stack<Ecran> fenetres;
    JFrame frame;
    Clip clip;
    // L'action que le joueur actif veut faire
    Action actionJoueur;
    volatile boolean animationEnCours;
    Animation animation;

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

    /* Setters */
    public synchronized void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public synchronized void setActionJoueur(Action actionJoueur) {
        this.actionJoueur = actionJoueur;
    }

    /* Méthodes héritées de la classe IHM */
    @Override
    public synchronized void updateAffichage(JeuConcret jeu) {
        try {
            // Mise à jour de la fenêtre de jeu
            fenetres.peek().update(jeu);

            if (animation != null) {
                animationEnCours = true;
                animation.start();
                animation.play();
                animation.stop();
                animation = null;
                animationEnCours = false;
            }

            fenetres.peek().update(this);

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
            plateauGraphique.repaint();
        } catch (Exception e) {
        }
    }

    @Override
    public Action attendreActionJoueur() {
        actionJoueur = null;

        System.out.println("Attente d'une action du joueur");
        // Tant qu'on a pas d'action de la part du joueur et que le partie est toujours en cours
        while (actionJoueur == null && moteurJeu.partieEnCours()) ;

        return actionJoueur;
    }

    @Override
    public void afficherMessage(String message, int duration) {
        fenetres.peek().afficherMessage(message);
        if (duration > 0) {
            Timer timer = new Timer(duration, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    fenetres.peek().afficherMessage("");
                }
            });
            timer.start();
        }
    }

    @Override
    public void attendreCreationPartie() {
    }

    @Override
    public void debutDePartie() {
        // On enlève toutes les tuiles en surbrillance
        plateauGraphique.setPionsSurbrillance(null);
        ouvrirFenetre(new EcranJeu(this));
    }

    @Override
    public void finDePartie() {
        moteurJeu.debug("Ouverture du pop up de fin de partie");
        PopUp p = new PopUpFinPartie(this);
        p.init(this);
        p.setVisible(true);
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

        // Mise à jour de la frame
        frame.validate();
    }

    /**
     * Ouvre une nouvelle fenêtre
     *
     * @param ecran La nouvelle fenêtre à ouvrir
     */
    @NotNull()
    public synchronized void ouvrirFenetre(Ecran ecran) {
        if (!fenetres.empty()) {
            // On ferme la fenêtre précédente lorsqu'il y en a une
            fenetres.peek().close(this);
        }

        // Ouverture de la nouvelle fenêtre
        fenetres.push(ecran);
        ecran.open(this);
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
}
