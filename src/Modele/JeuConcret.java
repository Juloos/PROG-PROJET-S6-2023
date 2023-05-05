package Modele;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

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

    public ArrayList<Coup> historique(int joueur) {
        int dernierCoup = passe.size() - 1;
        while (passe.get(dernierCoup).getJoueur() != joueur)
            dernierCoup--;
        ArrayList<Coup> coups = new ArrayList<>();
        for (int i = dernierCoup; i < passe.size(); i++)
            coups.add(passe.get(i));
        return coups;
    }

    public void jouer(Coup c) {
        super.jouer(c);
        passe.push(c);
        future.clear();  // on doit vider la pile si l'on fait de nouveau coup après avoir reculer
    }

    public void annuler() {
        if(!passe.empty()){
            Coup c = passe.pop();
            c.annuler(this);
            future.push(c);
        }else{
            System.out.println("Aucune action a annuler");
        }
    }

    public void refaire() {
        if(!future.empty()){
            Coup c = future.pop();
            super.jouer(c);
            passe.push(c);
        }else{
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

        // Sauvegarde le plateau a l'instant de la sauvegarde
        w_f.print(plateau);

        // Sauvegarde les a l'instant de la sauvegarde
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur jou = super.getJoueur(i);
            Coord[] tempL = jou.getPions().toArray(new Coord[nbPions]);
            for (int j = 0; j < jou.getPions().size(); j++) {
                sauv_data += " " + tempL[j].q + " " + tempL[j].r;
            }
            w_f.println(i + " " + jou.getScore() + " " + jou.getTuiles() + sauv_data);
        }

        sauv_data = "";
        //On creer le String de data de sauvegarde (i.e la liste des coup réaliser)
        while (!passe.empty()) {
            elem_hist = passe.pop();
            sauv_data += elem_hist.getSaveString() + " ";
        }
        w_f.println(sauv_data);
        w_f.close();
    }

    public void charger(String fichier) {
        int test, jou, val;
        HashMap<Coord, Integer> tempHash;
        Coord tempCoord1, tempCoord2;
        Coup tempCoup;

        Scanner sc_f;

        // Init fichier
        try {
            sc_f = new Scanner(new File(fichier));
        } catch (Exception E) {
            System.out.println(fichier + " isn't accesible");
            return;
        }

        // Tant que le fichier n'est pas vide, vérifie que le fichier est comforme et joue si il trouve un coup
        try {
            while (sc_f.hasNext()) {
                //sauve plateau
                //sauve joueur
                test = sc_f.nextInt();
                // repére le type du coup
                switch (test) {
                    case -1:
                        jou = sc_f.nextInt();
                        tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        tempCoup = new CoupAjout(tempCoord1, jou);
                        passe.push(tempCoup);
                        break;
                    case -2:
                        jou = sc_f.nextInt();
                        tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        tempCoord2 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        val = sc_f.nextInt();
                        tempCoup = new CoupDeplacement(tempCoord1, tempCoord2, val, jou);
                        passe.push(tempCoup);
                        break;
                    case -3:
                        jou = sc_f.nextInt();
                        tempHash = new HashMap<>();
                        for (int j = 0; j < nbPions; j++) {
                            tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                            tempHash.put(tempCoord1, sc_f.nextInt());
                        }
                        tempCoup = new CoupTerminaison(tempHash, jou);
                        passe.push(tempCoup);
                        break;
                    default:
                        throw new Exception();
                }
            }
        } catch (Exception E) {
            System.out.println(fichier + " isn't a save file");
            return;
        }

        sc_f.close();

    }
}