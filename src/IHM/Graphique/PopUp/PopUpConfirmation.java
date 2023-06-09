package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Pop-up pour confirmer une action
 */
public class PopUpConfirmation extends PopUp {

    private final ActionListener confirmationAction;

    public PopUpConfirmation(IHMGraphique ihm, ActionListener confirmationAction) {
        super(ihm, "Confirmation", 500, 300);
        this.confirmationAction = confirmationAction;
        this.backgroundImage = Images.chargerImage("/fondsEcrans/background_popup.png");
    }

    public PopUpConfirmation(PopUp owner, ActionListener confirmationAction) {
        super(owner, "Confirmation", 500, 300);
        this.confirmationAction = confirmationAction;
        this.backgroundImage = Images.chargerImage("/fondsEcrans/background_popup.png");
    }

    /* Méthodes héritées */
    @Override
    public void init(IHMGraphique ihm) {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButtonIcon retour = new JButtonIcon(Images.chargerImage("/boutons/retour.png"), 190, 80);
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(retour);

        retour.addActionListener(actionEvent -> close());

        add(Box.createRigidArea(new Dimension(0, 70)));

        JButtonIcon confirmation = new JButtonIcon(Images.chargerImage("/boutons/quitter.png"), 190, 80);
        confirmation.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmation.addActionListener(confirmationAction);
        add(confirmation);
    }
}
