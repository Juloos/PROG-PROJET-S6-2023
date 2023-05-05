package IHM.Graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Fenetre {

    protected JPanel panel;
    protected JButton retour;
    protected Image backgroundImage;
    protected boolean estCree;
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

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.retournerPrecedenteFenetre();
            }
        });
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
     * Met à jour la fenêtre lorqu'elle est ouverte
     *
     * @param ihm : l'IHM graphique sur laquelle est affichée la fenêtre
     */
    public void update(IHMGraphique ihm) {
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
