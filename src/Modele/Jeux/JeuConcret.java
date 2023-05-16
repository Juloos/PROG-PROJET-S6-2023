package Modele.Jeux;

import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Coups.CoupTerminaison;
import Modele.IA.IA;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;
import Modele.Plateau;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import static Global.Config.TAILLE_PLATEAU_X;
import static Global.Config.TAILLE_PLATEAU_Y;

public class JeuConcret extends Jeu {
    Stack<Coup> passe;
    Stack<Coup> future;

    public JeuConcret() {
        super();
        passe = new Stack<>();
        future = new Stack<>();
    }

    public JeuConcret(Joueur[] joueurs) {
        super(joueurs);
        passe = new Stack<>();
        future = new Stack<>();
    }

    public JeuConcret(Joueur[] joueurs, Plateau plateau) {
        super(joueurs, plateau);
        passe = new Stack<>();
        future = new Stack<>();
    }

    public JeuConcret(JeuConcret jeu) {
        super(jeu);
        this.passe = jeu.passe;
        this.future = jeu.future;
    }

    public ArrayList<Coup> historique(int jusquaJoueur) {
        int dernierCoup = passe.size() - 1;
        while (dernierCoup >= 0 && passe.get(dernierCoup).getJoueur() != jusquaJoueur)
            dernierCoup--;
        ArrayList<Coup> coups = new ArrayList<>();
        for (int i = Integer.max(dernierCoup, 0); i < passe.size(); i++)
            coups.add(passe.get(i));
        return coups;
    }

    public Stack<Coup> getPasse() {
        return passe;
    }

    public Stack<Coup> getFuture() {
        return future;
    }

    public void setPasseFromFutur(Stack<Coup> futur) {
        while(!futur.empty()){
            this.passe.push(futur.pop());
        }
    }

    public void jouer(Coup c) {
        super.jouer(c);
        passe.push(c);
        future.clear();  // on doit vider la pile si l'on fait de nouveau coup après avoir reculer
    }
    @Override
    public void deplacerPion(Coord c1, Coord c2){
        if (!deplacementsPion(c1).contains(c2))
            throw new RuntimeException("Déplacement impossible vers la destination " + c2 + ".");
        super.deplacerPion(c1,c2);
    }
    @Override
    public void ajouterPion(Coord c){
        if (plateau.get(c) != 1 || Arrays.stream(joueurs).anyMatch(j -> j.estPion(c)))
            throw new RuntimeException("Impossible de placer le pion à l'emplacement " + c + ".");
        if (joueurs[joueurCourant].getPions().size() >= nbPions)
            throw new RuntimeException("Trop de pions.");
        super.ajouterPion(c);
    }

    @Override
    public void terminerJoueur(){
        if (joueurs[joueurCourant].peutJouer(this))
            throw new RuntimeException("Le joueur " + joueurCourant + " peut encore jouer.");
        super.terminerJoueur();
    }

    @Override
    public void annulerDeplacer(int j, Coord c1, Coord c2){
        if (!deplacementsPion(c1).contains(c2))
            throw new RuntimeException("Déplacement impossible vers la destination " + c2 + ".");
        super.annulerDeplacer(j,c1,c2);
    }

    public void annuler() {
        if (!passe.empty()) {
            Coup c = passe.pop();
            c.annuler(this);
            future.push(c);
        } else {
            System.out.println("Aucune action a annuler");
        }
    }

    public void refaire() {
        if (!future.empty()) {
            Coup c = future.pop();
            super.jouer(c);
            passe.push(c);
        } else {
            System.out.println("Aucune action a refaire");
        }
    }

    public void updateJoueurFromeHist(){
        super.joueurCourant = passe.peek().getJoueur();
        joueurSuivant();
    }


    public void sauvegarder(String fichier) {
        // Init fichier
        PrintWriter w_f;
        File f;
        try {
            f = new File(fichier);
            f.setReadable(true);
            f.setWritable(true);
            w_f = new PrintWriter(f);
        }catch (Exception E){
            System.out.println("Erreur : creation fichier de sauvegarde");
            return;
        }

        // Init var
        String sauv_data = "";
        Coup elem_hist;

        // Sauvegarde des joueurs a l'instant de la sauvegarde
        w_f.println(nbJoueurs);
        for (int i = 0; i < nbJoueurs; i++) {
            w_f.println(joueurs[i].toString());
        }

        // Sauvegarde le plateau a l'instant de la sauvegarde
        w_f.print(plateau);


        //On creer le String de data de sauvegarde (i.e la liste des coup réaliser)
        int compt = 0;
        while (!passe.empty()) {
            compt++;
            elem_hist = passe.pop();
            sauv_data += elem_hist.getSaveString() + " ";
            future.push(elem_hist);
        }
        // Cela sert a ne pas vider l'historique lorsque l'on sauvegarde
        while(compt!=0){
            compt--;
            passe.push(future.pop());
        }
        w_f.println(sauv_data);
        w_f.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static JeuConcret charger(String fichier) {

        int test, jou, val, nbJoueurs, testType;
        int id, score, tuile, r, q;
        HashMap<Coord,Boolean> pions;
        Stack<Coup> tempFutur = new Stack<>(), tempPasse = new Stack<>();
        IA.Difficulte difficulte = IA.Difficulte.ALEATOIRE;
        String nom = "";
        HashMap<Coord, Integer> tempHash;
        Coord tempCoord1, tempCoord2;
        Coup tempCoup;

        Scanner sc_f;

        // Init fichier
        try {
            sc_f = new Scanner(new File(fichier));
        } catch (Exception E) {
            System.err.println(fichier + " isn't accesible");
            return null;
        }

        //charge joueur
        nbJoueurs = sc_f.nextInt();
        Joueur[] joueurs = new Joueur[nbJoueurs];
        try {
            for (int i = 0; i < nbJoueurs; i++) {
                testType = sc_f.nextInt();
                id = sc_f.nextInt();
                switch (testType) {
                    case 0:
                        System.err.println(" erreur : Joueur non typé");
                        throw new Exception();
                    case 1:
                        sc_f.nextLine();
                        nom = sc_f.nextLine();
                        break;
                    case 2:
                        sc_f.nextLine();
                        nom = sc_f.nextLine();
                        switch (nom) {
                            case "ALEATOIRE":
                                difficulte = IA.Difficulte.ALEATOIRE;
                                break;
                            case "FACILE":
                                difficulte = IA.Difficulte.FACILE;
                                break;
                            case "MOYEN":
                                difficulte = IA.Difficulte.MOYEN;
                                break;
                            case "DIFFICILE":
                                difficulte = IA.Difficulte.DIFFICILE;
                                break;
                            default:
                                System.err.println(fichier + " isn't a save file : not a valid IA");
                                throw new Exception();
                        }
                        break;
                    default:
                        System.err.println(fichier + " isn't a save file : not a valid player");
                        throw new Exception();
                }
                score = sc_f.nextInt();
                tuile = sc_f.nextInt();
                pions = new HashMap<>();
                int nbPions = sc_f.nextInt();
                for (int j = 0; j < nbPions; j++) {
                    q = sc_f.nextInt();
                    r = sc_f.nextInt();
                    pions.put(new Coord(q, r), false);
                }
                switch (testType) {
                    case 1:
                        joueurs[i] = new JoueurHumain(id, score, tuile, pions, nom);
                        break;
                    case 2:
                        joueurs[i] = new JoueurIA(id, score, tuile, pions, difficulte);
                        break;
                }
            }
        }catch (Exception E) {
            System.err.println(" error during player setup");
            return null;
        }

        //Creation du jeu
        JeuConcret jeu = new JeuConcret(joueurs);
        if (jeu == null) {
            System.err.println("Error during game creation");
            return null;
        }

        // Charge plateau
        try {
            Coord c = new Coord();
            for (c.r = 0; c.r < TAILLE_PLATEAU_X; c.r++) {
                for (c.q = 0; c.q < TAILLE_PLATEAU_Y; c.q++) {
                    jeu.getPlateau().set(c, sc_f.nextInt());
                }
            }
        }catch (Exception E) {
            System.err.println("Error during board creation");
            return null;
        }

        // Charge historique
        try{
        // Tant que le fichier n'est pas vide, vérifie que le fichier est comforme et joue si il trouve un coup
            while (sc_f.hasNext()) {
                test = sc_f.nextInt();
                // repére le type du coup
                switch (test) {
                    case -1:
                        jou = sc_f.nextInt();
                        tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        tempCoup = new CoupAjout(tempCoord1, jou);
                        tempFutur.push(tempCoup);
                        break;
                    case -2:
                        jou = sc_f.nextInt();
                        tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        tempCoord2 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        val = sc_f.nextInt();
                        tempCoup = new CoupDeplacement(tempCoord1, tempCoord2, val, jou);
                        tempFutur.push(tempCoup);
                        break;
                    case -3:
                        jou = sc_f.nextInt();
                        tempHash = new HashMap<>();
                        int nbPions = sc_f.nextInt();
                        for (int j = 0; j < nbPions; j++) {
                            tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                            tempHash.put(tempCoord1, sc_f.nextInt());
                        }
                        tempCoup = new CoupTerminaison(tempHash, jou);
                        tempFutur.push(tempCoup);
                        break;
                    default:
                        System.err.println(fichier + " isn't a save file : not a valid history");
                        throw new Exception();
                }
            }
            if (!tempFutur.isEmpty()){
                jeu.setPasseFromFutur(tempFutur);
                jeu.updateJoueurFromeHist();
            }
            jeu.checkPionBloque();
            //terminaison
            sc_f.close();
            return jeu;
        } catch (Exception E) {
            System.err.println("Error during history creation");
            return null;
        }
    }

    public Coup dernierCoupJoue() {
        if (passe.empty()) {
            return null;
        }
        return passe.peek();
    }
}