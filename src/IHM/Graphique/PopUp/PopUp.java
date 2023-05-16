package IHM.Graphique.PopUp;

import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;

public abstract class PopUp extends JDialog {
    protected Image backgroundImage;

    public PopUp(IHMGraphique ihm, String title, int width, int height) {
        super(ihm.getFrame(), title);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(panel);
        setSize(width, height);
        setModal(true);
    }

    public PopUp(PopUp owner, String title, int width, int height) {
        super(owner, title);
        setSize(width, height);
        setModal(true);
    }

    protected void close() {
        dispose();
    }

    public abstract void init(IHMGraphique ihm);
}
