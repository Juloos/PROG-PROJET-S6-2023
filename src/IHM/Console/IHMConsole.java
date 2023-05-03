package IHM.Console;

import Controlleur.MoteurJeu;
import IHM.Actions.ActionIHM;
import IHM.Actions.ActionJouerCoup;
import IHM.IHM;
import Modele.Coord;
import Modele.CoupAjout;
import Modele.CoupDeplacement;
import Modele.Jeu;

import java.util.Scanner;

public class IHMConsole extends IHM {

    private Scanner input;

    public IHMConsole(MoteurJeu moteurJeu) {
        super(moteurJeu);
        input = new Scanner(System.in);
    }

    @Override
    public void updateAffichage(Jeu jeu) {
        System.out.println(jeu.toString());
    }

    @Override
    public void attendreActionJoueur() {
        int numJoueur = moteurJeu.getJoueurActif().id;

        System.out.println("Joueur " + (numJoueur + 1) + " quelle action voulez-vous faire (jouer, annuler, refaire, sauvegarder) ?");

        String actionSouhaitee = input.nextLine();

        switch (actionSouhaitee.toLowerCase()) {
            case "jouer":
                if (moteurJeu.estPhasePlacementPions()) {
                    attendrePlacementPion();
                } else {
                    attendreDeplacementPion();
                }
                break;
            case "annuler":
                moteurJeu.annulerCoup();
                break;
            case "refaire":
                moteurJeu.refaireCoup();
                break;
            case "sauvegarder":
                moteurJeu.sauvegarder();
                break;
            default:
                System.out.println("Mais t'es con ou quoi fréro ?");
                break;
        }
    }

    private void attendrePlacementPion() {
        int numJoueur = moteurJeu.getJoueurActif().id;

        System.out.println("Veuillez placez un pingouin");
        String ligne = input.nextLine();
        int[] valeurs = decouperLigne(ligne);

        Coord coord = new Coord(valeurs[0], valeurs[1]);
        ActionIHM action = new ActionJouerCoup(new CoupAjout(coord, numJoueur));
        action.action(moteurJeu);
    }

    private void attendreDeplacementPion() {
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

        ActionIHM action = new ActionJouerCoup(new CoupDeplacement(source, destination, numJoueur));
        action.action(moteurJeu);
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
