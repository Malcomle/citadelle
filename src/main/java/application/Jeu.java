package application;

import controleur.Interaction;
import modele.Joueur;
import modele.Pioche;
import modele.PlateauDeJeu;

import java.util.Random;

public class Jeu {
    private PlateauDeJeu plateauDeJeu;
    private int numeroConfiguration;
    private Random generateur;

    public Jeu(){

    }

    public void jouer(){
        int choice;

        System.out.println("Bienvenue dans citadelle !");
        System.out.println("Que souhaitez vous faire");

        System.out.println("______________");
        System.out.println("[1]: Jouer une partie");
        System.out.println("[2]: Voir les regles");
        System.out.println("[3]: Quitter");
        System.out.println("______________");

        choice = Interaction.lireUnEntier(1,3);

        switch (choice){
            case 1:
                jouerPartie();
                break;
            case 2:
                afficherLesRegles();
                break;
            case 3:
                System.out.println("À une prochaine fois !");
                break;
        }
    };

    private void afficherLesRegles(){
        //todo Affichage des règles
        System.out.println("Regle");
        jouerPartie();
    };
    private void jouerPartie(){
        System.out.println("C'est partie !");
        initialisation();
        choixPersonnages();
    };
    private void initialisation(){
        Pioche pioche = Configuration.nouvellePioche();
        String nom;
        this.generateur = new Random();

        this.plateauDeJeu = Configuration.configurationDeBase(pioche);

        int r = this.generateur.nextInt(4);
        this.numeroConfiguration = this.generateur.nextInt(4);

        for (int i = 0; i<this.plateauDeJeu.getNombreJoueurs();i++){
            Joueur joueur = this.plateauDeJeu.getJoueur(i);
            joueur.ajouterPieces(2);

            if(i == r){
                joueur.setPossedeCouronne();
            }

            for (int j = 0; j<4;j++) {
                joueur.ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocher());
            }
        }
    };
    private void gestionCouronne(){};
    private void reinitialisationPersonnages(){};
    private boolean partieFinie(){return true;};
    private void tourDeJeu(){};
    private void choixPersonnages(){
        System.out.println("Choix des personnages : ");

    };
    private void percevoirRessource(){};
    private void calculDesPoints(){};
}
