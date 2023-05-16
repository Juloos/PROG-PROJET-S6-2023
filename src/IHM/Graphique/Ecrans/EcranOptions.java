package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranOptions extends Ecran {

    IHMGraphique ihhm;

    private JCheckBox volumeSlider;

    private PopUp popUp;

    public EcranOptions() {
        super("Options");
        init();
    }

    public EcranOptions(PopUp popUp) {
        super("Options");
        this.popUp = popUp;
        init();
    }

    private void init() {
        volumeSlider = new JCheckBox("Activer le son", false);
        volumeSlider.setHorizontalTextPosition(SwingConstants.LEFT);
        //Centrer le texte
        volumeSlider.setHorizontalAlignment(SwingConstants.CENTER);
        volumeSlider.setFont(new Font("Forte", Font.PLAIN, 48));
        volumeSlider.setIconTextGap(20);
        volumeSlider.addActionListener(actionEvent -> {
            if (volumeSlider.isSelected()) {
                float volume = 0.5f;
                ihhm.setVolume(volume);
            } else {
                ihhm.setVolume(0);
            }
        });
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new GridLayout(2, 2));
        ImageIcon icon = new ImageIcon("res\\background2.png");
        this.backgroundImage = icon.getImage();
        ihhm = ihm;
        panel.add(volumeSlider);

        JButton retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (popUp != null) {
                    popUp.setEnabled(true);
                    popUp.setVisible(true);
                } else {
                    ihm.retournerPrecedenteFenetre();
                }
            }
        });
        panel.add(retour);
    }
}
