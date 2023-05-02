package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Voulez vous quitter ?");
        panel.add(label, BorderLayout.CENTER);

        panel.add(retour, BorderLayout.PAGE_START);

        JButton confirmation = new JButton("Oui");
        confirmation.addActionListener(confirmationAction);
        panel.add(confirmation, BorderLayout.PAGE_END);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    @Override
    public void close(IHMGraphique ihm) {
        super.close(ihm);

        frame.setVisible(false);
    }
}
