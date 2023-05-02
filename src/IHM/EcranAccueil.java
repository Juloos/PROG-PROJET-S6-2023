package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranAccueil extends Ecran {

    public EcranAccueil() {
        super("Accueil");
        panel.setLayout(new BorderLayout());
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);

        JLabel title = new JLabel("Hey that's my fish!");
        panel.add(title, BorderLayout.PAGE_START);

        JPanel centerMenu = new JPanel();
        centerMenu.setLayout(new BoxLayout(centerMenu, BoxLayout.PAGE_AXIS));

        JButton nouvellePartie = new JButton("Nouvelle partie");
        nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranCreationPartie());
            }
        });
        centerMenu.add(nouvellePartie);

        JButton chargerPartie = new JButton("Charger partie");
        chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranChargerPartie());
            }
        });
        centerMenu.add(chargerPartie);

        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new EcranOptions());
            }
        });
        centerMenu.add(options);

        JButton quitter = new JButton("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });
        centerMenu.add(quitter);

        panel.add(centerMenu, BorderLayout.CENTER);
    }
}
