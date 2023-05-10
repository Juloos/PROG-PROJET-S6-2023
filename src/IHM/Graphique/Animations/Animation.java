package IHM.Graphique.Animations;

public interface Animation {

    void start();

    void play(int frameNumber);

    void stop();

    void play();

    void pause();

    void resume();
}
