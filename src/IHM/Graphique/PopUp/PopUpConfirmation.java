package IHM.Graphique.PopUp;

import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PopUpConfirmation extends PopUp {

    private ActionListener confirmationAction;

    public PopUpConfirmation(ActionListener confirmationAction) {
        super("Confirmation");
        this.confirmationAction = confirmationAction;
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        frame.setVisible(true);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Voulez vous quitter ?");
        label.setFont(new Font("Impact", Font.PLAIN, 48));
        panel.add(label, BorderLayout.CENTER);

        panel.add(retour, BorderLayout.PAGE_START);

        JButton confirmation = new JButton("Oui");
        confirmation.setFont(new Font("Impact", Font.PLAIN, 48));
        confirmation.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        confirmation.setBackground(Color.GREEN);
        confirmation.addActionListener(confirmationAction);
        panel.add(confirmation, BorderLayout.SOUTH);

        frame.setContentPane(panel);
    }

    @Override
    public void close(IHMGraphique ihm) {
        super.close(ihm);

        frame.setVisible(false);
    }
}
