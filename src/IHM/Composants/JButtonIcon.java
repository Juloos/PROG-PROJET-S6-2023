package IHM.Composants;

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

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }
}
