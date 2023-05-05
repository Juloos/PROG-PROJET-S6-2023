package Controleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import Modele.Actions.Action;
import Modele.Coups.Coup;
import Modele.Coups.CoupTerminaison;
import Modele.Jeu.JeuConcret;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;

public class MoteurJeu implements Runnable {

    IHM ihm;

    JeuConcret jeu;

    int nbPionsPlaces;

    public MoteurJeu() {
        Joueur[] joueurs = new Joueur[]{new JoueurHumain(0), new JoueurIA(1), new JoueurIA(2), new JoueurIA(3)};
        jeu = new JeuConcret(joueurs);

        switch (Config.TYPE_IHM) {
            case CONSOLE:
                ihm = new IHMConsole(this);
                break;
            case GRAPHIQUE:
                ihm = new IHMGraphique(this);
                break;
            case AUCUNE:
                ihm = null;
                break;
        }
    }

    public IHM getIHM() {
        return ihm;
    }

    public JeuConcret getJeu() {
        return jeu;
    }

    public Joueur getJoueurActif() {
        return jeu.getJoueur();
    }

    public boolean estPhasePlacementPions() {
        return nbPionsPlaces < jeu.getNbJoueurs() * jeu.getNbPions();
    }

    public void jouerCoup(Coup coup) {
        if (coup.estJouable(jeu)) {
            jeu.jouer(coup);
            nbPionsPlaces++;
            if (ihm != null)
                ihm.updateAffichage(jeu);
        } else if (ihm != null)
            ihm.afficherMessage("Coup injouable");
    }

    public void annulerCoup() {
        System.out.println("Annulation du dernier coup joué");
        nbPionsPlaces--;
        jeu.annuler();
    }

    public void refaireCoup() {
        System.out.println("Refaison du dernier coup annulé");
        nbPionsPlaces++;
        jeu.refaire();
    }

    public void sauvegarder() {
        System.out.println("Sauvegarde de la partie");
        try {
            jeu.sauvegarder("sauvegarde.txt");
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde");
        }
    }

    public void appliquerAction(Action action) {
        if (!action.peutAppliquer(this)) {
            ihm.afficherMessage("Action non applicable");
        } else {
            action.appliquer(this);
        }

        if (ihm != null) {
            ihm.updateAffichage(jeu);
        }
    }

    @Override
    public void run() {
        if (ihm != null)
            ihm.updateAffichage(jeu);

        nbPionsPlaces = 0;
        while (estPhasePlacementPions()) {
            appliquerAction(jeu.getJoueur().reflechir(this));
        }

        System.out.println("Fin de la phase de placements des pions");

        while (!jeu.estTermine()) {
            while (jeu.peutJouer()) {
                appliquerAction(jeu.getJoueur().reflechir(this));
            }
            jeu.jouer(new CoupTerminaison(jeu.getJoueur().id));
        }

        System.out.println("Fin de jeu");
    }
}
