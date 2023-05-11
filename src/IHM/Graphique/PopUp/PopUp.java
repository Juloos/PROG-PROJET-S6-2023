package IHM.Graphique.PopUp;

import IHM.Graphique.Fenetre;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class PopUp extends Fenetre {

    JFrame frame;

    public PopUp(String title) {
        super(title);

        frame = new JFrame(title);
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void open(IHMGraphique ihm) {
//        retour.setFont(new Font("Impact", Font.PLAIN, 48));
//        retour.setBackground(Color.RED);

        if (!estCree) {
            estCree = true;
            retour.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ihm.retournerPrecedenteFenetre();
                }
            });

            creation(ihm);
        }

        frame.setContentPane(panel);
        frame.setEnabled(true);
        frame.setVisible(true);
    }

    @Override
    public void close(IHMGraphique ihm) {
        frame.setVisible(false);
        frame.dispose();
    }
}
