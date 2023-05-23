package IHM.Graphique.PopUp;

import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;

/**
 * Classe abstraite étendue par tous les pop-ups du jeu, elle étend elle même la classe JDialog
 */
public abstract class PopUp extends JDialog {
    protected Image backgroundImage;

    public PopUp(IHMGraphique ihm, String title, int width, int height) {
        super(ihm.getFrame(), title);
        construct(width, height);
    }

    public PopUp(PopUp owner, String title, int width, int height) {
        super(owner, title);
        construct(width, height);
    }

    /**
     * Méthode utilisée par tous les constructeurs
     *
     * @param width  la largeur du pop-up
     * @param height la hauteur du pop-up
     */
    private void construct(int width, int height) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(panel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - 500) / 2,
                (screenSize.height - 490) / 2
        );
        setSize(width, height);
        setModal(true);
    }

    /**
     * Lorsque l'on ferme le pop-up
     */
    protected void close() {
        dispose();
    }

    /**
     * Crée et initialise les différents composants du pop-up
     *
     * @param ihm : l'IHM graphique
     */
    public abstract void init(IHMGraphique ihm);
}
