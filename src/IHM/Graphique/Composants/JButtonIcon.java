package IHM.Graphique.Composants;

import javax.swing.*;
import java.awt.*;

public class JButtonIcon extends JButton {

    public JButtonIcon(ImageIcon icon, int size) {
        super();
        init(icon, size, size);
    }

    public JButtonIcon(ImageIcon icon, int width, int height) {
        super();
        init(icon, width, height);
    }

    private void init(ImageIcon icon, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0, 0, 0, 0));
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setIcon(resizeIcon(icon, width, height));
    }

    public void setImageIcon(ImageIcon icon) {
        final int size = Math.min(getWidth(), getHeight());
        setIcon(resizeIcon(icon, size, size));
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        if (width > 0 && height > 0) {
            Image img = icon.getImage();
            Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resized);
        } else {
            return null;
        }
    }
}
