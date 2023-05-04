package IHM.Console;

import Controleur.MoteurJeu;
import IHM.IHM;
import Modele.*;

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

        System.out.println("Joueur " + (numJoueur + 1) + " quelle action voulez-vous faire (jouer, annuler, refaire, sauvegarder) ?");

        do {
            String actionSouhaitee = input.nextLine();

            switch (actionSouhaitee.toLowerCase()) {
                case "jouer":
                    if (moteurJeu.estPhasePlacementPions()) {
                        return attendrePlacementPion();
                    } else {
                        return attendreDeplacementPion();
                    }
                case "annuler":
                    return new ActionAnnuler();
                case "refaire":
                    return new ActionRefaire();
                case "sauvegarder":
                    return new ActionSauvegarder();
                default:
                    System.out.println("Mais t'es con ou quoi fréro ?");
                    break;
            }
        } while (true);
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
}
