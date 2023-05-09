import Controleur.MoteurJeu;

public class TestIHM {

    public static void main(String[] args) {
        MoteurJeu moteurJeu = new MoteurJeu();
        moteurJeu.start();

        try {
            moteurJeu.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.exit(0);
    }
}
