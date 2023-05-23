package IHM.Graphique.Animations;

import IHM.Graphique.Composants.PlateauGraphique;
import IHM.Graphique.Couleurs;
import IHM.Graphique.IHMGraphique;
import Modele.Coord;
import Modele.Coups.CoupDeplacement;

/**
 * Classe de l'animation lorsq'un pingouin est déplacé
 */
public class AnimationDeplacementPion extends Animation {
    private final PlateauGraphique plateauGraphique;
    private final CoupDeplacement deplacement;

    public AnimationDeplacementPion(IHMGraphique ihm, CoupDeplacement deplacement) {
        super(750, ihm);
        this.plateauGraphique = ihm.getPlateauGraphique();
        this.deplacement = deplacement;
    }

    /* Méthodes héritées */

    @Override
    public void begin() {
        super.begin();
    }

    @Override
    public void end() {
        plateauGraphique.viderTuilesSurbrillance();
        super.end();
    }

    @Override
    public void play() {
        plateauGraphique.ajouterTuilesSurbrillance(Coord.getCoordsEntre(deplacement.source, deplacement.destination), Couleurs.SURBRILLANCE);

        try {
            Thread.sleep(DURATION);
        } catch (InterruptedException e) {
        }
    }
}
