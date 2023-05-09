package IHM.Graphique.Animations;

import IHM.Graphique.IHMGraphique;

public abstract class Animation {

    final double DURATION;
    final int nbFrames;
    final IHMGraphique ihm;

    public Animation(IHMGraphique ihm, double duration, int nbFrames) {
        this.ihm = ihm;
        this.DURATION = duration;
        this.nbFrames = nbFrames;
    }

    abstract void start();

    abstract void play(int frameNumber);

    abstract void stop();

    public void run() {
        double TIME_BETWEEN_FRAMES = DURATION / nbFrames;
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
