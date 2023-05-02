package IHM;

import java.awt.*;

public class EcranCreationPartie extends Ecran {

    public EcranCreationPartie() {
        super("Creation partie");
    }

    @Override
    public void open(IHMGraphique ihm) {
        super.open(ihm);
        panel.setLayout(new BorderLayout());

        panel.add(retour, BorderLayout.PAGE_END);
    }
}
