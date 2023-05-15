package IHM.Graphique.Composants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Button extends JButton {
    private int radius = 0;
    private Shape shape;

    public Button(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
        }
        g2.setClip(shape);

        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, radius, radius);

        g2.setColor(Color.WHITE); // Définir la couleur du texte en blanc
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);
    }
}
