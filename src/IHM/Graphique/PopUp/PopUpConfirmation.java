package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpConfirmation extends PopUp {

    private final ActionListener confirmationAction;

    public PopUpConfirmation(IHMGraphique ihm, ActionListener confirmationAction) {
        super(ihm, "Confirmation", 500, 300);
        this.backgroundImage = new ImageIcon("res/fondsEcrans/background_popup.png").getImage();
        this.confirmationAction = confirmationAction;
    }

    public PopUpConfirmation(PopUp owner, ActionListener confirmationAction) {
        super(owner, "Confirmation", 500, 300);
        this.backgroundImage = new ImageIcon("res/fondsEcrans/background_popup.png").getImage();
        this.confirmationAction = confirmationAction;
    }

    @Override
    public void init(IHMGraphique ihm) {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButtonIcon retour = new JButtonIcon(new ImageIcon("res/back.png"), 190, 80);
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(retour);

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
            }
        });

        JLabel label = new JLabel("Voulez-vous quitter ?");
        label.setFont(new Font("Impact", Font.PLAIN, 48));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);

        JButtonIcon confirmation = new JButtonIcon(new ImageIcon("res/oui.png"), 190, 80);
        confirmation.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmation.addActionListener(confirmationAction);
        add(confirmation);
    }
}
