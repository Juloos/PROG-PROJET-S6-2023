package IHM.Graphique.Ecrans;

import Global.Config;
import IHM.Graphique.Composants.Button;
import IHM.Graphique.Couleurs;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images;
import Modele.IA.IA;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe du menu pour créer une nouvelle partie
 */
public class EcranCreationPartie extends Ecran {

    int nbJoueurs;
    Button ajouterJoueur;
    JPanel joueursPanel;

    public EcranCreationPartie() {
        super("Creation partie");
        nbJoueurs = Config.NB_JOUEUR;
    }

    /* Méthodes héritées */
    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new BorderLayout());
        this.backgroundImage = Images.chargerImage("/fondsEcrans/background2.jpg");

        JLabel label = new JLabel("Nouvelle Partie", SwingConstants.CENTER);
        label.setFont(new Font("Impact", Font.PLAIN, 48));
        panel.add(label, BorderLayout.PAGE_START);

        joueursPanel = new JPanel(new GridLayout(0, Config.NB_MAX_JOUEUR));
        joueursPanel.setOpaque(false);
        joueursPanel.setAlignmentX(JScrollPane.CENTER_ALIGNMENT);

        joueursPanel.setBackground(Couleurs.TRANSPARENT);
        for (int i = 0; i < nbJoueurs; i++) {
            joueursPanel.add(new MenuJoueur(i + 1), i);
        }

        panel.add(joueursPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(Couleurs.TRANSPARENT);
        ajouterJoueur = new IHM.Graphique.Composants.Button("Ajouter un joueur");
        ajouterJoueur.setPreferredSize(new Dimension(200, 50));
        ajouterJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                nouveauJoueur();
            }
        });
        buttons.add(ajouterJoueur);

        IHM.Graphique.Composants.Button lancerPartie = new IHM.Graphique.Composants.Button("Lancer partie");
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
                        System.out.println(((MenuJoueur) joueursPanel.getComponent(i)).difficultesIA.getSelectedIndex());
                        System.out.println(diff);
                        joueurs[i] = new JoueurIA(((MenuJoueur) joueursPanel.getComponent(i)).num - 1, diff);
//                        joueurs[i].setNom("IA " + diff);
                    } else {
                        joueurs[i] = new JoueurHumain(((MenuJoueur) joueursPanel.getComponent(i)).num - 1);
                        joueurs[i].setNom(((MenuJoueur) joueursPanel.getComponent(i)).nom.getText());
                    }
                }
                ihm.getMoteurJeu().lancerPartie(joueurs);
            }
        });
        retour.setPreferredSize(new Dimension(200, 50));
        buttons.add(retour);
        buttons.setOpaque(false);
        panel.add(buttons, BorderLayout.PAGE_END);
    }

    /* Méthodes d'instances */
    protected void nouveauJoueur() {
        nbJoueurs++;
        if (nbJoueurs >= Config.NB_MAX_JOUEUR) {
            ajouterJoueur.setVisible(false);
        }
        joueursPanel.add(new MenuJoueur(nbJoueurs), nbJoueurs - 1);
        panel.repaint();
        panel.revalidate();
    }

    protected void supprimerJoueur() {
        nbJoueurs--;
        if (nbJoueurs < Config.NB_MAX_JOUEUR) {
            ajouterJoueur.setVisible(true);
        }
        joueursPanel.remove(nbJoueurs);
        for (int i = Config.NB_JOUEUR; i < nbJoueurs; i++) {
            ((MenuJoueur) joueursPanel.getComponent(i)).updateNumJoueur(i + 1);
        }

        panel.repaint();
    }

    private class MenuJoueur extends JPanel {

        JLabel numJoueur;
        int num;
        JTextField nom;
        JComboBox<String> difficultesIA;
        IHM.Graphique.Composants.Button close;
        ActionListener closeAction;

        public MenuJoueur(int num) {
            super();
            this.num = num;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);
            Border border = BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK);
            setBorder(border);

            numJoueur = new JLabel("Joueur " + num, SwingConstants.CENTER);
            if (num == 1) {
                numJoueur.setForeground(Color.RED);
            } else if (num == 2) {
                numJoueur.setForeground(Color.BLUE);
            } else if (num == 3) {
                numJoueur.setForeground(Color.GREEN);
            } else if (num == 4) {
                numJoueur.setForeground(Color.YELLOW);
            } else {
                numJoueur.setForeground(Color.BLACK);
            }
            numJoueur.setFont(new Font("Impact", Font.PLAIN, 50));
            numJoueur.setAlignmentX(CENTER_ALIGNMENT);
            add(numJoueur);

            JPanel panelnom = new JPanel();
            panelnom.setBackground(Couleurs.TRANSPARENT);
            panelnom.setLayout(new BoxLayout(panelnom, BoxLayout.X_AXIS));
            JLabel nomLabel = new JLabel("Nom : ");
            nomLabel.setFont(new Font("Impact", Font.PLAIN, 20));
            nom = new JTextField("Joueur " + num);
            nom.setFont(new Font("Impact", Font.PLAIN, 20));
            nom.setBackground(Couleurs.BLEU_CIEL_CLAIR);
            nom.setMaximumSize(new Dimension(200, 40));
            panelnom.add(nomLabel);
            panelnom.add(nom);


            JPanel panelia = new JPanel();
            panelia.setLayout(new BoxLayout(panelia, BoxLayout.Y_AXIS));
            difficultesIA = new JComboBox<>();
            // Ajouter l'écouteur à la JComboBox
            difficultesIA.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                    // Ne rien faire
                    panel.repaint();
                    panel.revalidate();
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {
                    panel.repaint();
                    panel.revalidate();
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
            difficultesIA.addItem("Légendaire");
            difficultesIA.setSelectedIndex(0);
            difficultesIA.setMaximumSize(new Dimension(300, 50));
            //Personnalisation de l'affichage de la JComboBox
            DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setForeground(Color.BLACK); //couleur du texte
                    setBackground(Couleurs.TRANSPARENT); //couleur de fond
                    setHorizontalAlignment(DefaultListCellRenderer.CENTER);
                    return this;
                }
            };
            difficultesIA.setRenderer(renderer);
            difficultesIA.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    int selected = difficultesIA.getSelectedIndex();
                    if (selected != 0 && selected!= 4) {
                        panelia.remove(1);
                        ImageIcon image = null;
                        if (num == 1) {
                            image = new ImageIcon(Images.chargerImage("/ia/ia_rouge.png"));
                        } else if (num == 2) {
                            image = new ImageIcon(Images.chargerImage("/ia/ia_bleu.png"));
                        } else if (num == 3) {
                            image = new ImageIcon(Images.chargerImage("/ia/ia_verte.png"));
                        } else if (num == 4) {
                            image = new ImageIcon(Images.chargerImage("/ia/ia_jaune.png"));
                        }
                        image.setImage(image.getImage().getScaledInstance(250, 450, Image.SCALE_DEFAULT));
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

                } else if (selected == 0) {
                    panelia.remove(1);
                    ImageIcon image = null;
                    switch (num) {
                        case 1 :
                            image = new ImageIcon(Images.chargerImage("/pingouins/PingouinRouge.png"));
                            break;
                        case 2 :
                            image = new ImageIcon(Images.chargerImage("/pingouins/PingouinBleu.png"));
                            break;
                        case 3 :
                            image = new ImageIcon(Images.chargerImage("/pingouins/PingouinVert.png"));
                            break;
                        case 4 :
                            image = new ImageIcon(Images.chargerImage("/pingouins/PingouinJaune.png"));
                            break;
                    }
                    image.setImage(image.getImage().getScaledInstance(650, 650, Image.SCALE_DEFAULT));
                    JLabel img = new JLabel(image);
                    img.setAlignmentX(CENTER_ALIGNMENT);
                    JPanel jPanel = new JPanel();
                    jPanel.setOpaque(false);
                    jPanel.setLayout(new BorderLayout());
                    jPanel.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
                    jPanel.add(img, BorderLayout.CENTER);
                    panelia.add(jPanel);
                    panelia.repaint();
                    panelia.revalidate();
                }
                else {
                    panelia.remove(1);
                    ImageIcon image = null;
                    switch (num) {
                        case 1 :
                            image = new ImageIcon(Images.chargerImage("/ia/legendaire_rouge.png"));
                            break;
                        case 2 :
                            image = new ImageIcon(Images.chargerImage("/ia/legendaire_bleu.png"));
                            break;
                        case 3 :
                            image = new ImageIcon(Images.chargerImage("/ia/legendaire_vert.png"));
                            break;
                        case 4 :
                            image = new ImageIcon(Images.chargerImage("/ia/legendaire_jaune.png"));
                            break;
                    }
                    image.setImage(image.getImage().getScaledInstance(350, 480, Image.SCALE_DEFAULT));
                    JLabel img = new JLabel(image);
                    img.setAlignmentX(CENTER_ALIGNMENT);
                    JPanel jPanel = new JPanel();
                    jPanel.setOpaque(false);
                    jPanel.setLayout(new BorderLayout());
                    jPanel.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
                    jPanel.add(img, BorderLayout.CENTER);
                    panelia.add(jPanel);
                    panelia.repaint();
                    panelia.revalidate();
                }
            }});
            panelia.add(difficultesIA);
            add(panelnom);
            ImageIcon image = null;
            switch (num) {
                case 1 :
                    image = new ImageIcon(Images.chargerImage("/pingouins/PingouinRouge.png"));
                    break;
                case 2 :
                    image = new ImageIcon(Images.chargerImage("/pingouins/PingouinBleu.png"));
                    break;
                case 3 :
                    image = new ImageIcon(Images.chargerImage("/pingouins/PingouinVert.png"));
                    break;
                case 4 :
                    image = new ImageIcon(Images.chargerImage("/pingouins/PingouinJaune.png"));
                    break;
            }
            image.setImage(image.getImage().getScaledInstance(650, 650, Image.SCALE_DEFAULT));
            JLabel img = new JLabel(image);
            img.setAlignmentX(CENTER_ALIGNMENT);
            JPanel jPanel = new JPanel();
            jPanel.setOpaque(false);
            jPanel.setLayout(new BorderLayout());
            jPanel.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
            jPanel.add(img, BorderLayout.CENTER);
            panelia.setOpaque(false);
            panelia.add(jPanel);
            add(panelia);

            if (num > Config.NB_JOUEUR) {
                close = new Button("X");
                close.setSize(40, 40);
                closeAction = actionEvent -> supprimerJoueur();
                close.addActionListener(closeAction);
                close.setAlignmentX(CENTER_ALIGNMENT);
                add(close);
            }
        }

        private void updateNumJoueur(int num) {
            this.num = num;
            close.removeActionListener(closeAction);
            closeAction = actionEvent -> supprimerJoueur();
            close.addActionListener(closeAction);
        }
    }
}
