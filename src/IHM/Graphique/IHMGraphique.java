package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranJeu;
import IHM.IHM;
import Modele.Jeu;
import com.sun.istack.internal.NotNull;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

public class IHMGraphique extends IHM implements MouseListener {

    Stack<Fenetre> fenetres;
    JFrame frame;
    boolean enJeu;

    Clip clip;

    public IHMGraphique(MoteurJeu moteurJeu) {
        super(moteurJeu);

        fenetres = new Stack<>();

        frame = new JFrame("");
        try {
            // chargement du fichier audio
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("Wallpaper.wav")); // "res/Wallpaper.wav
            // création du Clip
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // boucle infinie
        } catch (Exception e) {
            e.printStackTrace();
        }

        ouvrirFenetre(new EcranAccueil());

        frame.addMouseListener(this);
        frame.setSize(1500, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void updateAffichage(Jeu jeu) {
        if (enJeu) {
            ((EcranJeu) fenetres.peek()).updatePlateau(jeu.getPlateau());
        }
    }

    @Override
    public void attendreActionJoueur() {

    }

    @Override
    public void afficherMessage(String message) {

    }

    public MoteurJeu getMoteurJeu() {
        return moteurJeu;
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Détruit toutes les fenêtres de l'IHM et retourne au menu d'accueil
     */
    public void fermerFenetres() {

    }

    /**
     * Retourne à la fenêtre précédente et détruit la fenêtre actuelle
     */
    public void retournerPrecedenteFenetre() {
        fenetres.peek().close(this);
        System.out.println("Fermeture de la fenetre : " + fenetres.peek().title);
        fenetres.pop();
        fenetres.peek().open(this);
        System.out.println("Ouverture de la fenetre : " + fenetres.peek().title);
        frame.revalidate();

        enJeu = fenetres.peek() instanceof EcranJeu;
    }

    /**
     * Ouvre une nouvelle fenêtre
     *
     * @param fenetre La nouvelle fenêtre à ouvrir
     */
    @NotNull()
    public void ouvrirFenetre(Fenetre fenetre) {
        fenetres.push(fenetre);
        fenetre.open(this);
        System.out.println("Ouverture de la fenetre : " + fenetres.peek().title);
        frame.revalidate();

        enJeu = fenetres.peek() instanceof EcranJeu;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
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
}
