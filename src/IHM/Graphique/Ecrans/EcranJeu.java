package IHM.Graphique.Ecrans;

import IHM.Composants.InfoJoueur;
import IHM.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUpMenu;
import Modele.Actions.ActionAnnuler;
import Modele.Actions.ActionRefaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EcranJeu extends Ecran {

    List<InfoJoueur> joueurs;

    JPanel menu;

    JLabel message;
    JButton options, annuler, refaire;

    public EcranJeu() {
        super("Partie en cours");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new BorderLayout());

        joueurs = new ArrayList<>();

        menu = new JPanel(new GridLayout(0, 1));

        for (int i = 0; i < ihm.getMoteurJeu().getJeu().getNbJoueurs(); i++) {
            InfoJoueur joueur = new InfoJoueur(ihm.getMoteurJeu().getJeu().getJoueur(i));
            joueurs.add(joueur);
            menu.add(joueur);
        }

        message = new JLabel("", SwingConstants.CENTER);
        menu.add(message);

        JPanel annulerRefaire = new JPanel(new FlowLayout(FlowLayout.CENTER));

        annuler = new JButtonIcon(new ImageIcon("res/arrow_left.png"), 100);
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionAnnuler());
            }
        });

        refaire = new JButtonIcon(new ImageIcon("res/arrow_right.png"), 100);
        refaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.getMoteurJeu().appliquerAction(new ActionRefaire());
            }
        });

        annulerRefaire.add(annuler);
        annulerRefaire.add(refaire);
        menu.add(annulerRefaire);

        JPanel optionsPanel = new JPanel(new BorderLayout());

        options = new JButtonIcon(new ImageIcon("res/gear.png"), 70);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.ouvrirFenetre(new PopUpMenu());
            }
        });

        JPanel horizontal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontal.add(options);
        optionsPanel.add(horizontal, BorderLayout.SOUTH);
        menu.add(optionsPanel);


        panel.add(menu, BorderLayout.EAST);
        panel.add(ihm.getPlateauGraphique(), BorderLayout.CENTER);
    }

    @Override
    public void update(IHMGraphique ihm) {
        for (InfoJoueur joueur : joueurs) {
            joueur.update(joueur.getJoueurID() == ihm.getMoteurJeu().getJoueurActif().getID());
            panel.repaint();
        }
    }

    @Override
    public void afficherMessage(String message) {
        this.message.setText(message);
    }

    @Override
    public void resized() {
        final int menuWidth = panel.getWidth() * 2 / 7;
        menu.setPreferredSize(new Dimension(menuWidth, panel.getHeight()));
    }
}
