package IHM.Graphique.Ecrans;

import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.*;

public class EcranOptions extends Ecran {

    IHMGraphique ihhm;

    private JCheckBox volumeSlider;

    public EcranOptions() {
        super("Options");
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
        panel.add(retour);
    }
}
