package IHM.Graphique.Animations;

import IHM.Graphique.Ecrans.Ecran;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;

public abstract class Animation extends SwingWorker<Void, Void> {
    protected final IHMGraphique ihm;
    protected int DURATION = 3000;

    public Animation(int duration, IHMGraphique ihm) {
        super();
        this.DURATION = duration;
        this.ihm = ihm;
    }

    public int getDuration() {
        return DURATION;
    }

    public void begin() {
        Ecran.clickEnable = false;
    }

    public abstract void play();

    public void end() {
        Ecran.clickEnable = true;
    }

    @Override
    protected Void doInBackground() throws Exception {
        System.out.println("On est parti!");
        begin();
        play();
        end();
        return null;
    }

    @Override
    protected void done() {
        super.done();
        ihm.updateAffichage(ihm.getMoteurJeu().getJeu());
    }
}
