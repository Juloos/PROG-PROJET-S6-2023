package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.IHMGraphique;
import Modele.Actions.ActionSauvegarder;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setLayout(new GridLayout(0, 1));

        // Preparation du panel de sauvegarde
        JPanel nomPanel = new JPanel();
        nomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomPanel.setLayout(new BoxLayout(nomPanel, BoxLayout.X_AXIS));
        JLabel nomLabel = new JLabel("Nom sauvegarde : ");
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomLabel.setFont(new Font("Impact", Font.PLAIN, 20));
        nomPanel.add(nomLabel);

        // Initialisation de la zone d'écriture pour le nom de sauvegarde
        JTextField nom = new JTextField("");
        nom.setMaximumSize(new Dimension(200, 40));
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
        listSauv.setFixedCellWidth(200);
        add(listSauv);

        // Mise a jour du texte dans la zone d'écriture de la sauvegarde
        listSauv.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                nom.setText(listNameFiles[listSauv.getSelectedIndex()]);
            }
        });

        // Creation de la sauvegarde
        Button valider = new Button("Sauvegarder");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionSauvegarder("sauvegarde/" + nom.getText()));
                JOptionPane.showMessageDialog(null, "Sauvegarde réussie", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
                close();
            }
        });
        add(valider);

        Button retour = new Button("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
                owner.setEnabled(true);
                owner.setVisible(true);
            }
        });
        add(retour);
    }
}
