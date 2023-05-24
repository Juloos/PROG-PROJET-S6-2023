package IHM.Graphique.PopUp;

import IHM.Graphique.Composants.Button;
import IHM.Graphique.Composants.JButtonIcon;
import IHM.Graphique.IHMGraphique;
import IHM.Graphique.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Le pop-up pour afficher les règles du jeu
 */
public class PopUpReglesJeu extends PopUp {

    private static final Image REGLES_PAGE1 = Images.chargerImage("/regles/regles_page1.png");
    private static final Image REGLES_PAGE2 = Images.chargerImage("/regles/regles_page2.png");

    private final IHMGraphique ihm;
    private int pageIndex;
    private Image page;
    private JButton pagePrecedente, pageSuivante, quitter;

    public PopUpReglesJeu(IHMGraphique ihm) {
        super(ihm, "Règles du jeu", ihm.getFrame().getWidth(), ihm.getFrame().getHeight());
        this.setLocation(ihm.getFrame().getX(), ihm.getFrame().getY());
        this.ihm = ihm;
    }

    /* Méthodes héritées */
    @Override
    public void init(IHMGraphique ihm) {

        //Initialisation du panel principale
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(page, 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new BorderLayout());

        // Initialisation du boutton quitter
        quitter = new Button("Retour");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
            }
        });

        // Initialisation du boutton pagePrécedente
        pagePrecedente = new JButtonIcon(Images.chargerImage("/icones/arrow_left.png"), 80);
        pagePrecedente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changerPage(true);
            }
        });

        // Initialisation du boutton pageSuivante
        pageSuivante = new JButtonIcon(Images.chargerImage("/icones/arrow_right.png"), 80);
        pageSuivante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changerPage(false);
            }
        });

        // Initialisation du panel bouttons
        JPanel bouttons = new JPanel();
        bouttons.setLayout(new BoxLayout(bouttons, BoxLayout.X_AXIS));
        bouttons.setOpaque(false);
        bouttons.add(pagePrecedente);
        bouttons.add(Box.createHorizontalGlue());
        bouttons.add(quitter);
        bouttons.add(Box.createHorizontalGlue());
        bouttons.add(pageSuivante);

        // Construction du panel principale
        panel.add(Box.createVerticalGlue(), BorderLayout.CENTER);
        panel.add(bouttons, BorderLayout.SOUTH);

        setContentPane(panel);

        page = REGLES_PAGE1;
        pagePrecedente.setVisible(false);
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
                pagePrecedente.setVisible(false);
                pageSuivante.setVisible(true);
                break;
            case 1:
                page = REGLES_PAGE2;
                pagePrecedente.setVisible(true);
                pageSuivante.setVisible(false);
                break;
        }
        repaint();
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            ihm.getMoteurJeu().pauseGame(true);
        }
        super.setVisible(b);
    }
}
