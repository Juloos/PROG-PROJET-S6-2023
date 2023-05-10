package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;

public class EcranChargerPartie extends Ecran {

    protected JButton charger;

    public EcranChargerPartie() {
        super("Charger partie");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        charger = new JButton("charger");
        charger.addActionListener(actionEvent -> {
            ihm.getMoteurJeu().lancerPartie("sauvegarde.txt");
            ihm.ouvrirFenetre(new EcranJeu());
        });
        panel.add(charger, BorderLayout.PAGE_START);
        panel.add(retour, BorderLayout.PAGE_END);
    }
}
