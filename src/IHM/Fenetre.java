package IHM;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Fenetre {

    private String title;
    JPanel panel;
    JButton retour;

    public Fenetre(String title) {
        this.title = title;
        panel = new JPanel();
        retour = new JButton("Retour");
    }

    public void open(IHMGraphique ihm) {
        ihm.frame.setTitle(title);
        ihm.frame.getContentPane().removeAll();
        ihm.frame.getContentPane().add(panel);

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
