package IHM.Graphique;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Fenetre {

    protected JPanel panel;
    protected JButton retour;
    String title;
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
