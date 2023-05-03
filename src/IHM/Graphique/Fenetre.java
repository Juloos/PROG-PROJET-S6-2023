package IHM.Graphique;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Fenetre {

    protected JPanel panel;
    protected JButton retour;
    String title;

    public Fenetre(String title) {
        this.title = title;
        panel = new JPanel();
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
