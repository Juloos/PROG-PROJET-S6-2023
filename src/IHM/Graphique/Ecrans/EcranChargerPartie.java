package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import Modele.Jeux.JeuConcret;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;

public class EcranChargerPartie extends Ecran {

    protected JButton charger;

    public EcranChargerPartie() {
        super("Charger partie");
    }

    @Override
    public void creation(IHMGraphique ihm) {

        JPanel panelPlateau = new JPanel();
        panel.add(panelPlateau);

        PlateauGraphique platGraph = new PlateauGraphique();

        // Création du dossier de sauvegarde
        File dirSauv = new File("sauvegarde");
        if (!dirSauv. isDirectory()){
            dirSauv.mkdirs();
            System.out.println("Directory created successfully");
        }

        // Affichages des sauvegardes
        String[] listNameFiles = dirSauv.list();
        System.out.println(listNameFiles);
        JList listCharg = new JList(listNameFiles);
        charger = new JButton("charger");


        // Gestion de la selection de la sauvegarde a charger
        charger.addActionListener(actionEvent -> {
            ihm.getMoteurJeu().lancerPartie("sauvegarde/" + listNameFiles[listCharg.getSelectedIndex()]);
        });
        panel.add(charger, BorderLayout.PAGE_START);
        panel.add(retour, BorderLayout.PAGE_END);
        panel.add(listCharg);
    }
}
