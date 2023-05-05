import Controleur.MoteurJeu;

public class TestIHM {

    public static void main(String[] args) {
        MoteurJeu moteurJeu = new MoteurJeu();
        Thread thread = new Thread(moteurJeu);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        IHM ihm = new IHMGraphique(null);
    }
}
