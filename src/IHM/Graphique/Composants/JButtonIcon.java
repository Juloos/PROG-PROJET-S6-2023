package IHM.Graphique.Composants;

import IHM.Graphique.Images.Images;

import javax.swing.*;
import java.awt.*;

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

        setIcon(resizeIcon(width, height));
    }

    public void setImageIcon(Image icon) {
        this.image = icon;
        if (image != null) {
            ImageIcon newIcon = resizeIcon(getWidth(), getHeight());
            setIcon(newIcon);
        } else {
            setIcon(null);
        }
    }

    public void setDimension(int width, int height) {
        if (getIcon() != null) {
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
