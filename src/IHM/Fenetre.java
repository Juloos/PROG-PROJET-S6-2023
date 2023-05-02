package IHM;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Fenetre {

    String title;
    JPanel panel;
    JButton retour;
    Image backgroundImage;

    public Fenetre(String title) {
        this.title = title;
        panel = new JPanel(){
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, null);
        }};
        retour = new JButton("Retour");
    }

    public void open(IHMGraphique ihm) {
        ihm.frame.setTitle(title);
        ihm.frame.getContentPane().removeAll();
        ihm.frame.getContentPane().add(panel);

        panel.removeAll();

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.retournerPrecedenteFenetre();
            }
        });
    }

    public void close(IHMGraphique ihm) {
    }
}
