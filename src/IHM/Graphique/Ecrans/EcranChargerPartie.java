package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.IHMGraphique;
import Modele.Jeux.JeuConcret;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;

import static java.lang.Math.max;

public class EcranChargerPartie extends Ecran {

    protected JButton charger;

    protected JList listCharg;

    protected  String[] listNameFiles;

    public EcranChargerPartie() {
        super("Charger partie");
    }

    @Override
    public void creation(IHMGraphique ihm) {

        JButton suppr;

        JPanel panelPlateau = new JPanel();
        panelPlateau.setLayout(new BoxLayout(panelPlateau, BoxLayout.X_AXIS));
        panelPlateau.setPreferredSize(new Dimension(500,500));
        panel.add(panelPlateau);

        PlateauGraphique platGraph = new PlateauGraphique();
        platGraph.setPreferredSize(new Dimension(500,400));
        panelPlateau.add(platGraph);

        // Création du dossier de sauvegarde
        File dirSauv = new File("sauvegarde");
        if (!dirSauv. isDirectory()){
            dirSauv.mkdirs();
            System.out.println("Directory created successfully");
        }

        // Affichages des sauvegardes
        listNameFiles = dirSauv.list();
        listCharg = new JList(listNameFiles);
        panel.add(listCharg);

        // Preview du plateau de la sauvegarde selectionner
        listCharg.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int tempInd = listCharg.getSelectedIndex();
                if (tempInd >= 0 && tempInd<= listNameFiles.length){
                    platGraph.setJeu(JeuConcret.charger("sauvegarde/" + listNameFiles[tempInd]));
                    panelPlateau.repaint();
                    panelPlateau.revalidate();
                }
            }
        });

        // Gestion de la supression / du chargement de la sauvegarde selectionner
        charger = new JButton("charger");
        charger.addActionListener(actionEvent -> {
            ihm.getMoteurJeu().lancerPartie("sauvegarde/" + listNameFiles[listCharg.getSelectedIndex()]);
        });
        panel.add(charger, BorderLayout.PAGE_START);

        suppr = new JButton("Supprimer");
        suppr.addActionListener(actionEvent -> {
            int tempIndex = listCharg.getSelectedIndex();
            File f = new File("sauvegarde/" + listNameFiles[tempIndex]);
            f.delete();
            listNameFiles = dirSauv.list();
            listCharg.setListData(listNameFiles);
            listCharg.setSelectedIndex(max(0,tempIndex-1));
            listCharg.repaint();
            listCharg.revalidate();
        });
        panel.add(suppr, BorderLayout.PAGE_START);

        panel.add(retour, BorderLayout.PAGE_END);
    }
}
