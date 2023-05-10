package IHM.Graphique.Ecrans;

import Global.Config;
import IHM.Colors;
import IHM.Graphique.IHMGraphique;
import Modele.IA.IA;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranCreationPartie extends Ecran {

    int nbJoueurs;

    Button ajouterJoueur;

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
        ImageIcon icon = new ImageIcon("res/background2.jpg");
        this.backgroundImage = icon.getImage();

        JLabel label = new JLabel("Nouvelle Partie", SwingConstants.CENTER);
        label.setFont(new Font("Impact", Font.PLAIN, 48));
        panel.add(label, BorderLayout.PAGE_START);

        joueursPanel = new JPanel(new GridLayout(0, Config.NB_MAX_JOUEUR));
        joueursPanel.setAlignmentX(JScrollPane.CENTER_ALIGNMENT);

        joueursPanel.setBackground(Colors.TRANSPARENT);
        for (int i = 0; i < nbJoueurs; i++) {
            joueursPanel.add(new MenuJoueur(i + 1), i);
        }

        panel.add(joueursPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(Colors.TRANSPARENT);
        ajouterJoueur = new Button("Ajouter un joueur");
        ajouterJoueur.setPreferredSize(new Dimension(200, 50));
        ajouterJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                nouveauJoueur();
            }
        });
        buttons.add(ajouterJoueur);

        Button lancerPartie = new Button("Lancer partie");
        lancerPartie.setPreferredSize(new Dimension(200, 50));
        buttons.add(lancerPartie);
        lancerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Joueur joueurs[] = new Joueur[nbJoueurs];
                for (int i = 0; i < nbJoueurs; i++) {
                    //si le joueur est une IA
                    if (((MenuJoueur) joueursPanel.getComponent(i)).difficultesIA.getSelectedIndex() != 0) {
                        IA.Difficulte diff = IA.Difficulte.values()[((MenuJoueur) joueursPanel.getComponent(i)).difficultesIA.getSelectedIndex()];
                        System.out.println(diff);
                        joueurs[i] = new JoueurIA(((MenuJoueur) joueursPanel.getComponent(i)).num - 1,diff);
                    } else {
                        joueurs[i] = new JoueurHumain(((MenuJoueur) joueursPanel.getComponent(i)).num - 1);
                    }
                }
                ihm.getMoteurJeu().lancerPartie(joueurs);
//                ihm.getMoteurJeu().lancerPartie(new Joueur[]{new JoueurIA(0), new JoueurIA(1)});
//                ihm.getMoteurJeu().lancerPartie(new Joueur[]{new JoueurHumain(0), new JoueurIA(1), new JoueurIA(2)});
                System.out.println("Fin lancement partie");
                ihm.ouvrirFenetre(new EcranJeu());
            }
        });
        buttons.add(retour);

        panel.add(buttons, BorderLayout.PAGE_END);
    }

    protected void nouveauJoueur() {
        nbJoueurs++;
        if (nbJoueurs >= Config.NB_MAX_JOUEUR) {
            ajouterJoueur.setVisible(false);
        }

        joueursPanel.add(new MenuJoueur(nbJoueurs), nbJoueurs - 1);
        panel.repaint();
        panel.revalidate();
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

    @Override
    public void close(IHMGraphique ihm) {
        super.close(ihm);
        System.out.println("Fermeture fenetre");
    }

    private class MenuJoueur extends JPanel {

        JLabel numJoueur;
        int num;
        JComboBox<String> difficultesIA;
        Button close;
        ActionListener closeAction;

        public MenuJoueur(int num) {
            super();
            this.num = num;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Colors.TRANSPARENT);

            numJoueur = new JLabel("Joueur " + num, SwingConstants.CENTER);
            if (num == 1) {
                numJoueur.setForeground(Color.RED);
            }
            else if (num == 2) {
                numJoueur.setForeground(Color.BLUE);
            }
            else if (num == 3) {
                numJoueur.setForeground(Color.GREEN);
            }
            else if (num == 4) {
                numJoueur.setForeground(Color.YELLOW);
            }
            else {
                numJoueur.setForeground(Color.BLACK);
            }
            numJoueur.setFont(new Font("Impact", Font.PLAIN, 50));
            numJoueur.setAlignmentX(CENTER_ALIGNMENT);
            add(numJoueur);

            JPanel panelnom = new JPanel();
            panelnom.setBackground(Colors.TRANSPARENT);
            panelnom.setLayout(new BoxLayout(panelnom, BoxLayout.X_AXIS));
            JLabel nomLabel = new JLabel("Nom : ");
            nomLabel.setFont(new Font("Impact", Font.PLAIN, 20));
            JTextField nom = new JTextField("Joueur " + num);
            nom.setMaximumSize(new Dimension(200, 40));
            panelnom.add(nomLabel);
            panelnom.add(nom);
            add(panelnom);

            JPanel panelia = new JPanel();
            panelia.setLayout(new BoxLayout(panelia, BoxLayout.Y_AXIS));
            difficultesIA = new JComboBox<>();
            // Ajouter l'écouteur à la JComboBox
            difficultesIA.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                    // Ne rien faire
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {
                    panel.repaint(); // Redessiner le panneau
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {
                    // Ne rien faire
                }
            });
            difficultesIA.setOpaque(false);
            difficultesIA.setAlignmentX(CENTER_ALIGNMENT);
            difficultesIA.addItem("Joueur");
            difficultesIA.addItem("Facile");
            difficultesIA.addItem("Moyenne");
            difficultesIA.addItem("Difficile");
            difficultesIA.setSelectedIndex(0);
            difficultesIA.setMaximumSize(new Dimension(300, 50));
            //Personnalisation de l'affichage de la JComboBox
            DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setForeground(Color.BLACK); //couleur du texte
                    setBackground(Colors.TRANSPARENT); //couleur de fond
                    setHorizontalAlignment(DefaultListCellRenderer.CENTER);
                    return this;
                }
            };
            difficultesIA.setRenderer(renderer);
            difficultesIA.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    int selected = difficultesIA.getSelectedIndex();
                    if (selected != 0) {
                        ImageIcon image = new ImageIcon("res/ia.png");
                        image.setDescription(" " + difficultesIA.getSelectedItem());

// création du label contenant l'image
                        JLabel label = new JLabel(image);
                        label.setAlignmentX(CENTER_ALIGNMENT);

// création du panel contenant le label
                        JPanel iaPanel = new JPanel();
                        iaPanel.setOpaque(false);
                        iaPanel.setLayout(new BorderLayout());
                        iaPanel.add(Box.createHorizontalStrut(10), BorderLayout.WEST); // ajout d'un espace avant l'image
                        iaPanel.add(label, BorderLayout.CENTER);

// ajout du panel contenant l'image au panel principal
                        panelia.add(iaPanel);
                        panelia.setOpaque(false);

// rafraîchissement du panel
                        panelia.repaint();
                        panelia.revalidate();

                    } else {
                        panelia.remove(1);
                        panelia.repaint();
                        panelia.revalidate();
                    }
                }
            });

            panelia.add(difficultesIA);
            add(panelia);

            if (num > Config.NB_JOUEUR) {
                close = new Button("X");
                close.setSize(40, 40);
                closeAction = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        supprimerJoueur(num - 1);
                    }
                };
                close.addActionListener(closeAction);
                close.setAlignmentX(CENTER_ALIGNMENT);
                add(close);
            }
        }

        private void updateNumJoueur(int num) {
            this.num = num;
            close.removeActionListener(closeAction);
            closeAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    supprimerJoueur(num - 1);
                }
            };
            close.addActionListener(closeAction);
        }

        public Joueur getJoueur() {
            if (difficultesIA.getSelectedIndex() == 0) {
                return new JoueurHumain(num);
            } else {
                return new JoueurIA(num);
            }
        }
    }
}
