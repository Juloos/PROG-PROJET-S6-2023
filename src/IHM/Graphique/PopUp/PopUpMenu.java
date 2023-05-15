package IHM.Graphique.PopUp;

import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranOptions;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Composants.Button;

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
        System.out.println("Création du pop de menu");
        panel.setLayout(new GridLayout(0, 1, 0, 30));

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().pauseGame(false);
            }
        });
        panel.add(retour);

        Button sauvegarder = new Button("Sauvegarder");
        sauvegarder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpSauvegarder());
            }
        });
        panel.add(sauvegarder);

        Button options = new Button("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });
        panel.add(options);

        Button quitter = new Button("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpConfirmation(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.getMoteurJeu().arreterPartie();
                        ihm.fermerFenetres();
                        ihm.ouvrirFenetre(new EcranAccueil());
                        ihm.updateAffichage();
                    }
                }));
            }
        });
        panel.add(quitter);
        System.out.println("Fin création");
    }

    @Override
    public void close(IHMGraphique ihm) {
        super.close(ihm);
    }
}
