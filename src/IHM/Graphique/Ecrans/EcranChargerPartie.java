package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EcranChargerPartie extends Ecran {

    protected JButton charger;

    public EcranChargerPartie() {
        super("Charger partie");
    }

    @Override
    public void creation(IHMGraphique ihm) {
        File dirSauv = new File("sauvegarde");
        String[] listNameFiles = dirSauv.list();
//        System.out.println(listNameFiles);
        JList listCharg = new JList(listNameFiles);
        charger = new JButton("charger");
//        charger.addActionListener(actionEvent -> {
//            ihm.getMoteurJeu().lancerPartie("sauvegarde/" + listNameFiles[listCharg.getSelectedIndex()]);
//        });
        panel.add(charger, BorderLayout.PAGE_START);
        panel.add(retour, BorderLayout.PAGE_END);
//        panel.add(listCharg);
    }
}
