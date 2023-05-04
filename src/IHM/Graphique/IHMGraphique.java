package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Ecrans.EcranJeu;
import IHM.IHM;
import Modele.Jeu;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

public class IHMGraphique extends IHM implements MouseListener {

    Stack<Fenetre> fenetres;
    JFrame frame;
    boolean enJeu;

    public IHMGraphique(MoteurJeu moteurJeu) {
        super(moteurJeu);

        fenetres = new Stack<>();

        frame = new JFrame("");

        ouvrirFenetre(new EcranJeu());

        frame.addMouseListener(this);
        frame.setSize(1500, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void updateAffichage(Jeu jeu) {
        if (enJeu) {
            ((EcranJeu) fenetres.peek()).updatePlateau(jeu.getPlateau());
        }
    }

    @Override
    public void attendreActionJoueur() {

    }

    @Override
    public void afficherMessage(String message) {

    }

    public MoteurJeu getMoteurJeu() {
        return moteurJeu;
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Détruit toutes les fenêtres de l'IHM et retourne au menu d'accueil
     */
    public void fermerFenetres() {

    }

    /**
     * Retourne à la fenêtre précédente et détruit la fenêtre actuelle
     */
    public void retournerPrecedenteFenetre() {
        fenetres.peek().close(this);
        System.out.println("Fermeture de la fenetre : " + fenetres.peek().title);
        fenetres.pop();
        fenetres.peek().open(this);
        System.out.println("Ouverture de la fenetre : " + fenetres.peek().title);
        frame.revalidate();

        enJeu = fenetres.peek() instanceof EcranJeu;
    }

    /**
     * Ouvre une nouvelle fenêtre
     *
     * @param fenetre La nouvelle fenêtre à ouvrir
     */
    @NotNull()
    public void ouvrirFenetre(Fenetre fenetre) {
        fenetres.push(fenetre);
        fenetre.open(this);
        System.out.println("Ouverture de la fenetre : " + fenetres.peek().title);
        frame.revalidate();

        enJeu = fenetres.peek() instanceof EcranJeu;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
