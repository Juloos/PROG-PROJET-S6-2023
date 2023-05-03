package IHM.Graphique.Ecrans;

import Global.Config;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
        panel.setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("src\\background2.jpg");
        this.backgroundImage = icon.getImage();

        JLabel label = new JLabel("Nouvelle Partie", SwingConstants.CENTER);
        label.setFont(new Font("Impact", Font.PLAIN, 48));
        label.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.PAGE_START);

        joueursPanel = new JPanel(new GridLayout(0, Config.NB_MAX_JOUEUR));
        joueursPanel.setOpaque(true);
        for (int i = 0; i < nbJoueurs; i++) {
            joueursPanel.add(new MenuJoueur(i + 1), i);
        }

        panel.add(joueursPanel, BorderLayout.CENTER);

        JPanel boutons = new JPanel();

        ajouterJoueur = new JButton("Ajouter un joueur");
        ajouterJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                nouveauJoueur();
            }
        });
        boutons.add(ajouterJoueur);

        JButton lancerPartie = new JButton("Lancer partie");
        boutons.add(lancerPartie);

        boutons.add(retour);

        panel.add(boutons, BorderLayout.PAGE_END);
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
        JCheckBox ia;
        JComboBox<String> difficultesIA;
        JButton close;
        ActionListener closeAction;

        public MenuJoueur(int num) {
            super();

            setLayout(new GridLayout(0, 1));

            numJoueur = new JLabel("Joueur " + num, SwingConstants.CENTER);
            numJoueur.setFont(new Font("Impact", Font.PLAIN, 24));
            add(numJoueur);

            ia = new JCheckBox("IA");
            add(ia);
            ia.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent itemEvent) {
                    onIACheck(itemEvent.getStateChange() == 1);
                }
            });

            JPanel panelNom = new JPanel();
            JLabel nomLabel = new JLabel("Nom : ");
            JTextField nom = new JTextField("Joueur " + num);
            panelNom.add(nomLabel);
            panelNom.add(nom);

            add(panelNom);

            difficultesIA = new JComboBox<>();
            difficultesIA.addItem("Facile");
            difficultesIA.addItem("Moyenne");
            difficultesIA.addItem("Difficile");
            difficultesIA.setVisible(false);

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

        private void onIACheck(boolean checked) {
            difficultesIA.setVisible(checked);
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
