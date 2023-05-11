package IHM.Graphique.PopUp;

import IHM.Graphique.Ecrans.EcranAccueil;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpFinPartie extends PopUp {

    public PopUpFinPartie() {
        super("Partie terminée");
    }

    @Override
    public void creation(IHMGraphique ihm) {
        JLabel joueurGagnant = new JLabel();

        String text = "";
        for (int i : ihm.getMoteurJeu().getJeu().getWinner()) {
            text += ihm.getMoteurJeu().getJeu().getJoueur(i).getNom() + "\n";
        }
        joueurGagnant.setText(text);
        panel.add(joueurGagnant);

        JButton retourAccueil = new JButton("Retourner à l'accueil");
        retourAccueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ihm.fermerFenetres();
                ihm.ouvrirFenetre(new EcranAccueil());
            }
        });
        panel.add(retourAccueil);
    }
}
