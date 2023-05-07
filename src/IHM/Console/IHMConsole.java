package IHM.Console;

import Controleur.MoteurJeu;
import IHM.IHM;
import Modele.Actions.*;
import Modele.Coord;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Jeu.Jeu;

import java.util.Scanner;

public class IHMConsole extends IHM {

    private final Scanner input;

    public IHMConsole(MoteurJeu moteurJeu) {
        super(moteurJeu);
        input = new Scanner(System.in);
    }

    @Override
    public void updateAffichage(Jeu jeu) {
        System.out.println(jeu.toString());
    }

    @Override
    public Action attendreActionJoueur() {
        int numJoueur = moteurJeu.getJoueurActif().id;
        Action action = null;

        do {
            System.out.println("Joueur " + (numJoueur + 1) + " quelle action voulez-vous faire (jouer, annuler, refaire, sauvegarder) ?");
            String actionSouhaitee = input.nextLine();

            try {
                switch (actionSouhaitee.toLowerCase()) {
                    case "jouer":
                        if (moteurJeu.estPhasePlacementPions()) {
                            action = attendrePlacementPion();
                        } else {
                            action = attendreDeplacementPion();
                        }
                        break;
                    case "annuler":
                        action = new ActionAnnuler();
                        break;
                    case "refaire":
                        action = new ActionRefaire();
                        break;
                    case "sauvegarder":
                        action = new ActionSauvegarder("test");
                        break;
                    default:
                        System.out.println("Mais t'es con ou quoi fréro ?");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Mais t'es con ou quoi fréro ?");
                action = null;
            }
        } while (action == null || !action.peutAppliquer(moteurJeu));

        return action;
    }

    private Action attendrePlacementPion() {
        int numJoueur = moteurJeu.getJoueurActif().id;

        System.out.println("Veuillez placez un pingouin");
        String ligne = input.nextLine();
        int[] valeurs = decouperLigne(ligne);

        Coord coord = new Coord(valeurs[0], valeurs[1]);
        return new ActionCoup(new CoupAjout(coord, numJoueur));
    }

    private Action attendreDeplacementPion() {
        int numJoueur = moteurJeu.getJoueurActif().id;
        System.out.println("Veuillez déplacez un pingouin (source puis destination)");
        // Source
        String ligne = input.nextLine();
        int[] valeurs = decouperLigne(ligne);

        Coord source = new Coord(valeurs[0], valeurs[1]);

        // Destination
        ligne = input.nextLine();
        valeurs = decouperLigne(ligne);

        Coord destination = new Coord(valeurs[0], valeurs[1]);

        return new ActionCoup(new CoupDeplacement(source, destination, numJoueur));
    }

    @Override
    public void afficherMessage(String message) {
        System.out.println(message);
    }
    
    private int[] decouperLigne(String ligne) {
        String[] ligne_split = ligne.split(" ");
        int[] valeurs = new int[ligne_split.length];

        for (int i = 0; i < ligne_split.length; i++) {
            valeurs[i] = Integer.parseInt(ligne_split[i]);
        }

        return valeurs;
    }

    @Override
    public void run() {

    }
}
