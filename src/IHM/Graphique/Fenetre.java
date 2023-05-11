package IHM.Graphique;

import Modele.Jeux.Jeu;

import javax.swing.*;
import java.awt.*;

public abstract class Fenetre {

    protected JPanel panel;
    // Le bouton pour retourner à la fenêtre précédente
    protected JButton retour;
    // L'image de fond des fenêtres
    protected Image backgroundImage;
    // Vrai si la création de la fenêtre est terminée
    protected boolean estCree;
    // Le nom affiché en haut de la frame lorsque la fenêtre est ouverte
    String title;

    public Fenetre(String title) {
        this.title = title;
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        retour = new JButton("Retour");
    }

    /**
     * Lorsque la fenêtre est ouverte
     *
     * @param ihm : l'IHM graphique sur laquelle est affichée la fenêtre
     */
    public void open(IHMGraphique ihm) {
        ihm.frame.setTitle(title);

        if (!estCree) {
            estCree = true;
            creation(ihm);
        }

        retour.addActionListener(actionEvent -> ihm.retournerPrecedenteFenetre());
    }

    public abstract void creation(IHMGraphique ihm);

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

    public void update(Jeu jeu) {
    }

    /**
     * Met à jour les dimensions des éléments de la fenêtre lorsqu'elle est ouverte
     */
    public void resized() {
    }

    /**
     * Permet d'afficher le message reçu par l'IHM
     *
     * @param message : le message à afficher
     */
    public void afficherMessage(String message) {
    }
}
