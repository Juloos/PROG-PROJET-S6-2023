package Controleur;

import Global.Config;
import IHM.Console.IHMConsole;
import IHM.Graphique.IHMGraphique;
import IHM.IHM;
import Modele.*;

public class MoteurJeu implements Runnable {

    IHM ihm;

    JeuConcret jeu;

    int nbPionsPlaces;

    public MoteurJeu() {
        Joueur[] joueurs = new Joueur[]{new JoueurHumain(0), new JoueurHumain(1)};
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

        if (ihm != null) {
            ihm.updateAffichage(jeu);
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
            System.out.println("Nouveau pion placé");
            if (ihm != null)
                ihm.updateAffichage(jeu);
        } else if (ihm != null)
            ihm.afficherMessage("Coup injouable");
    }

    public void annulerCoup() {
        System.out.println("Annulation du dernier coup joué");
        jeu.annuler();
    }

    public void refaireCoup() {
        System.out.println("Refaison du dernier coup annulé");
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
    }

    @Override
    public void run() {
        if (ihm != null)
            ihm.updateAffichage(jeu);

        nbPionsPlaces = 0;
        while (nbPionsPlaces < jeu.getNbJoueurs() * jeu.getNbPions()) {
            appliquerAction(jeu.getJoueur().reflechir(this));
        }

        while (!jeu.estTermine()) {
            while (jeu.peutJouer())
                appliquerAction(jeu.getJoueur().reflechir(this));
            jeu.jouer(new CoupTerminaison(jeu.getJoueur().id));
        }
    }
}
