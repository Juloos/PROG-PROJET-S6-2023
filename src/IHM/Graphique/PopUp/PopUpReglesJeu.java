package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpReglesJeu extends PopUp {

    private static final Image REGLES_PAGE1 = Images.chargerImage("/regles/regles_page1.png");
    private static final Image REGLES_PAGE2 = Images.chargerImage("/regles/regles_page2.png");

    private final IHMGraphique ihm;
    private int pageIndex;
    private Image page;

    public PopUpReglesJeu(IHMGraphique ihm) {
        super(ihm, "Règles du jeu", ihm.getFrame().getWidth(), ihm.getFrame().getHeight());
        this.ihm = ihm;
    }

    @Override
    public void init(IHMGraphique ihm) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(page, 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new BorderLayout());

        JPanel bouttons = new JPanel();
        bouttons.setLayout(new BoxLayout(bouttons, BoxLayout.X_AXIS));
        bouttons.setOpaque(false);

        JButton pagePrecedente = new JButtonIcon(Images.chargerImage("/icones/arrow_left.png"), 100);
        pagePrecedente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changerPage(true);
            }
        });

        JButton pageSuivante = new JButtonIcon(Images.chargerImage("/icones/arrow_right.png"), 100);
        pageSuivante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changerPage(false);
            }
        });

        bouttons.add(pagePrecedente);
        bouttons.add(Box.createHorizontalGlue());
        bouttons.add(pageSuivante);

        panel.add(Box.createVerticalGlue(), BorderLayout.CENTER);
        panel.add(bouttons, BorderLayout.SOUTH);

        setContentPane(panel);

        page = REGLES_PAGE1;
    }

    private void changerPage(boolean previous) {
        pageIndex = previous ? pageIndex - 1 : pageIndex + 1;

        if (pageIndex < 0) {
            pageIndex = 0;
        } else if (pageIndex > 1) {
            pageIndex = 1;
        }

        switch (pageIndex) {
            case 0:
                page = REGLES_PAGE1;
                break;
            case 1:
                page = REGLES_PAGE2;
                break;
        }
        repaint();
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            ihm.getMoteurJeu().pauseGame(true);
        } else {
            ihm.getMoteurJeu().pauseGame(false);
        }
        super.setVisible(b);
    }
}
