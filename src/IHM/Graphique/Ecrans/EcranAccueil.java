package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUpConfirmation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranAccueil extends Ecran {
    public EcranAccueil() {
        super("Accueil");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel buttons = new JPanel();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 4;

        panel.setLayout(new GridBagLayout());
        panel.setLayout(new BorderLayout());

        JButton nouvellePartie = new JButton("Nouvelle partie");
        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranCreationPartie());
            }
        });

        buttons.add(nouvellePartie, BorderLayout.CENTER);

        JButton chargerPartie = new JButton("Charger partie");
        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranChargerPartie());
            }
        });
        buttons.add(chargerPartie, BorderLayout.CENTER);

        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });
        buttons.add(options, BorderLayout.CENTER);
        JButton quitter = new JButton("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpConfirmation(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.retournerPrecedenteFenetre();
                        System.exit(0);
                    }
                }));
            }
        });
        buttons.add(quitter, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.CENTER);
    }
}
