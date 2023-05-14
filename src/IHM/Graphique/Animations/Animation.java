package IHM.Graphique.Animations;

public interface Animation extends Runnable {

    void start();

    void play(int frameNumber);

    void stop();

    void pause();

    void resume();
}
