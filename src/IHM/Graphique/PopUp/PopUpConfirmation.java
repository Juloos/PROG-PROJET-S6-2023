package IHM.Graphique.PopUp;

import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpConfirmation extends PopUp {

    private ActionListener confirmationAction;

    public PopUpConfirmation(IHMGraphique ihm, ActionListener confirmationAction) {
        super(ihm, "Confirmation", 500, 300);
        this.confirmationAction = confirmationAction;
    }

    public PopUpConfirmation(PopUp owner, ActionListener confirmationAction) {
        super(owner, "Confirmation", 500, 300);
        this.confirmationAction = confirmationAction;
    }

    @Override
    public void init(IHMGraphique ihm) {
        setLayout(new BorderLayout());

        JButton retour = new JButton("Retour");
        retour.setFont(new Font("Impact", Font.PLAIN, 48));
        retour.setBackground(Color.RED);
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
            }
        });

        JLabel label = new JLabel("Voulez vous quitter ?");
        label.setFont(new Font("Impact", Font.PLAIN, 48));
        add(label, BorderLayout.CENTER);

        add(retour, BorderLayout.PAGE_START);

        JButton confirmation = new JButton("Oui");
        confirmation.setFont(new Font("Impact", Font.PLAIN, 48));
        confirmation.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        confirmation.setBackground(Color.GREEN);
        confirmation.addActionListener(confirmationAction);
        add(confirmation, BorderLayout.SOUTH);
    }
}
