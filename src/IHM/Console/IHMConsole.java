package IHM.Console;

import Controleur.MoteurJeu;
import IHM.IHM;
import Modele.Actions.*;
import Modele.Coord;
import Modele.Coups.CoupAjout;
import Modele.Coups.CoupDeplacement;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Global.Config.NB_MAX_JOUEUR;
import static Global.Config.NB_MIN_JOUEUR;

public class IHMConsole extends IHM {

    private final Scanner input;

    public IHMConsole(MoteurJeu moteurJeu) {
        super(moteurJeu);
        input = new Scanner(System.in);
    }

    @Override
    public void updateAffichage(boolean jouerAnimation) {
        System.out.println(moteurJeu.getJeu().toString() + "\n");
        moteurJeu.finUpdateAffichage();
    }

    @Override
    public void attendreActionJoueur(JoueurHumain joueur) {
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

        joueur.setAction(moteurJeu, action);
    }

    /**
     * Attend que le joueur actif choisisse une tuile sur laquelle placer un de ses pions
     *
     * @return l'action de placement du pion
     */
    private ActionCoup attendrePlacementPion() {
        int numJoueur = getMoteurJeu().getJoueurActif().id;

        System.out.println("Veuillez placez un pingouin (colonne ligne)");
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
        System.out.println("Source (colonne ligne)");
        String ligne = input.nextLine();
        int[] valeurs = decouperLigne(ligne);

        Coord source = new Coord(valeurs[0], valeurs[1]);

        // Destination
        System.out.println("Destination (colonne ligne)");
        ligne = input.nextLine();
        valeurs = decouperLigne(ligne);

        Coord destination = new Coord(valeurs[0], valeurs[1]);

        return new ActionCoup(new CoupDeplacement(source, destination, numJoueur));
    }

    @Override
    public void afficherMessage(String message, int duration) {
        System.out.println(message);
    }

    @Override
    public synchronized void attendreCreationPartie() {
        String ligne = "";
        boolean choix = false;
        boolean creationTerminee = false;

        do {
            do {
                System.out.println("Choix possibles : \n" +
                        "choisir -> créer une partie en choissant les joueurs" +
                        "\ncharger -> charger une partie depuis un fichier de sauvegarde" +
                        "\nstop -> quitter le programme");
                ligne = input.nextLine();
                ligne = ligne.toLowerCase();

                choix = ligne.equals("stop") || ligne.equals("charger") || ligne.equals("choisir");
            } while (!choix);

            switch (ligne) {
                case "charger":
                    // Chargement depuis un fichier de sauvegardes
                    do {
                        System.out.println("Entrez le nom de la sauvegarde que vous voulez");
                        ligne = input.nextLine();
                    } while (!ligne.equals("retour") && !ligne.equals(""));

                    if (!ligne.equals("retour")) {
                        getMoteurJeu().lancerPartie(ligne);
                        creationTerminee = true;
                    }
                    break;
                case "choisir":
                    // Choix des types des joueurs
                    List<Joueur> joueurs = new ArrayList<>();
                    do {
                        System.out.println("Entrez le type du joueur n°" + (joueurs.size() + 1) + " (humain ou ia ou ligne vide pour arrêter)");
                        ligne = input.nextLine();
                        ligne = ligne.toLowerCase();

                        switch (ligne) {
                            case "humain":
                                joueurs.add(new JoueurHumain(joueurs.size()));
                                break;
                            case "ia":
                                joueurs.add(new JoueurIA(joueurs.size()));
                                break;
                        }

                    } while (!ligne.equals("retour") &&
                            (!ligne.equals("") || joueurs.size() < NB_MIN_JOUEUR) &&
                            joueurs.size() < NB_MAX_JOUEUR);

                    if (!ligne.equals("retour")) {
                        getMoteurJeu().lancerPartie(joueurs.toArray(new Joueur[0]));
                        creationTerminee = true;
                    }
                    break;
                case "stop":
                    // Arrêt du moteur de jeu
                    getMoteurJeu().terminer();
                    creationTerminee = true;
                    break;
                default:
                    break;
            }
        } while (!creationTerminee);
    }

    @Override
    public void debutDePartie() {

    }

    @Override
    public void finDePartie() {

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
}
