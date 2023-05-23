package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.IHMGraphique;
import Modele.Jeux.JeuConcret;

import javax.swing.*;
import java.awt.*;

/**
 * Classe abstraite étendue par tous les menus du jeu
 */
public abstract class Ecran {

    /**
     * Vrai si les cliques sont autorisées sur la fenêtre
     */
    public static boolean clickEnable = true;

    // Le panel dans lequel les composants de la fenêtre sont ajoutés
    protected JPanel panel;
    // Le bouton pour retourner à la fenêtre précédente
    protected Button retour;
    // L'image de fond de la fenêtre
    protected Image backgroundImage;
    // Vrai si la création de la fenêtre est terminée
    protected boolean estCree;
    // Le nom affiché en haut de la frame lorsque la fenêtre est ouverte
    String title;

    public Ecran(String title) {
        this.title = title;
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        retour = new Button("Retour");
    }

    /* Méthodes d'instance */

    /**
     * Initialise l'écran et ses composants
     *
     * @param ihm : l'IHM graphique
     */
    public abstract void creation(IHMGraphique ihm);

    /**
     * Lorsque l'écran est ouvert
     *
     * @param ihm : l'IHM graphique
     */
    public void open(IHMGraphique ihm) {
        ihm.getFrame().setTitle(title);

        if (!estCree) {
            estCree = true;
            creation(ihm);
        }

        retour.addActionListener(actionEvent -> ihm.retournerPrecedenteFenetre());
        ihm.getFrame().setContentPane(panel);
    }

    /**
     * Lorsque l'écran est fermé
     *
     * @param ihm : l'IHM graphique
     */
    public void close(IHMGraphique ihm) {
    }

    /**
     * Met à jour l'écran lorsqu'il est ouvert
     *
     * @param ihm : l'IHM graphique
     */
    public void update(IHMGraphique ihm) {
    }

    /**
     * Met à jour l'écran lorsqu'il est ouvert
     *
     * @param jeu : l'état courant du jeu à afficher
     */
    public void update(JeuConcret jeu) {
    }

    /**
     * Met à jour les dimensions des éléments de l'écran lorsque la fenêtre change de dimensions
     *
     * @param frameDimension : les dimensions de la fenêtre
     */
    public void resized(Dimension frameDimension) {
    }

    /**
     * Permet d'afficher le message reçu par l'IHM
     *
     * @param message : le message à afficher
     */
    public void afficherMessage(String message) {
    }

    /**
     * Méthode appelée lorsque le jeu est mit en pause
     */
    public void pause() {
    }

    /**
     * Méthode appelée lorsque le jeu reprend
     */
    public void resume() {
    }
}
