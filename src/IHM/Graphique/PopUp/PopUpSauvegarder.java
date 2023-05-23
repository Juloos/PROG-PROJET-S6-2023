package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images;
import Modele.Actions.ActionSauvegarder;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Le pop-up pour sauvegarder une partie
 */
public class PopUpSauvegarder extends PopUp {

    private final PopUp owner;

    public PopUpSauvegarder(PopUp owner) {
        super(owner, "Sauvegarder", 700, 500);
        this.owner = owner;
    }

    /* Méthodes héritées */
    @Override
    public void init(IHMGraphique ihm) {
        this.backgroundImage = Images.chargerImage("/fondsEcrans/background_chargement.jpg");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Preparation du panel de sauvegarde
        JPanel nomPanel = new JPanel();
        nomPanel.setOpaque(false);
        nomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomPanel.setLayout(new BoxLayout(nomPanel, BoxLayout.X_AXIS));
        JLabel nomLabel = new JLabel("Nom sauvegarde : ");
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomLabel.setFont(new Font("Impact", Font.PLAIN, 20));
        nomLabel.setForeground(Color.WHITE);
        nomPanel.add(nomLabel);

        // Initialisation de la zone d'écriture pour le nom de sauvegarde
        JTextField nom = new JTextField("");
        nom.setMaximumSize(new Dimension(200, 40));
        nom.setFont(new Font("Impact", Font.PLAIN, 20));
        nom.setForeground(Color.WHITE);
        nom.setOpaque(false);
        nomPanel.add(nom);
        add(nomPanel);

        // Création du dossier de sauvegarde
        File dirSauv = new File("sauvegarde");
        if (!dirSauv.isDirectory()) {
            dirSauv.mkdirs();
            System.out.println("Directory created successfully");
        }

        // Affichages des sauvegardes
        String[] listNameFiles = dirSauv.list();
        System.out.println(listNameFiles);
        JList listSauv = new JList(listNameFiles);
        listSauv.setAlignmentX(Component.CENTER_ALIGNMENT);
        listSauv.setFont(new Font("Impact", Font.PLAIN, 20));
        listSauv.setOpaque(false);
        listSauv.setFixedCellWidth(300);
        listSauv.setFixedCellHeight(40);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(listSauv);

        // Mise a jour du texte dans la zone d'écriture de la sauvegarde
        listSauv.addListSelectionListener(e -> nom.setText(listNameFiles[listSauv.getSelectedIndex()]));

        // Creation de la sauvegarde
        Button valider = new Button("Sauvegarder");
        valider.addActionListener(actionEvent -> {
            ihm.getMoteurJeu().appliquerAction(new ActionSauvegarder("sauvegarde/" + nom.getText()));
            JOptionPane.showMessageDialog(null, "Sauvegarde réussie", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
            close();
        });

        Button retour = new Button("Retour");
        retour.addActionListener(actionEvent -> {
            close();
            owner.setEnabled(true);
            owner.setVisible(true);
        });
        JPanel panelBouton = new JPanel();
        panelBouton.setOpaque(false);
        panelBouton.setLayout(new BoxLayout(panelBouton, BoxLayout.X_AXIS));
        valider.setPreferredSize((new Dimension(200, 50)));
        retour.setPreferredSize((new Dimension(200, 50)));
        panelBouton.add(valider);
        panelBouton.add(retour);
        add(panelBouton, BorderLayout.SOUTH);
    }
}
