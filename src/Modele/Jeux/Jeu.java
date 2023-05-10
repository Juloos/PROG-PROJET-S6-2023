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

    public Joueur getJoueur() {
        return joueurs[joueurCourant];
    }

    public Joueur getJoueur(int id) {
        return joueurs[id];
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public int getNbPions() {
        return nbPions;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean estJoueurValide(int joueur) {
        return joueur >= 0 && joueur < nbJoueurs;
    }

    public boolean peutJouer(int joueur) {
        return estJoueurValide(joueur) && joueurs[joueur].peutJouer(this);
    }

    public boolean peutJouer() {
        return peutJouer(joueurCourant);
    }

    public boolean estTermine() {
        return Arrays.stream(joueurs).allMatch(Joueur::estTermine);
    }

    public boolean estPion(Coord c) {
        return Arrays.stream(joueurs).anyMatch(j -> j.estPion(c));
    }

    public boolean estPionBloque(Coord c) {
        return estPion(c) && c.voisins().stream().allMatch(
                v -> !plateau.estCoordValide(v) || plateau.get(v) == Plateau.VIDE || estPion(v)
        );
    }

    public int joueurDePion(Coord c) {
        for (int i = 0; i < nbJoueurs; i++)
            if (joueurs[i].estPion(c))
                return i;
        return -1;
    }

    public void manger(Coord c) {
        joueurs[joueurCourant].ajouterTuile(plateau.get(c));
        plateau.set(c, Plateau.VIDE);
    }

    public ArrayList<Coord> deplacementsPion(Coord c) {
        ArrayList<Coord> liste = new ArrayList<>();
        for (int dir = 0; dir < 6; dir++) {
            Coord curr = c.decale(dir);
            while (plateau.estCoordValide(curr) && plateau.get(curr) != Plateau.VIDE && !estPion(curr)) {
                liste.add(curr);
                curr = curr.decale(dir);
            }
        }
        return liste;
    }


    public ArrayList<Coord> placementsPionValide() {
        ArrayList<Coord> liste = new ArrayList<>();
        for (int i = 0; i < plateau.getNbColumns(); i++) {
            for (int j = 0; j < plateau.getNbRows(); j++) {
                Coord check = new Coord(i, j);
                if (plateau.estCoordValide(check) && plateau.get(check) == 1 && !estPion(check)) {
                    liste.add(check);
                }
            }
        }
        return liste;
    }

    void joueurSuivant() {
        joueurCourant = (joueurCourant + 1) % nbJoueurs;
        for (int i = 1; i < nbJoueurs; i++) {
            if (getJoueur().estTermine())
                joueurCourant = (joueurCourant + 1) % nbJoueurs;
            else
                return;
        }
    }

    public void deplacerPion(Coord c1, Coord c2) {
        if (!deplacementsPion(c1).contains(c2))
            throw new RuntimeException("Déplacement impossible vers la destination " + c2 + ".");
        manger(c1);
        joueurs[joueurCourant].deplacerPion(c1, c2);
        if (estPionBloque(c2))
            joueurs[joueurCourant].bloquerPion(c2);
        c1.voisins().forEach(
                voisin -> {
                    if (estPion(voisin) && estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                }
        );
        c2.voisins().forEach(
                voisin -> {
                    if (estPion(voisin) && estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                }
        );
        joueurSuivant();
    }

    public void ajouterPion(Coord c) {
        if (plateau.get(c) != 1 || Arrays.stream(joueurs).anyMatch(j -> j.estPion(c)))
            throw new RuntimeException("Impossible de placer le pion à l'emplacement " + c + ".");
        if (joueurs[joueurCourant].getPions().size() >= nbPions)
            throw new RuntimeException("Trop de pions.");
        joueurs[joueurCourant].ajouterPion(c);
        if (estPionBloque(c))
            joueurs[joueurCourant].bloquerPion(c);
        c.voisins().forEach(
                voisin -> {
                    if (estPion(voisin) && estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                }
        );
        joueurSuivant();
    }

    public void terminerJoueur() {
        if (joueurs[joueurCourant].peutJouer(this))
            throw new RuntimeException("Le joueur " + joueurCourant + " peut encore jouer.");
        joueurs[joueurCourant].terminer();
        for (Coord c : joueurs[joueurCourant].getPions())
            manger(c);
        joueurs[joueurCourant].getPions().clear();
        joueurSuivant();
    }

    public void jouer(Coup c) {
        if (c.getJoueur() != joueurCourant)
            throw new IllegalArgumentException("Mauvaise selection du joueur du coup : " + c + ".");
        c.jouer(this);
    }

    public void annulerDeplacer(int j, Coord c1, Coord c2) {
        if (!deplacementsPion(c1).contains(c2))
            throw new RuntimeException("Déplacement impossible vers la destination " + c2 + ".");
        joueurs[j].deplacerPion(c1, c2);
        c1.voisins().forEach(
                voisin -> {
                    if (estPion(voisin) && estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].debloquerPion(voisin);
                }
        );
        c2.voisins().forEach(
                voisin -> {
                    if (estPion(voisin) && estPionBloque(voisin))
                        joueurs[joueurDePion(voisin)].bloquerPion(voisin);
                }
        );
        this.joueurCourant = j;
    }

    public void annulerAjout(int j, Coord cible) {
        this.getJoueur(j).supprimerPion(cible);
        this.joueurCourant = j;
    }
    public void annulerTerminaison(int joueur,Coord source, int oldVal){
        this.getPlateau().set(source, oldVal);
        this.getJoueur(joueur).ajouterPion(source);
        this.getJoueur(joueur).replacerPion(source, true);
        this.getJoueur(joueur).supprimerTuile();
        this.getJoueur(joueur).decrementerScore(oldVal);
        this.getJoueur(joueur).reAnimer();
        this.joueurCourant = joueur;

    }

    public ArrayList<Integer> getWinner() {
        ArrayList<Integer> winners = new ArrayList<>();
        Joueur max = Arrays.stream(joueurs).max(Joueur::compareTo).get();
        for (int i = 0; i < nbJoueurs; i++)
            if (joueurs[i].compareTo(max) == 0)
                winners.add(i);
        return winners;
    }

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
