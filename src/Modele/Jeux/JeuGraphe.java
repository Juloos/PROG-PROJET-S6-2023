package Modele.Jeux;

import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Coups.CoupTerminaison;
import Modele.Joueurs.Joueur;
import Modele.Plateau;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static Global.Config.DEBUG;

public class JeuGraphe extends Jeu {
    HashMap<Coord, HashSet<Coord>> ilots = new HashMap<>();

    public JeuGraphe(Jeu j) {
        super(new Joueur[j.joueurs.length], j.plateau.clone());
        for (int i = 0; i < nbJoueurs; i++)
            joueurs[i] = j.joueurs[i].clone();
        joueurCourant = j.joueurCourant;
    }

    public JeuGraphe(JeuGraphe j) {
        super(new Joueur[j.joueurs.length], j.plateau.clone());
        for (int i = 0; i < nbJoueurs; i++)
            joueurs[i] = j.joueurs[i].clone();
        joueurCourant = j.joueurCourant;
        ilots = new HashMap<>(j.ilots);
        System.out.println("JeuGraphe(JeuGraphe j)");
    }

    private ArrayList<Coup> abstractCoupsPossibles(boolean avecPionsIsoles) {
        ArrayList<Coup> coups = new ArrayList<>();
        if (getJoueur().getPions().size() < nbPions) {
            for (Coord c : placementsPionValide())
                coups.add(new CoupAjout(c, joueurCourant));
        } else if (peutJouer()) {
            for (Coord s : joueurs[joueurCourant].getPions())
                if (avecPionsIsoles || !estPionIsole(s))
                    for (Coord d : deplacementsPion(s))
                        coups.add(new CoupDeplacement(s.clone(), d, joueurCourant));
        } else if (!getJoueur().estTermine())
            coups.add(new CoupTerminaison(joueurCourant));
        return coups;
    }

    public ArrayList<Coup> coupsPossibles() {
        return abstractCoupsPossibles(true);
    }

    public ArrayList<Coup> coupsPossiblesOpti() {
        ArrayList<Coup> coups = abstractCoupsPossibles(false);

        if (coups.isEmpty()) {
            HashSet<Coord> traitees = new HashSet<>();
            for (Coord pion : getJoueur().getPions()) {
                if (traitees.contains(pion))
                    continue;
                ArrayList<Coord> pionsIlot = new ArrayList<>();
                pionsIlot.add(pion);
                traitees.add(pion);
                for (Coord autrePion : getJoueur().getPions()) {
                    if (traitees.contains(autrePion))
                        continue;
                    traitees.add(autrePion);
                    if (ilots.get(pion).equals(ilots.get(autrePion)))
                        pionsIlot.add(autrePion);
                }

                Coup coupIlot;
                if ((coupIlot = remplirIlot(joueurs[joueurCourant], pionsIlot)) != null) {
                    coups.add(coupIlot);
                    break;
                }
            }
        }

        return coups;
    }

    @Override
    public void deplacerPion(Coord s, Coord d) {
        super.deplacerPion(s, d);
        ilots.put(d, ilots.remove(s));
    }

    @Override
    public void annulerDeplacer(int j, Coord c1, Coord c2) {
        super.annulerDeplacer(j, c1, c2);
        ilots.remove(c2);
    }

    public boolean estPionIsole(Coord c) {
        if (ilots.containsKey(c))
            return ilots.get(c) != null;

        int cjp = joueurDePion(c);
        ArrayList<Coord> pionsAmis = new ArrayList<>();
        pionsAmis.add(c);

        HashSet<Coord> traites = new HashSet<>();
        ArrayList<Coord> enAttente = new ArrayList<>();
        enAttente.add(c);
        while (!enAttente.isEmpty()) {
            Coord co = enAttente.remove(0);
            traites.add(co);
            int jp = joueurDePion(co);
            if (jp != -1 && jp != cjp) {
                ilots.put(c, null);
                ilots.put(co, null);
                return false;
            } else if (jp != -1)
                pionsAmis.add(co);
            for (Coord v : co.voisins())
                if (plateau.get(v) != Plateau.VIDE && !traites.contains(v))
                    enAttente.add(v);
        }

        for (Coord pion : pionsAmis)
            ilots.put(pion, traites);
        return true;
    }

    public HashSet<Coord> getIlot(Coord c) {
        return ilots.get(c);
    }

    public Coup remplirIlot(Joueur j, ArrayList<Coord> pions) {
        Coup maxcoup = null;
        int max = 0;
        for (Coord s : pions) {
            for (Coord d : deplacementsPion(s)) {
                CoupDeplacement cou = new CoupDeplacement(s, d, joueurCourant);
                jouer(cou);
                pions.remove(s);
                pions.add(d);
                int k = remplir(j, pions);
                if (k > max) {
                    maxcoup = cou;
                    max = k;
                }
                pions.remove(d);
                pions.add(s);
                cou.annuler(this);
            }
        }
        return maxcoup;
    }

    public int remplir(Joueur j, ArrayList<Coord> pions) {
        int valeur = 0;
        if (j.estTermine())
            return getJoueur(joueurCourant).getScore();
        for (Coord s : pions) {
            for (Coord d : deplacementsPion(s)) {
                CoupDeplacement cou = new CoupDeplacement(s, d, getJoueur(joueurCourant).getScore(), joueurCourant);
                jouer(cou);
                pions.remove(s);
                pions.add(d);
                int k = remplir(j, pions);
                if (k > valeur)
                    valeur = k;
                pions.remove(d);
                pions.add(s);
                cou.annuler(this);
            }
        }
        return valeur;
    }
}
