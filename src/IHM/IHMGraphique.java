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
        fenetres.pop();
        fenetres.peek().open(this);
        frame.revalidate();
    }

    /**Ouvre une nouvelle fenêtre
     * @param fenetre La nouvelle fenêtre à ouvrir
     */
    @NotNull()
    public void ouvrirFenetre(Fenetre fenetre) {
        fenetres.push(fenetre);
        fenetre.open(this);
        frame.revalidate();
    }
}
