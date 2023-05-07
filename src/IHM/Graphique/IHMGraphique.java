package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Animations.Animation;
import IHM.Graphique.Animations.AnimationDeplacementPion;
import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Ecrans.EcranJeu;
import IHM.Graphique.PopUp.PopUpFinPartie;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Actions.ActionCoup;
import Modele.Coord;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Jeu.Jeu;
import com.sun.istack.internal.NotNull;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class IHMGraphique extends IHM implements MouseListener {

    // Pile des fenêtres qui ont été ouverte, la fênetre ouverte est au sommet de la pile
    Stack<Fenetre> fenetres;
    JFrame frame;
    boolean enJeu;
    Clip clip;
    // La réprésentation graphique du plateau
    PlateauGraphique plateauGraphique;
    // La tuile sélectionné par le joueur
    Coord selection;
    // L'action que le joueur fait
    Action actionJoueur;

    boolean animationEnCours;
    Animation animation;
    WaitActionJoueur waitActionJoueur;

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

    @Override
    public synchronized void updateAffichage(Jeu jeu) {
        if(animation != null) {
            plateauGraphique.setTuilesSurbrillance(null);
            jouerAnimation(animation);
            animation = null;
        }

        fenetres.peek().update(this);

        plateauGraphique.setJeu(jeu);
        if (jeu.estTermine()) {
            ouvrirFenetre(new PopUpFinPartie());
        } else {
            if (moteurJeu.estPhasePlacementPions()) {
                plateauGraphique.setTuilesSurbrillance(jeu.placememntPionValide());
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

        WaitActionJoueur waitActionJoueur = new WaitActionJoueur();
        waitActionJoueur.start();
        try {
            waitActionJoueur.join();
        } catch (Exception e) {
        }

        return actionJoueur;
    }


    @Override
    public synchronized void afficherMessage(String message) {
        Thread thread = new Thread(new AfficherMessage(this, message));
        thread.start();
    }

    public synchronized void jouerAnimation(Animation animation) {
        animation.run();
//        Thread thread = new Thread(animation);
//        animationEnCours = true;
//        try {
//            thread.start();
//            thread.join();
//        } catch (Exception e) {
//        }
        animationEnCours = false;
    }

    public synchronized MoteurJeu getMoteurJeu() {
        return moteurJeu;
    }

    public synchronized JFrame getFrame() {
        return frame;
    }

    public synchronized PlateauGraphique getPlateauGraphique() {
        return plateauGraphique;
    }

    /**
     * Détruit toutes les fenêtres de l'IHM et retourne au menu d'accueil
     */
    public synchronized void fermerFenetres() {
        for (Fenetre fenetre : fenetres) {
            fenetre.close(this);
        }
        fenetres.clear();
    }

    /**
     * Retourne à la fenêtre précédente et détruit la fenêtre actuelle
     */
    public synchronized void retournerPrecedenteFenetre() {
        fenetres.peek().close(this);
//        System.out.println("Fermeture de la fenetre : " + fenetres.peek().title);
        fenetres.pop();
        fenetres.peek().open(this);
        fenetres.peek().resized();
//        System.out.println("Ouverture de la fenetre : " + fenetres.peek().title);
        frame.revalidate();

        enJeu = fenetres.peek() instanceof EcranJeu;
    }

    /**
     * Ouvre une nouvelle fenêtre
     *
     * @param fenetre La nouvelle fenêtre à ouvrir
     */
    @NotNull()
    public synchronized void ouvrirFenetre(Fenetre fenetre) {
        if (!fenetres.empty()) {
            fenetres.peek().close(this);
        }

        fenetres.push(fenetre);
        fenetre.open(this);
        fenetres.peek().resized();
//        System.out.println("Ouverture de la fenetre : " + fenetres.peek().title);
        frame.revalidate();

        enJeu = fenetres.peek() instanceof EcranJeu;
    }

    /**
     * Lorsqu'un joueur clique sur la fenêtre
     *
     * @param mouseEvent
     */
    @Override
    public synchronized void mouseClicked(MouseEvent mouseEvent) {
        Coord coord = plateauGraphique.getClickedTuile(mouseEvent.getX(), mouseEvent.getY());
        if (moteurJeu.getJeu().getPlateau().estCoordValide(coord)) {
            if (moteurJeu.estPhasePlacementPions()) {
                selection = coord;
                actionJoueur = new ActionCoup(new CoupAjout(selection, moteurJeu.getJoueurActif().id));
            } else if (actionJoueur == null) {
                if (selection == null) {
                    selection = coord;
                    if (moteurJeu.getJeu().getPlateau().estCoordValide(selection) && moteurJeu.getJeu().estPion(selection)
                            && moteurJeu.getJeu().joueurDePion(selection) == moteurJeu.getJoueurActif().id) {
//                        System.out.println("Selection");

                        ArrayList<Coord> coords = moteurJeu.getJeu().deplacementsPion(selection);
                        coords.add(selection);
                        plateauGraphique.setTuilesSurbrillance(coords);
                        plateauGraphique.repaint();
                    } else {
                        selection = null;
                    }

                } else {
                    Coord cible = coord;
//                    System.out.println("Cible");

                    if (moteurJeu.getJeu().getPlateau().estCoordValide(cible) && !moteurJeu.getJeu().estPion(cible)) {
                        actionJoueur = new ActionCoup(new CoupDeplacement(selection, cible, moteurJeu.getJoueurActif().id));

                        if(actionJoueur.peutAppliquer(moteurJeu)) {
                            List<Coord> coords = new ArrayList<>();
                            int decalage = selection.getDecalage(cible);
                            Coord current = new Coord(selection.q, selection.r);

                            while(!current.equals(cible)) {
                                coords.add(current);
                                current = current.decale(decalage);
                            }
                            coords.add(cible);

                            Coord[] array = new Coord[coords.size()];
                            animation = new AnimationDeplacementPion(this, coords.toArray(array));
                        }

                    } else if (moteurJeu.getJeu().joueurDePion(cible) == moteurJeu.getJoueurActif().id) {
                        selection = cible;

                        ArrayList<Coord> coords = moteurJeu.getJeu().deplacementsPion(selection);
                        coords.add(selection);
                        plateauGraphique.setTuilesSurbrillance(coords);
                        plateauGraphique.repaint();
                    }

                }
            }
        } else {
            afficherMessage("Coordonées invalide");
        }
        System.out.println();
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

    /**
     * Fonction utilisée pour attendre que le joueur fasse une action sans bloquer l'IHM
     */
    @Override
    public void run() {
//        System.out.println("Lancement IHM");

        frame.setVisible(true);
    }

    synchronized Action getActionJoueur() {
        return actionJoueur;
    }

    private class WaitActionJoueur extends Thread {
        @Override
        public void run() {
            while(getActionJoueur() == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class AfficherMessage implements Runnable {
        IHMGraphique ihm;
        String message;

        public AfficherMessage(IHMGraphique ihm, String message) {
            this.ihm = ihm;
            this.message = message;
        }

        @Override
        public void run() {
            ihm.fenetres.peek().afficherMessage(message);
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            ihm.fenetres.peek().afficherMessage("");
        }
    }
}
