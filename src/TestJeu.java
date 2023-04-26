import Modele.*;
import java.util.*;
public class TestJeu {
    public static void main(String[] args){
        JeuGraphe j = new JeuGraphe();
        Scanner sc = new Scanner(System.in);
        Coord c1 = new Coord();
        Coord c2 = new Coord();
        Coord c3 = new Coord();
        Coord c4 = new Coord();
        System.out.println("J1 : Entrez coordonnée pion 1 : ");
        c1.q = sc.nextInt();
        c1.r = sc.nextInt();
        System.out.println(c1);
        j.ajouterPion(c1);
        System.out.println("J2 : Entrez coordonnée pion 1 : ");
        c2.q = sc.nextInt();
        c2.r = sc.nextInt();
        System.out.println(c2);
        j.ajouterPion(c2);
        System.out.println("J1 : Entrez coordonnée pion 2 : ");
        c3.q = sc.nextInt();
        c3.r = sc.nextInt();
        System.out.println(c3);
        j.ajouterPion(c3);
        System.out.println("J2 : Entrez coordonnée pion 2 : ");
        c4.q = sc.nextInt();
        c4.r = sc.nextInt();
        System.out.println(c4);
        j.ajouterPion(c4);
        System.out.println(j.plateau);
    }
}
