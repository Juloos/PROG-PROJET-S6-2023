package IHM.Graphique.PopUp;

import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranOptions;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenu extends PopUp {

    public PopUpMenu() {
        super("Menu");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new GridLayout(0, 1));

        retour.setText("Retour au jeu");
        panel.add(retour);

        JButton sauvegarder = new JButton("Sauvegarder");
        panel.add(sauvegarder);

        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });
        panel.add(options);

        JButton quitter = new JButton("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpConfirmation(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.fermerFenetres();
                        ihm.ouvrirFenetre(new EcranAccueil());
                    }
                }));
            }
        });
        panel.add(quitter);
    }
}
