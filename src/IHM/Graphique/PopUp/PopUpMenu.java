package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranOptions;
import IHM.Graphique.IHMGraphique;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenu extends PopUp {

    Button quitter;

    boolean optionsOuverte;

    public PopUpMenu(IHMGraphique ihm) {
        super(ihm, "Menu", 500, 500);
    }

    @Override
    public void init(IHMGraphique ihm) {
        setLayout(new GridLayout(0, 1, 0, 30));

        Button retour = new Button("Retour au jeu");
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
