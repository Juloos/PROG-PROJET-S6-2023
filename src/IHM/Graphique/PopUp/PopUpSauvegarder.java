package IHM.Graphique.PopUp;

import IHM.Colors;
import IHM.Graphique.IHMGraphique;
import Modele.Actions.ActionSauvegarder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpSauvegarder extends PopUp {

    public PopUpSauvegarder() {
        super("Sauvegarder");
    }

    @Override
    public void creation(IHMGraphique ihm) {
        JButton valider = new JButton("Sauvegarder");
        JLabel nomLabel = new JLabel("Nom sauvegarde : ");
        nomLabel.setFont(new Font("Impact", Font.PLAIN, 20));
        JTextField nom = new JTextField("Sauv 1");
        nom.setMaximumSize(new Dimension(200, 40));
        panel.add(nomLabel);
        panel.add(nom);
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionSauvegarder("sauvegarde/" + nom.getText()));
                ihm.retournerPrecedenteFenetre();
            }
        });
        panel.add(valider);

        panel.add(retour);
    }
}
