package IHM.Graphique.Animations;

import IHM.Graphique.Ecrans.Ecran;
import IHM.Graphique.IHMGraphique;

import javax.swing.*;

/**
 * La classe abstraite que chaque classe d'animation étend
 */
public abstract class Animation extends SwingWorker<Void, Void> {
    protected final IHMGraphique ihm;
    protected int DURATION;

    public Animation(int duration, IHMGraphique ihm) {
        super();
        this.DURATION = duration;
        this.ihm = ihm;
    }

    /**
     * Méthode appelée avant que l'animation se lance
     */
    public void begin() {
        Ecran.clickEnable = false;
    }

    /**
     * Contient l'animation en elle même
     */
    public abstract void play();

    /**
     * Méthode appelée une fois que l'animation est terminée
     */
    public void end() {
        Ecran.clickEnable = true;
    }

    /**
     * Méthode appelée par le thread qui gère l'animation
     */
    @Override
    protected Void doInBackground() {
        begin();
        play();
        end();
        return null;
    }

    /**
     * Méthode appelée une fois que le thread de l'animation est terminée
     */
    @Override
    protected void done() {
        super.done();
        ihm.updateAffichage(false);
    }
}
