package IHM.Graphique.Composants;

import IHM.Graphique.Images;

import javax.swing.*;
import java.awt.*;

/**
 * Classe des boutons sans texte et avec uniquement un icone
 */
public class JButtonIcon extends JButton {
    private Image image;

    public JButtonIcon(Image image, int size) {
        super();
        this.image = image;
        init(size, size);
    }

    public JButtonIcon(Image image, int width, int height) {
        super();
        this.image = image;
        init(width, height);
    }


    private void init(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setDimension(width, height);
    }

    public synchronized void setImageIcon(Image icon) {
        this.image = icon;
        if (image != null) {
            setIcon(resizeIcon(getWidth(), getHeight()));
        } else {
            setIcon(null);
        }
    }

    public synchronized void setDimension(int width, int height) {
        if (image != null) {
            setPreferredSize(new Dimension(width, height));
            setMaximumSize(new Dimension(width, height));
            ImageIcon newIcon = resizeIcon(width, height);
            setIcon(newIcon);
        }
    }

    private ImageIcon resizeIcon(int width, int height) {
        if (width > 0 && height > 0) {
            Image resized = Images.resizeImage(image, width, height);
            return new ImageIcon(resized);
        } else {
            return null;
        }
    }
}
