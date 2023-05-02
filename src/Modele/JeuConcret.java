package Modele;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import static Global.Config.NB_JOUEUR;
import static Global.Config.NB_PIONS;

public class JeuConcret extends Jeu {
    Stack<Coup> passe;
    Stack<Coup> future;

    public JeuConcret() {
        super();
        passe = new Stack<>();
        future = new Stack<>();
    }

    public void jouer(Coup c) {
        super.jouer(c);
        passe.push(c);
        future.clear();  // on doit vider la pile si l'on fait de nouveau coup après avoir reculer
    }

    public void annuler(Jeu j) {
        Coup c = passe.pop();
        c.annuler(j);
        future.push(c);
    }
    public void refaire() {
        Coup c = future.pop();
        super.jouer(c);
        passe.push(c);
    }
    public void sauvegarder(String fichier) throws Exception{
        // Init fichier
        File f = new File(fichier);
        f.setReadable(true);
        f.setWritable(true);
        PrintWriter w_f = new PrintWriter(f);

        // Init var
        String sauv_data = "";
        Coup elem_hist;

        // Sauvegarde le plateau a l'instant de la sauvegarde
        w_f.print(super.getPlateau().toString());

        // Sauvegarde les a l'instant de la sauvegarde
        for(int i = 0; i<NB_JOUEUR; i++){
            Joueur jou  = super.getJoueur(0);
            Coord[] tempL = jou.getPions().toArray(new Coord[NB_PIONS]);
            for(int j = 0; j<NB_PIONS; j++){
                sauv_data +=  " "+tempL[j].q+" "+tempL[j].r ;
            }
            w_f.println(i+" "+jou.getScore()+" "+jou.getTuiles()+sauv_data);
        }

        sauv_data = "";
        //On creer le String de data de sauvegarde (i.e la liste des coup réaliser)
        while(!passe.empty()){
            elem_hist = passe.pop();
            sauv_data += elem_hist.getSaveString() + " ";
        }
        w_f.println(sauv_data);
        w_f.close();
    }

    public void charger(String fichier) {
        int test, jou, val;
        HashMap<Coord,Integer> tempHash;
        Coord tempCoord1, tempCoord2;
        Coup tempCoup;

        Scanner sc_f;

        // Init fichier
        try {
            sc_f = new Scanner(new File(fichier));
        }catch (Exception E){
            System.out.println(fichier + " isn't accesible");
            return;
        }

        // Tant que le fichier n'est pas vide, vérifie que le fichier est comforme et joue si il trouve un coup
        try {
            while(sc_f.hasNext()) {
                //sauve plateau
                //sauve joueur
                test = sc_f.nextInt();
                // repére le type du coup
                switch (test){
                    case -1 :
                        jou = sc_f.nextInt();
                        tempCoord1 = new Coord(sc_f.nextInt(),sc_f.nextInt());
                        tempCoup = new CoupAjout(tempCoord1, jou);
                        passe.push(tempCoup);
                        break;
                    case -2 :
                        jou = sc_f.nextInt();
                        tempCoord1 = new Coord(sc_f.nextInt(),sc_f.nextInt());
                        tempCoord2 = new Coord(sc_f.nextInt(),sc_f.nextInt());
                        val = sc_f.nextInt();
                        tempCoup = new CoupDeplacement(tempCoord1, tempCoord2, val, jou);
                        passe.push(tempCoup);
                        break;
                    case -3 :
                        jou = sc_f.nextInt();
                        tempHash = new HashMap<>();
                        for(int j = 0; j<NB_PIONS; j++) {
                            tempCoord1 = new Coord(sc_f.nextInt(), sc_f.nextInt());
                            tempHash.put(tempCoord1, sc_f.nextInt());
                        }
                        tempCoup = new CoupTerminaison(tempHash, jou);
                        passe.push(tempCoup);
                        break;
                    default :
                        throw new Exception();
                }
            }
        }catch (Exception E) {
            System.out.println(fichier + " isn't a save file");
            return;
        }

        sc_f.close();

    }
}