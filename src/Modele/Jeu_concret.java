package Modele;

import Global.Config;

import java.util.Stack;

public class Jeu_concret extends Jeu {

    public class Elem_pile{
        Coord origine;
        Coord but;
        int val_origine;
        int joueur;

        public Elem_pile(Coord o, Coord b, int v, int j){
            origine = o;
            but = b;
            val_origine = v;
            joueur = j;
        }

        public Coord getOrigine(){ return origine; }
        public Coord getBut() { return but; }
        public int getVal_origine() { return val_origine; }
        public int getJoueur() { return joueur; }
    }

    Stack<Elem_pile> pil_passer;
    Stack<Elem_pile> pil_futur;
    public Jeu_concret(){
        pil_passer = new Stack<Elem_pile>();
        pil_futur = new Stack<Elem_pile>();
    }

    public void ajouter_pile(Coord origine,Coord but,int val_origine,int joueur){
        Elem_pile elem = new Elem_pile(origine, but, val_origine, joueur);
        pil_passer.push(elem);
        // on doit vider la pile si l'on fait de nouveau coup après avoir reculer
        while(!pil_futur.empty()){
            pil_futur.pop();
        }
    }

    @Override
    public void deplacerPion(Coord c1, Coord c2) {
        ajouter_pile(c1,c2, plateau.get(c1),joueurCourant);
        super.deplacerPion(c1, c2);
    }

    @Override
    public void ajouterPion(Coord c) {
        ajouter_pile(new Coord(-1,-1),c,-1,joueurCourant);
        super.ajouterPion(c);
    }

    @Override
    public void terminerJoueur(int joueur) {
        if (peutJouer(joueur))
            return;

        if (joueur == J1){
            for (Coord c : joueurs[J1].pions.keySet()){
                ajouter_pile(c,new Coord(-1,-1), plateau.get(c),joueur);
                manger(c);
            }
        }else {
            for (Coord c : joueurs[J2].pions.keySet()) {
                ajouter_pile(c,new Coord(-1,-1), plateau.get(c),joueur);
                manger(c);
            }
        }
        joueurCourant = joueurSuivant();
    }


}