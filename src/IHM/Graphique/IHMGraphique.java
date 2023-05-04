package IHM.Graphique;

import Controleur.MoteurJeu;
import IHM.Graphique.Ecrans.EcranJeu;
import IHM.IHM;
import Modele.Jeu;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.util.Stack;

public class IHMGraphique extends IHM {

    Stack<Fenetre> fenetres;
    JFrame frame;

    public IHMGraphique(MoteurJeu moteurJeu) {
        super(moteurJeu);

        fenetres = new Stack<>();

        frame = new JFrame("");

        ouvrirFenetre(new EcranJeu());

        frame.setSize(1500, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void updateAffichage(Jeu jeu) {
        if (fenetres.peek() instanceof EcranJeu) {
            System.out.println("On met à jour");
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
    }
}
