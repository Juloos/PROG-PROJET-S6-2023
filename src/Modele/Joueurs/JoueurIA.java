package Modele.Joueurs;

import Controleur.MoteurJeu;
import Modele.Actions.ActionCoup;
import Modele.Coord;
import Modele.IA.IA;
import Modele.IA.IAAleatoire;

import java.util.HashMap;

import static Global.Config.DEBUG;

public class JoueurIA extends Joueur {
    IA ia;

    public JoueurIA(int id) {
        super(id);
        this.ia = new IAAleatoire(id);
    }

    public JoueurIA(int id, IA.Difficulte d) {
        super(id);
        ia = IA.getIA(d, id);
        if (ia == null)
            throw new IllegalArgumentException("Difficulté inconnue : " + d);
    }

    public JoueurIA(int id, IA ia) {
        super(id);
        this.ia = ia;
    }

    public JoueurIA(int id, int score, int tuiles, HashMap<Coord, Boolean> pions,  IA.Difficulte d) {
        super(id, score, tuiles, pions);
        ia = IA.getIA(d, id);
        if (ia == null)
            throw new IllegalArgumentException("Difficulté inconnue : " + d);
    }

    @Override
    public void reflechir(MoteurJeu mt) {
        if (DEBUG)
            System.out.println("Le joueur IA réfléchit");
        if (mt.getJeu().getJoueur().id != id)
            throw new IllegalArgumentException("Mauvais joueur courant pôur ce joueur : " + mt.getJoueurActif().id);
        mt.afficherMessage("Le joueur " + getNom() + " réfléchit...", 0);
        mt.appliquerAction(new ActionCoup(ia.reflechir(mt.getJeu())));
        if (DEBUG)
            System.out.println("Fin réflexion du joueur IA");
    }

    @Override
    public JoueurIA clone() {
        return new JoueurIA(id, score, tuiles, new HashMap<>(pions),  ia.getDifficulte());
    }

    @Override
    public String getNom() {
        return "IA " + ia.getDifficulte();
    }

    @Override
    public String toString() {
        String dataHash = "";
        Coord[] tempL = pions.keySet().toArray(new Coord[pions.size()]);
        for (int j = 0; j < pions.size(); j++) {
            dataHash += " " + tempL[j].q + " " + tempL[j].r;
        }
        return 2 + " " + id + " \n" + ia.getDifficulte() + "\n " + score + " " + tuiles + " " + pions.size() + dataHash;
    }

    @Override
    public JoueurIA resetJoueur() {
        return new JoueurIA(this.id, this.ia.getDifficulte());
    }
}