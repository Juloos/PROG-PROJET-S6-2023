package IHM;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.util.Stack;

public class IHMGraphique extends IHM {

    Stack<Fenetre> fenetres;
    JFrame frame;

    public IHMGraphique() {
        super();

        fenetres = new Stack<Fenetre>();

        frame = new JFrame("");

        ouvrirFenetre(new EcranAccueil());

        frame.setSize(1500, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**Détruit toutes les fenêtres de l'IHM et retourne au menu d'accueil
     */
    public void fermerFenetres() {

    }

    /**Retourne à la fenêtre précédente et détruit la fenêtre actuelle
     */
    public void retournerPrecedenteFenetre() {
        fenetres.peek().close(this);
        System.out.println("Fermeture de la fenetre : " + fenetres.peek().title);
        fenetres.pop();
        fenetres.peek().open(this);
        System.out.println("Ouverture de la fenetre : " + fenetres.peek().title);
        frame.revalidate();
    }

    /**Ouvre une nouvelle fenêtre
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
