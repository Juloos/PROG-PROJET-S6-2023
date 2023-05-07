import Controleur.MoteurJeu;

public class TestIHM {

    public static void main(String[] args) {
        MoteurJeu moteurJeu = new MoteurJeu();
        moteurJeu.start();

        try {
            moteurJeu.join();
            moteurJeu.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        IHM ihm = new IHMGraphique(null);
    }
}
