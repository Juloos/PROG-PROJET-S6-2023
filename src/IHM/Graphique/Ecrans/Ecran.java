package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.IHMGraphique;
import Modele.Jeux.JeuConcret;

import javax.swing.*;
import java.awt.*;

public abstract class Ecran {

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
    public abstract void creation(IHMGraphique ihm);

    /**
     * Lorsque la fenêtre est ouverte
     *
     * @param ihm : l'IHM graphique sur laquelle est affichée la fenêtre
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
     * Lorsque la fenêtre est fermée
     *
     * @param ihm : l'IHM graphique sur laquelle est affichée la fenêtre
     */
    public void close(IHMGraphique ihm) {
    }

    /**
     * Met à jour la fenêtre lorsqu'elle est ouverte
     *
     * @param ihm : l'IHM graphique sur laquelle est affichée la fenêtre
     */
    public void update(IHMGraphique ihm) {
    }

    /**
     * Mise à jour de la fenêtre lorsqu'elle est ouverte
     *
     * @param jeu : l'état courant du jeu à afficher
     */
    public void update(JeuConcret jeu) {
    }

    /**
     * Met à jour les dimensions des éléments de la fenêtre lorsqu'elle change de dimensions
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

    public void pause() {
    }

    public void resume() {
    }
}
