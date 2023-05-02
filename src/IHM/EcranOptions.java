package IHM;

import java.awt.*;

public class EcranOptions extends  Ecran {

    public EcranOptions() {
        super("Options");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);

        panel.add(retour);
    }
}
