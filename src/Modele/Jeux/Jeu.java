package Modele.Jeux;

import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;
import Modele.Plateau;

import java.util.ArrayList;
import java.util.Arrays;

import static Global.Config.*;

public abstract class Jeu {
    final Plateau plateau;
    final Joueur[] joueurs;
    int nbJoueurs;
    int nbPions;
    int joueurCourant;
    int dernierJoueurMort = -1;

    public Jeu() {
        plateau = new Plateau();
        joueurs = new Joueur[NB_JOUEUR];
        for (int i = 0; i < NB_JOUEUR; i++)
            joueurs[i] = new JoueurIA(i);
        joueurCourant = 0;
        nbJoueurs = NB_JOUEUR;
        nbPions = NB_PIONS_TOTAL / nbJoueurs;
    }

    public Jeu(Joueur[] joueurs) {
        plateau = new Plateau();
        this.joueurs = joueurs;
        nbJoueurs = joueurs.length;
        if (nbJoueurs > NB_MAX_JOUEUR)
            throw new IllegalArgumentException("Trop de joueurs");
        joueurCourant = 0;
        nbPions = NB_PIONS_TOTAL / nbJoueurs;
    }

    public Jeu(Joueur[] joueurs, Plateau plateau) {
        this.plateau = plateau;
        this.joueurs = joueurs;
        nbJoueurs = joueurs.length;
        if (nbJoueurs > NB_MAX_JOUEUR)
            throw new IllegalArgumentException("Trop de joueurs");
        joueurCourant = 0;
        nbPions = NB_PIONS_TOTAL / nbJoueurs;
    }

    public Jeu(Jeu jeu) {
        this.plateau = jeu.plateau.clone();

        Joueur[] copyJoueurs = new Joueur[jeu.getNbJoueurs()];
        for (int i = 0; i < jeu.getNbJoueurs(); i++) {
            copyJoueurs[i] = jeu.getJoueur(i).clone();
        }

        this.joueurs = copyJoueurs;
        this.nbJoueurs = jeu.nbJoueurs;
        this.nbPions = jeu.nbPions;
        this.joueurCourant = jeu.joueurCourant;
    }


    // Ensemble des methodes pour recupérer les variables de jeu
    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public Joueur getJoueur() {
        return joueurs[joueurCourant];
    }

    public Joueur getJoueur(int id) {
        return joueurs[id];
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public int getNbPions() { return nbPions; }

    public int getDernierJoueurMort() { return dernierJoueurMort; }

    public Plateau getPlateau() {
        return plateau;
    }


    // Ensemble des methode pour verifier la validiter d'un parametre
    public boolean estJoueurValide(int joueur) {
        return joueur >= 0 && joueur < nbJoueurs;
    }

    public boolean estPion(Coord c) {
        return Arrays.stream(joueurs).anyMatch(j -> j.estPion(c));
    }

    public boolean peutJouer(int joueur) {
        return estJoueurValide(joueur) && joueurs[joueur].peutJouer(this);
    }

    // Ensemble des methodes pour obtenir l'état de la partie, d'un pion ou d'un joueur
    public boolean estTermine() {
        return Arrays.stream(joueurs).allMatch(Joueur::estTermine);
    }

    public boolean peutJouer() {
        return peutJouer(joueurCourant);
    }

    public int joueurDePion(Coord c) {
        for (int i = 0; i < nbJoueurs; i++)
            if (joueurs[i].estPion(c))
                return i;
        return -1;
    }

    public boolean estPionBloque(Coord c) {
        return estPion(c) && c.voisins().stream().allMatch(
                v -> plateau.get(v) == Plateau.VIDE || estPion(v)
        );
    }

    public void checkPionBloque() {
        for (Joueur j : joueurs) {
            j.getPions().forEach(c -> c.voisins().forEach(voisin -> {
                        if (estPionBloque(c))
                            j.bloquerPion(c);
                        if (estPionBloque(voisin))
                            joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                    })

            );
            if(!j.peutJouer(this)){
                j.terminer();
            }
        }
    }


    // Mise a jour du tour de joueur
    void joueurSuivant() {
        joueurCourant = (joueurCourant + 1) % nbJoueurs;
        for (int i = 1; i < nbJoueurs; i++) {
            if (getJoueur().estTermine())
                joueurCourant = (joueurCourant + 1) % nbJoueurs;
            else
                return;
        }
    }


    // Methode pour obtenier l'ensemble des gagnant selon les règles
    public ArrayList<Integer> getWinner() {
        ArrayList<Integer> winners = new ArrayList<>();
        Joueur max = Arrays.stream(joueurs).max(Joueur::compareTo).get();
        for (int i = 0; i < nbJoueurs; i++)
            if (joueurs[i].compareTo(max) == 0)
                winners.add(i);
        return winners;
    }


    // Methode de modification du plateau
    public void generateNewPlateau() {
        plateau.randomInit();
    }

    public void manger(Coord c) {
        joueurs[joueurCourant].ajouterTuile(plateau.get(c));
        plateau.set(c, Plateau.VIDE);
    }


    // Methode pour obtenir l'ensemble des emplacement valide lors de la phase de placement
    public synchronized ArrayList<Coord> placementsPionValide() {
        ArrayList<Coord> liste = new ArrayList<>();
        for (int i = 0; i < plateau.getNbColumns(); i++) {
            for (int j = 0; j < plateau.getNbRows(); j++) {
                Coord check = new Coord(i, j);
                if (plateau.get(check) == 1 && !estPion(check)) {
                    liste.add(check);
                }
            }
        }
        return liste;
    }


    // Methode pour obtenir l'ensemble des deplacements possible lors de la phase de deplacement
    public ArrayList<Coord> deplacementsPion(Coord c) {
        ArrayList<Coord> liste = new ArrayList<>();
        for (int dir = 0; dir < 6; dir++) {
            Coord curr = c.decale(dir);
            while (plateau.get(curr) != Plateau.VIDE && !estPion(curr)) {
                liste.add(curr);
                curr = curr.decale(dir);
            }
        }
        return liste;
    }


    // Methode pour jouer un coup (envoie vers la bonne implementation)
    public void jouer(Coup c) {
        if (c.getJoueur() == joueurCourant)
            c.jouer(this);
    }


    // Ensemble des methodes implementant les coups d'un joueur
    public void deplacerPion(Coord c1, Coord c2) {

        manger(c1);
        joueurs[joueurCourant].deplacerPion(c1, c2);
        if (estPionBloque(c2))
            joueurs[joueurCourant].bloquerPion(c2);
        c1.voisins().forEach(
                voisin -> {
                    if (estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                }
        );
        c2.voisins().forEach(
                voisin -> {
                    if (estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                }
        );
        joueurSuivant();
    }

    public void ajouterPion(Coord c) {
        joueurs[joueurCourant].ajouterPion(c);
        if (estPionBloque(c))
            joueurs[joueurCourant].bloquerPion(c);
        c.voisins().forEach(
                voisin -> {
                    if (estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                }
        );
        joueurSuivant();
    }

    public void terminerJoueur() {
        dernierJoueurMort = joueurCourant;
        joueurs[joueurCourant].terminer();
        for (Coord c : joueurs[joueurCourant].getPions())
            manger(c);
        joueurSuivant();
    }


    // Ensemble des methodes implementant l'annulation d'un coup
    public void annulerDeplacer(int j, Coord c1, Coord c2) {
        joueurs[j].deplacerPion(c1, c2);
        c1.voisins().forEach(
                voisin -> {
                    if (estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].debloquerPion(voisin);
                }
        );
        c2.voisins().forEach(
                voisin -> {
                    if (estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                }
        );
        this.joueurCourant = j;
    }

    public void annulerAjout(int j, Coord cible) {
        this.getJoueur(j).supprimerPion(cible);
        this.joueurCourant = j;
    }

    public void annulerTerminaison(int joueur, Coord source, int oldVal) {
        this.getPlateau().set(source, oldVal);
        this.getJoueur(joueur).replacerPion(source, true);
        this.getJoueur(joueur).supprimerTuile();
        this.getJoueur(joueur).decrementerScore(oldVal);
        this.getJoueur(joueur).reAnimer();
        this.joueurCourant = joueur;

    }


    // To String
    @Override
    public String toString() {
        // Même format que Plateau.toString() mais en mettant en couleur les pions des différents joueurs,
        // et une Ligne de score à la fin.
        StringBuilder str = new StringBuilder();
        for (int r = 0; r < plateau.getNbRows(); r++) {
            if (r % 2 == 0)
                str.append(" ");
            for (int q = 0; q < plateau.getNbColumns(); q++) {
                Coord c = new Coord(q, r);
                str.append(COULEURS[joueurDePion(c) + 1]);
                str.append(plateau.get(c) != Plateau.VIDE ? plateau.get(c) : (r % 2 == 0 && q == plateau.getNbColumns() - 1 ? " " : "."));
                str.append(" ");
            }
            str.append("\n");
        }
        str.append(COULEURS[0]);
        str.append("Score : ");
        for (int i = 0; i < nbJoueurs; i++) {
            str.append(COULEURS[i + 1]);
            str.append(joueurs[i].getScore());
            str.append(COULEURS[0]);
            str.append(" - ");
        }
        str.delete(str.length() - 3, str.length());
        return str.toString();
    }
}
