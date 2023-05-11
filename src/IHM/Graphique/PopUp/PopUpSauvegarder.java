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
        // Initialisation de la zone d'écriture pour le nom de sauvegarde
        JButton valider = new JButton("Sauvegarder");
        JLabel nomLabel = new JLabel("Nom sauvegarde : ");
        nomLabel.setFont(new Font("Impact", Font.PLAIN, 20));
        JTextField nom = new JTextField("");
        nom.setMaximumSize(new Dimension(500, 40));
        panel.add(nomLabel);
        panel.add(nom);

        //Creation de la sauvegarde
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
