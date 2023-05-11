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
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new GridLayout(0, 1, 0, 30));

        retour.setText("Retour au jeu");
        panel.add(retour);

        JButton sauvegarder = new JButton("Sauvegarder");
        sauvegarder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpSauvegarder());
            }
        });
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

    @Override
    public void close(IHMGraphique ihm) {
        ihm.getMoteurJeu().pauseGame(false);
        System.out.println("Fermeture");
        super.close(ihm);
    }
}
