package IHM.Graphique.Ecrans;

import Global.Config;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranCreationPartie extends Ecran {

    int nbJoueurs;

    JButton ajouterJoueur;

    JPanel joueursPanel;

    public EcranCreationPartie() {
        super("Creation partie");
        nbJoueurs = Config.NB_JOUEUR;
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("res/fonds/background2.jpg");
        this.backgroundImage = icon.getImage();

//        JLabel label = new JLabel("Nouvelle Partie", SwingConstants.CENTER);
//        label.setFont(new Font("Impact", Font.PLAIN, 48));
//        label.setBorder(new EmptyBorder(20, 20, 20, 20));
//        panel.add(label, BorderLayout.PAGE_START);
//
//        joueursPanel = new JPanel(new GridLayout(0, Config.NB_MAX_JOUEUR));
//        joueursPanel.setAlignmentX(JScrollPane.CENTER_ALIGNMENT);
//
//        joueursPanel.setBackground(TRANSPARENT);
//        joueursPanel.setOpaque(true);
//        for (int i = 0; i < nbJoueurs; i++) {
//            joueursPanel.add(new MenuJoueur(i + 1), i);
//        }
//
//        panel.add(joueursPanel, BorderLayout.CENTER);
//
//        JPanel boutons = new JPanel();
//        SpringLayout layout = new SpringLayout();
//        boutons.setLayout(layout);
//        boutons.setBackground(Color.RED);
//        boutons.setSize(200, 200);
//
//        ajouterJoueur = new JButton("Ajouter un joueur");
//        ajouterJoueur.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                nouveauJoueur();
//            }
//        });
//        boutons.add(ajouterJoueur);
//
//        JButton lancerPartie = new JButton("Lancer partie");
//        boutons.add(lancerPartie);
//
//        layout.putConstraint(SpringLayout.SOUTH, ajouterJoueur, 20, SpringLayout.NORTH, lancerPartie);
//
//        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
//        boutons.add(retour);
//
//        panel.add(boutons, BorderLayout.PAGE_END);
    }

    protected void nouveauJoueur() {
        nbJoueurs++;
        if (nbJoueurs == Config.NB_MAX_JOUEUR) {
            ajouterJoueur.setVisible(false);
        }

        joueursPanel.add(new MenuJoueur(nbJoueurs), nbJoueurs - 1);
    }

    protected void supprimerJoueur(int num) {
        nbJoueurs--;
        if (nbJoueurs < Config.NB_MAX_JOUEUR) {
            ajouterJoueur.setVisible(true);
        }

        joueursPanel.remove(num);
        for (int i = Config.NB_JOUEUR; i < nbJoueurs; i++) {
            ((MenuJoueur) joueursPanel.getComponent(i)).updateNumJoueur(i + 1);
        }

        panel.repaint();
    }

    private class MenuJoueur extends JPanel {

        JLabel numJoueur;
        JComboBox<String> difficultesIA;
        JButton close;
        ActionListener closeAction;

        public MenuJoueur(int num) {
            super();

            setLayout(new GridLayout(0, 1));
            setBackground(TRANSPARENT);

            GridBagConstraints constraints = new GridBagConstraints();

            numJoueur = new JLabel("Joueur " + num, SwingConstants.CENTER);
            numJoueur.setFont(new Font("Impact", Font.PLAIN, 24));
            numJoueur.setAlignmentX(CENTER_ALIGNMENT);
            add(numJoueur);

            JPanel panelNom = new JPanel();
            panelNom.setAlignmentX(CENTER_ALIGNMENT);
            panelNom.setBackground(TRANSPARENT);
            JLabel nomLabel = new JLabel("Nom : ");
            JTextField nom = new JTextField("Joueur " + num);
            panelNom.add(nomLabel);
            panelNom.add(nom);

            add(panelNom);

            difficultesIA = new JComboBox<>();
            difficultesIA.addItem("Joueur");
            difficultesIA.addItem("Facile");
            difficultesIA.addItem("Moyenne");
            difficultesIA.addItem("Difficile");

            add(difficultesIA);

            if (num > Config.NB_JOUEUR) {
                close = new JButton("X");

                closeAction = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        supprimerJoueur(num - 1);
                    }
                };
                close.addActionListener(closeAction);
                add(close);
            }
        }

        private void updateNumJoueur(int num) {
            close.removeActionListener(closeAction);
            closeAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    supprimerJoueur(num - 1);
                }
            };
            close.addActionListener(closeAction);
        }
    }
}
