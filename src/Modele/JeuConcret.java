package Modele;

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

        // Sauvegarde les a l'instant de la sauvegarde
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur jou = super.getJoueur(i);
            Coord[] tempL = jou.getPions().toArray(new Coord[jou.getPions().size()]);
            for (int j = 0; j < jou.getPions().size(); j++) {
                sauv_data += " " + tempL[j].q + " " + tempL[j].r;
            }
            if (jou.getClass().getName() == "JoueurHumain"){
                w_f.println(i + " " + 1 + " " + jou.getNom() + " " + jou.getScore() + " " + jou.getTuiles() + " " + jou.getPions().size() + sauv_data);
            }else{
                w_f.println(i + " " + 1 + " " + jou.getDifficulte() + " " + jou.getScore() + " " + jou.getTuiles() + " " + jou.getPions().size() + sauv_data);
            }
            sauv_data = "";
        }

        // Sauvegarde le plateau a l'instant de la sauvegarde
        w_f.print(plateau);


        //On creer le String de data de sauvegarde (i.e la liste des coup réaliser)
        while (!passe.empty()) {
            elem_hist = passe.pop();
            sauv_data += elem_hist.getSaveString() + " ";
        }
        w_f.println(sauv_data);
        w_f.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void charger(String fichier) {
        int test, jou, val;
        HashMap<Coord, Integer> tempHash;
        Coord tempCoord1, tempCoord2;
        Coup tempCoup;
        Joueur tempJoueur[] = new Joueur[NB_JOUEUR];

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

                //charge joueur

                //charge plateau
                Coord c = new Coord();
                for (c.r = 0; c.r < TAILLE_PLATEAU_X; c.r++){
                    for (c.q = 0; c.q < TAILLE_PLATEAU_Y; c.q++){
                        p.set(c, sc_f.nextInt());
                    }
                }
                test = sc_f.nextInt();
            while (sc_f.hasNext()) {
                // repére le type du coup
                switch (test) {
                    case -1:
                        jou = sc_f.nextInt();
                        tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        tempCoup = new CoupAjout(tempCoord1, jou);
                        future.push(tempCoup);
                        break;
                    case -2:
                        jou = sc_f.nextInt();
                        tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        tempCoord2 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                        val = sc_f.nextInt();
                        tempCoup = new CoupDeplacement(tempCoord1, tempCoord2, val, jou);
                        future.push(tempCoup);
                        break;
                    case -3:
                        jou = sc_f.nextInt();
                        tempHash = new HashMap<>();
                        for (int j = 0; j < nbPions; j++) {
                            tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                            tempHash.put(tempCoord1, sc_f.nextInt());
                        }
                        tempCoup = new CoupTerminaison(tempHash, jou);
                        future.push(tempCoup);
                        break;
                    default:
                        throw new Exception();
                }
            }
            while(future.empty()){
                passe.push(future.pop());
            }
        } catch (Exception E) {
            System.out.println(fichier + " isn't a save file");
            return;
        }

        sc_f.close();

    }
}