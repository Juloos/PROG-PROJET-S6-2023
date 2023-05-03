package IHM.Graphique.PopUp;

import IHM.Graphique.Fenetre;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class PopUp extends Fenetre {

    JFrame frame;

    public PopUp(String title) {
        super(title);

        frame = new JFrame(title);
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void open(IHMGraphique ihm) {
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                ihm.retournerPrecedenteFenetre();
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
        retour.setFont(new Font("Impact", Font.PLAIN, 48));
        retour.setBackground(Color.RED);
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.retournerPrecedenteFenetre();
            }
        });
    }

    @Override
    public void close(IHMGraphique ihm) {

    }
}
