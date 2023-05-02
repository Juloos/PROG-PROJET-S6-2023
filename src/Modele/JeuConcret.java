package Modele;

import java.util.Stack;

public class JeuConcret extends Jeu {
    Stack<Coup> passe;
    Stack<Coup> future;

    public JeuConcret() {
        super();
        passe = new Stack<>();
        future = new Stack<>();
    }

    public void jouer(Coup c) {
        super.jouer(c);
        passe.push(c);
        future.clear();  // on doit vider la pile si l'on fait de nouveau coup après avoir reculer
    }

    public void annuler(Jeu j) {
        Coup c = passe.pop();
        c.annuler(j);
        future.push(c);
    }
    public void refaire() {
        Coup c = future.pop();
        super.jouer(c);
        passe.push(c);
    }
    public void sauvegarder(String fichier) {

    }
    public void charger(String fichier) {

    }
}