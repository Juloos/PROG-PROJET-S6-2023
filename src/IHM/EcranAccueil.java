package IHM;

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

        JLabel title = new JLabel("Hey that's my fish!");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        panel.add(title, constraints);

////        panel.setLayout(new GridBagLayout());
//        panel.setLayout(new BorderLayout());
//
//        GridBagConstraints constraints = new GridBagConstraints();
//
//
//
//        JButton nouvellePartie = new JButton("Nouvelle partie");
//        nouvellePartie.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                ihm.ouvrirFenetre(new EcranCreationPartie());
//            }
//        });
//        constraints.gridy = 1;
//        constraints.gridwidth = 2;
//        panel.add(nouvellePartie, BorderLayout.PAGE_END);
//
//        JButton chargerPartie = new JButton("Charger partie");
//        chargerPartie.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                ihm.ouvrirFenetre(new EcranChargerPartie());
//            }
//        });
//
//        JButton options = new JButton("Options");
//        options.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                ihm.ouvrirFenetre(new EcranOptions());
//            }
//        });
//
//        JButton quitter = new JButton("Quitter");
//        quitter.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                ihm.ouvrirFenetre(new PopUpConfirmation(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent actionEvent) {
//                        ihm.retournerPrecedenteFenetre();
//                        System.out.println("C'est confirmé!");
//                    }
//                }));
//            }
//        });
//
////        panel.add(centerMenu, BorderLayout.CENTER);
    }
}
