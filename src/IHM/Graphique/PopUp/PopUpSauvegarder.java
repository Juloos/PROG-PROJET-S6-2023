package IHM.Graphique.PopUp;

import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpSauvegarder extends PopUp {

    public PopUpSauvegarder() {
        super("Sauvegarder");
    }

    @Override
    public void creation(IHMGraphique ihm) {
        JButton valider = new JButton("Sauvegarder");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                ihm.getMoteurJeu().appliquerAction(new ActionSauvegarder("test"));
                ihm.retournerPrecedenteFenetre();
            }
        });
        panel.add(valider);

        panel.add(retour);
    }
}
