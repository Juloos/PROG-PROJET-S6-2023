import Modele.*;

public class TestJeu {
    public static void main(String[] args) {
        JeuConcret j = new JeuConcret();
        JeuGraphe jg = new JeuGraphe(j);
        jg.calculerFils();

        System.out.println(jg + "\n\n");
        for (JeuGraphe jg2 : jg.fils)
            System.out.println(jg2 + "\n");
    }
}
