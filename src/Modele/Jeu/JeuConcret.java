package Modele.Jeu;

import Modele.Coord;
import Modele.Coups.Coup;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Coups.CoupTerminaison;
import Modele.IA.IA;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import static Global.Config.*;

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

    public Stack<Coup> getPasse() {
        return passe;
    }

    public Stack<Coup> getFuture() {
        return future;
    }

    public void jouer(Coup c) {
        super.jouer(c);
        passe.push(c);
        future.clear();  // on doit vider la pile si l'on fait de nouveau coup après avoir reculer
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



    public void sauvegarder(String fichier) throws Exception {
        // Init fichier
        File f = new File(fichier);
        f.setReadable(true);
        f.setWritable(true);
        PrintWriter w_f = new PrintWriter(f);

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
    public static Jeu charger(String fichier) {

        int test, jou, val, nbJoueurs, testType;
        int id, score, tuile, r, q;
        HashMap<Coord,Boolean> pions;
        Stack<Coup> tempFutur = new Stack<>(), tempPasse = new Stack<>();
        IA.Difficulte difficulte = IA.Difficulte.ALEATOIRE;
        String temp, nom = "";
        HashMap<Coord, Integer> tempHash;
        Coord tempCoord1, tempCoord2;
        Coup tempCoup;

        Scanner sc_f;

        // Init fichier
        try {
            sc_f = new Scanner(new File(fichier));
        } catch (Exception E) {
            System.out.println(fichier + " isn't accesible");
            return null;
        }

        // Tant que le fichier n'est pas vide, vérifie que le fichier est comforme et joue si il trouve un coup
        try {

            //charge joueur
            nbJoueurs = sc_f.nextInt();
            Joueur[] joueurs = new Joueur[nbJoueurs];
            for(int i = 0; i<nbJoueurs; i++){
                testType = sc_f.nextInt();
                id = sc_f.nextInt();
                switch(testType){
                    case 0 :
                        System.out.println(" erreur : Joueur non typé");
                        throw new Exception();
                    case 1 :
                        nom = "";
                        temp = sc_f.next();
                        while(temp.equals("\0")){
                            nom += temp;
                            temp= sc_f.next();
                        }
                        break;
                    case 2 :
                        nom = "";
                        temp = sc_f.next();
                        while(temp.equals("\0")){
                            nom += temp;
                            temp= sc_f.next();
                        }
                        switch (nom){
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
                                System.out.println(fichier + " isn't a save file");
                                throw new Exception();
                        };
                        break;
                    default:
                        System.out.println(fichier + " isn't a save file");
                        throw new Exception();
                }
                score = sc_f.nextInt();
                tuile = sc_f.nextInt();
                pions = new HashMap<>();
                for(int j = 0; j < sc_f.nextInt(); j++){
                    q=sc_f.nextInt();
                    r=sc_f.nextInt();
                    pions.put(new Coord(q,r) , false);
                }
                switch(testType){
                    case 1 :
                        joueurs[i]= new JoueurHumain(id,score,tuile,pions,nom);
                        break;
                    case 2 :
                        joueurs[i]= new JoueurIA(id,score,tuile,pions,difficulte);
                        break;
                }
            }

            //Creation du jeu
            Jeu jeu = new JeuConcret(joueurs);

            //charge plateau
            Coord c = new Coord();
            for (c.r = 0; c.r < TAILLE_PLATEAU_X; c.r++){
                for (c.q = 0; c.q < TAILLE_PLATEAU_Y; c.q++){
                    jeu.getPlateau().set(c, sc_f.nextInt());
                }
            }

            //Charge historique
            test = sc_f.nextInt();
            while (sc_f.hasNext()) {
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
                        for (int j = 0; j < sc_f.nextInt(); j++) {
                            tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                            tempHash.put(tempCoord1, sc_f.nextInt());
                        }
                        tempCoup = new CoupTerminaison(tempHash, jou);
                        tempFutur.push(tempCoup);
                        break;
                    default:
                        throw new Exception();
                }
            }
            while(tempFutur.empty()){
                tempPasse.push(tempFutur.pop());
            }

            //terminaison
            sc_f.close();
            return jeu;
        } catch (Exception E) {
            System.out.println(fichier + " isn't a save file");
            return null;
        }
    }
}