package IHM.Graphique.Animations;

import IHM.Graphique.IHMGraphique;

public abstract class Animation {

    IHMGraphique ihm;

    double duration;

    int nbFrames;

    public Animation(IHMGraphique ihm, double duration, int nbFrames) {
        this.ihm = ihm;
        this.duration = duration;
        this.nbFrames = nbFrames;
    }

    abstract void start();

    abstract void play(int frameNumber);

    abstract void stop();

    public void run() {
        final int TIME_BETWEEN_FRAMES = (int) (duration * 1000.0 / nbFrames);

        start();
        for (int i = 0; i < nbFrames; i++) {
            try {
                Thread.sleep(TIME_BETWEEN_FRAMES);
                play(i);
                ihm.getPlateauGraphique().repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stop();
    }
}
