package IHM.Console;

import Controleur.MoteurJeu;
import Global.Config;
import IHM.IHM;
import Modele.Actions.*;
import Modele.Coord;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Jeu.Jeu;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;

import java.util.ArrayList;
import java.util.List;
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
        int numJoueur = getMoteurJeu().getJoueurActif().id;
        Action action = null;

        do {
            System.out.println("Joueur " + (numJoueur + 1) + " quelle action voulez-vous faire (jouer, annuler, refaire, sauvegarder) ?");
            String actionSouhaitee = input.nextLine();

            try {
                switch (actionSouhaitee.toLowerCase()) {
                    case "jouer":
                        if (getMoteurJeu().estPhasePlacementPions()) {
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
        } while (action == null || !action.peutAppliquer(getMoteurJeu()));

        return action;
    }

    /**
     * Attend que le joueur actif choisisse une tuile sur laquelle placer un de ses pions
     *
     * @return l'action de placement du pion
     */
    private ActionCoup attendrePlacementPion() {
        int numJoueur = getMoteurJeu().getJoueurActif().id;

        System.out.println("Veuillez placez un pingouin");
        String ligne = input.nextLine();
        int[] valeurs = decouperLigne(ligne);

        Coord coord = new Coord(valeurs[0], valeurs[1]);
        return new ActionCoup(new CoupAjout(coord, numJoueur));
    }

    /**
     * Attend que le joueur actif choisisse un pion à déplacer et sur quelle tuile il veut le déplacer
     *
     * @return l'action de déplacement du pion
     */
    private ActionCoup attendreDeplacementPion() {
        int numJoueur = getMoteurJeu().getJoueurActif().id;
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

    @Override
    public void attendreCreationPartie() {
        List<Joueur> listJoueurs = new ArrayList<>();

        String ligne;
        do {
            ligne = input.next();

            try {
                switch (ligne.toLowerCase()) {
                    case "humain":
                        // On ajoute un joueur humain
                        listJoueurs.add(new JoueurHumain(listJoueurs.size()));
                        break;
                    case "ia":
                        // On ajoute un joueur IA
                        // La difficulté de l'IA
//                        String difficulte = ligne.split(" ")[1];
                        listJoueurs.add(new JoueurIA(listJoueurs.size()));
                    case "fin":
                        break;
                    default:
                        System.out.println("Mais t'es con ou quoi fréro ?");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Mais t'es con ou quoi fréro ?");
            }
        } while (!ligne.equals("fin") &&
                (!ligne.equals("") || listJoueurs.size() < Config.NB_MIN_JOUEUR) &&
                listJoueurs.size() < Config.NB_MAX_JOUEUR);

        if (ligne.equals("fin")) {
            getMoteurJeu().fin();
        } else {
            getMoteurJeu().lancerPartie(listJoueurs.toArray(new Joueur[0]));
        }
    }

    @Override
    public void terminer() {
        input.close();
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
