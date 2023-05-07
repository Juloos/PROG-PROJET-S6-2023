package IHM.Graphique.Animations;

import IHM.Graphique.IHMGraphique;

public abstract class Animation {

    final double TIME_BETWEEN_FRAMES;
    IHMGraphique ihm;
    int nbFrames;

    public Animation(IHMGraphique ihm, double timeBetweenFrame, int nbFrames) {
        this.ihm = ihm;
        this.TIME_BETWEEN_FRAMES = timeBetweenFrame;
        this.nbFrames = nbFrames;
    }

    abstract void start();

    abstract void play(int frameNumber);

    abstract void stop();

    public void run() {
        start();
        for (int i = 0; i < nbFrames; i++) {
            try {
                Thread.sleep((int) (TIME_BETWEEN_FRAMES * 1000.0));
                play(i);
                ihm.getPlateauGraphique().repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stop();
    }
}
