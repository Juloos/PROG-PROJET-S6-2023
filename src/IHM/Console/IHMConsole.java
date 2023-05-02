package IHM.Console;

import IHM.IHM;
import Modele.Coord;
import Modele.CoupAjout;
import Modele.CoupDeplacement;
import Modele.Jeu;

import java.util.Scanner;

public class IHMConsole extends IHM {

    private Scanner input;

    public IHMConsole() {
        input = new Scanner(System.in);
    }

    @Override
    public void AfficherPlateau(Jeu jeu) {
        System.out.println(jeu.toString());
    }

    @Override
    public CoupAjout placerPingouin(int numJoueur) {
        System.out.println("Joueur " + (numJoueur + 1) + " placez un pingouin");
        String ligne = input.nextLine();
        int[] valeurs = decouperLigne(ligne);

        Coord coord = new Coord(valeurs[0], valeurs[1]);
        return new CoupAjout(coord, numJoueur);
    }

    @Override
    public CoupDeplacement deplacerPingouin(int numJoueur) {
        System.out.println("Joueur " + (numJoueur + 1) + " déplacez un pingouin (source puis destination)");
        // Source
        String ligne = input.nextLine();
        int[] valeurs = decouperLigne(ligne);

        Coord source = new Coord(valeurs[0], valeurs[1]);

        // Destination
        ligne = input.nextLine();
        valeurs = decouperLigne(ligne);

        Coord destination = new Coord(valeurs[0], valeurs[1]);

        return new CoupDeplacement(source, destination, numJoueur);
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
