package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.Ecrans.EcranOptions;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenu extends PopUp {

    JButtonIcon quitter;

    boolean optionsOuverte;

    public PopUpMenu(IHMGraphique ihm) {
        super(ihm, "Menu", 450, 450);
    }

    @Override
    public void init(IHMGraphique ihm) {
        this.backgroundImage = Images.chargerImage("/fondsEcrans/background_popup.png");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 20)));

        JButtonIcon retour = new JButtonIcon(Images.chargerImage("/boutons/retour.png"), 190, 80);
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
                ihm.getMoteurJeu().pauseGame(false);

                if (optionsOuverte) {
                    ihm.retournerPrecedenteFenetre();
                }
            }
        });
        add(retour);

        PopUp ref = this;

        JButtonIcon sauvegarder = new JButtonIcon(Images.chargerImage("/boutons/Sauvegarder.png"), 190, 80);
        sauvegarder.setAlignmentX(Component.CENTER_ALIGNMENT);
        sauvegarder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnabled(false);
                setVisible(false);
                PopUp p = new PopUpSauvegarder(ref);
                p.init(ihm);
                p.setVisible(true);
            }
        });
        add(sauvegarder);

        JButtonIcon options = new JButtonIcon(Images.chargerImage("/boutons/option.png"), 190, 80);
        options.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnabled(false);
                setVisible(false);
                if (!optionsOuverte) {
                    ihm.ouvrirFenetre(new EcranOptions(ref));
                }

                optionsOuverte = true;
            }
        });
        add(options);

        quitter = new JButtonIcon(Images.chargerImage("/boutons/quitter.png"), 190, 80);
        quitter.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PopUp p = new PopUpConfirmation(ref, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ihm.fermerFenetres();
                        close();
                        ref.close();
                        ihm.ouvrirFenetre(new EcranAccueil());
                    }
                });
                p.init(ihm);
                p.setVisible(true);
            }
        });
        add(quitter);
    }
}
