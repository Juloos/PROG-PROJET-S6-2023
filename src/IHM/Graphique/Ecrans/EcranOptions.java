package IHM.Graphique.Ecrans;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.PopUp.PopUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
        volumeSlider.setFont(new Font("Impact", Font.PLAIN, 48));
        volumeSlider.setIconTextGap(30);
        volumeSlider.addActionListener(actionEvent -> {
            if (volumeSlider.isSelected()) {
                float volume = 0.5f;
                ihhm.setVolume(volume);
            } else {
                ihhm.setVolume(0);
            }
        });
        volumeSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeSlider.setOpaque(false);
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
    }

    @Override
    public void creation(IHMGraphique ihm) {
        panel.setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("res/fondsEcrans/background_options.jpg");
        this.backgroundImage = icon.getImage();
        ihhm = ihm;
        JPanel panelVolume = new JPanel();
        panelVolume.add(Box.createRigidArea(new Dimension(0, 100)));
        panelVolume.setLayout(new BoxLayout(panelVolume, BoxLayout.Y_AXIS));
        panelVolume.setPreferredSize(new Dimension(400,400));
        panelVolume.setOpaque(false);
        panelVolume.add(volumeSlider);
        panelVolume.add(Box.createRigidArea(new Dimension(0, 50)));
        panelVolume.add(Box.createRigidArea(new Dimension(0, 50)));
        Button rules = new Button("Règles");
        rules.setFont(new Font("Impact", Font.PLAIN, 48));
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().open(new java.io.File("res/rules.pdf"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelVolume.add(rules);
        panel.add(panelVolume, BorderLayout.CENTER);

        Button retour = new Button("Retour");
        retour.setFont(new Font("Impact", Font.PLAIN, 48));
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
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(retour, BorderLayout.SOUTH);
    }
}
