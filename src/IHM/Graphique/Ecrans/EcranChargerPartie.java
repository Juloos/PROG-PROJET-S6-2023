package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Couleurs;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Composants.Button;
import Modele.Jeux.JeuConcret;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;

import static java.lang.Math.max;

public class EcranChargerPartie extends Ecran {

    protected Button charger;

    protected JList listCharg;

    protected  String[] listNameFiles;

    public EcranChargerPartie() {
        super("Charger partie");
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("res/fondsEcrans/background_chargement.png");
        this.backgroundImage = icon.getImage();

        JLabel titre = new JLabel("Charger une partie", SwingConstants.CENTER);
        titre.setFont(new Font("Impact", Font.PLAIN, 50));
        titre.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        panel.add(titre, BorderLayout.NORTH);


        JPanel panelPlateau = new JPanel();
        panelPlateau.setLayout(new BoxLayout(panelPlateau, BoxLayout.Y_AXIS));
        panelPlateau.setPreferredSize(new Dimension(400,400));
        PlateauGraphique platGraph = new PlateauGraphique();


        // Création du dossier de sauvegarde
        File dirSauv = new File("sauvegarde");
        if (!dirSauv. isDirectory()){
            dirSauv.mkdirs();
            System.out.println("Directory created successfully");
        }

        // Affichages des sauvegardes
        listNameFiles = dirSauv.list();
        System.out.println(listNameFiles);
        listCharg = new JList(listNameFiles);
        listCharg.setFixedCellWidth(200);
        listCharg.setFixedCellHeight(50);
        listCharg.setBackground(Couleurs.TRANSPARENT);
        listCharg.setFont(new Font("Impact", Font.PLAIN, 20));
        listCharg.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Gestion de la selection de la sauvegarde a charger

        // Preview du plateau de la sauvegarde selectionner
        listCharg.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int tempInd = listCharg.getSelectedIndex();
                int nbJoueurs;
                if (tempInd >= 0 && tempInd<= listNameFiles.length){
                    platGraph.setJeu(JeuConcret.charger("sauvegarde/" + listNameFiles[tempInd]));
                    platGraph.setMaximumSize(new Dimension(600,600));
                    nbJoueurs = JeuConcret.charger("sauvegarde/" + listNameFiles[listCharg.getSelectedIndex()]).getJoueurs().length;
                    panelPlateau.add(platGraph);
                    panelPlateau.remove(0);
                    JLabel nbJoueursLabel = new JLabel("Nombre de joueurs : " + nbJoueurs);
                    nbJoueursLabel.setFont(new Font("Impact", Font.PLAIN, 30));
                    nbJoueursLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    panelPlateau.add(nbJoueursLabel);
                    panelPlateau.repaint();
                    panelPlateau.revalidate();
                }
            }
        });
        JPanel panelPreview = new JPanel();
        panelPreview.setLayout(new BoxLayout(panelPreview, BoxLayout.Y_AXIS));
        JLabel preview = new JLabel("Sauvegardes :");
        preview.setFont(new Font("Impact", Font.PLAIN, 30));
        panelPreview.add(preview);
        panelPreview.add(listCharg);
        panelPreview.setOpaque(false);
        panel.add(panelPreview, BorderLayout.WEST);
        panelPlateau.setOpaque(false);
        panel.add(panelPlateau, BorderLayout.CENTER);

        // Gestion de la supression / du chargement de la sauvegarde selectionner
        charger = new Button("charger");
        charger.addActionListener(actionEvent -> {
            ihm.getMoteurJeu().lancerPartie("sauvegarde/" + listNameFiles[listCharg.getSelectedIndex()]);
        });
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new FlowLayout());
        Button suppr = new Button("Supprimer");

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
        charger.setPreferredSize(new Dimension(200, 50));
        suppr.setPreferredSize(new Dimension(200, 50));
        retour.setPreferredSize(new Dimension(200, 50));
        buttons.add(charger);
        buttons.add(suppr);
        buttons.add(retour);
        panel.add(buttons, BorderLayout.SOUTH);
        panel.repaint();
    }
}
