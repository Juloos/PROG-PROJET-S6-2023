package IHM.Graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Fenetre {

    protected JPanel panel;
    protected JButton retour;
    protected Image backgroundImage;
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
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.retournerPrecedenteFenetre();
            }
        });
    }

    /**
     * Lorsque la fenêtre est fermée
     *
     * @param ihm : l'IHM graphique sur laquelle est affichée la fenêtre
     */
    public void close(IHMGraphique ihm) {
    }
}
