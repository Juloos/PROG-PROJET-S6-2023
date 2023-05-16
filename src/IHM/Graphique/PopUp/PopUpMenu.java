package IHM.Graphique.PopUp;

import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranOptions;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenu extends PopUp {

    Button quitter;

    boolean optionsOuverte, quitterConfirmationOuvert;

    public PopUpMenu(IHMGraphique ihm) {
        super(ihm, "Menu", 500, 500);
    }

    @Override
    public void init(IHMGraphique ihm) {
        setLayout(new GridLayout(0, 1, 0, 30));

        JButton retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
                ihm.getMoteurJeu().pauseGame(false);
                if (optionsOuverte) {
                    ihm.retournerPrecedenteFenetre();
                }
            }
        });
        retour.setText("Retour au jeu");
        add(retour);

        PopUp ref = this;

        Button sauvegarder = new Button("Sauvegarder");
        sauvegarder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnabled(false);
                setVisible(false);
                PopUp p = new PopUpSauvegarder(ref);
                p.init(ihm);
                p.setVisible(true);
            }
        });
        add(sauvegarder);

        Button options = new Button("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnabled(false);
                setVisible(false);
                if (!optionsOuverte) {
                    ihm.ouvrirFenetre(new EcranOptions(ref));
                }

                optionsOuverte = true;
            }
        });
        add(options);

        quitter = new Button("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PopUp p = new PopUpConfirmation(ref, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.fermerFenetres();
                        close();
                        ref.close();
                        ihm.ouvrirFenetre(new EcranAccueil());
                    }
                });
                p.init(ihm);
                p.setVisible(true);
            }
        });
        add(quitter);
    }
}
