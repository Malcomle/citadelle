package application;

import controleur.Interaction;
import modele.Joueur;
import modele.Pioche;
import modele.PlateauDeJeu;
import modele.Quartier;

import java.util.Random;

public class Jeu {
    private PlateauDeJeu plateauDeJeu;
    private int numeroConfiguration;
    private Random generateur;

    public Jeu(){}

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
                quitterJeu();
                break;
        }
    };

    private void afficherLesRegles(){
        //todo Affichage des règles
        System.out.println("Règles du jeu Citadelles:\n");

        System.out.println("1. Préparation: Chaque joueur commence avec deux pièces d'or et quatre cartes de quartier.");
        System.out.println("2. Choix des personnages: En commençant par le roi, les joueurs choisissent un personnage en secret.");
        System.out.println("3. Tour de jeu: Les joueurs jouent dans l'ordre des numéros des personnages.");
        System.out.println("4. Actions: Durant son tour, un joueur peut prendre deux pièces d'or ou piocher deux cartes de quartier (en en gardant une).");
        System.out.println("5. Construction: Un joueur peut construire un quartier en payant le coût en or.");
        System.out.println("6. Pouvoirs des personnages: Chaque personnage a des pouvoirs spéciaux à utiliser durant son tour.");
        System.out.println("7. Fin du jeu: Le jeu se termine quand un joueur construit son huitième quartier.");
        System.out.println("8. Points de victoire: Les joueurs gagnent des points pour leurs quartiers construits, bonus pour le premier qui atteint huit quartiers et pour celui qui a construit des quartiers de toutes les couleurs.\n");

        System.out.println("Bon jeu !");

        jouer();
    };

    private void quitterJeu(){
        System.out.println("À une prochaine fois !");
    }

    private void jouerPartie(){
        System.out.println("C'est partie !");
        initialisation();
        gestionCouronne();
        percevoirRessource();
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
                joueur.setPossedeCouronne(true);
            }

            for (int j = 0; j<4;j++) {
                joueur.ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocher());
            }
        }
    };
    private void gestionCouronne(){
        boolean roiSelection = false;
        Joueur joueurRoi = null;
        for (int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++) {
            if (this.plateauDeJeu.getJoueur(i).getPossedeCouronne()) {
                joueurRoi = this.plateauDeJeu.getJoueur(i);
            }
            if(this.plateauDeJeu.getJoueur(i).getMonPersonnage() != null) {
                if (this.plateauDeJeu.getJoueur(i).getMonPersonnage().getNom().equals("Roi")) {
                    roiSelection = true;
                }
            }
        }
        if (!roiSelection) {
            if (joueurRoi != null) {
                System.out.println("Personne n'a choisi la classe Roi, la couronne est à " + joueurRoi.getNom() + ".");
            } else {
                System.out.println("La classe Roi n'a pas été sélectionnée, personne ne possède la couronne.");
            }
            return;
        }
        if (joueurRoi != null) {
            joueurRoi.setPossedeCouronne(false);
        }
        for (int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++) {
            if (this.plateauDeJeu.getJoueur(i).getMonPersonnage().getNom().equals("Roi")) {
                System.out.println("\t\n" + this.plateauDeJeu.getJoueur(i).getNom() + " possède la couronne\n");
                this.plateauDeJeu.getJoueur(i).setPossedeCouronne(true);
            }
        }


};
    private void reinitialisationPersonnages(){};
    private boolean partieFinie(){return true;};
    private void tourDeJeu(){};
    private void choixPersonnages(){
        System.out.println("Choix des personnages : ");

        int faceCaché1 = generateur.nextInt(8);
        int faceVisible = generateur.nextInt(8);
        int faceVisible2 = generateur.nextInt(8);

        System.out.println("Le personnage `"+plateauDeJeu.getPersonnage(faceVisible).getNom()+"` est écarté face visible");
        System.out.println("Le personnage `"+plateauDeJeu.getPersonnage(faceVisible2).getNom()+"` est écarté face visible");
        System.out.println("Le personnage est écarté face cachée");

        int couronne = 0;
        for (int i = 0; i<4;i++) {
            if(plateauDeJeu.getJoueur(i).getPossedeCouronne()){
                couronne = i;
                if(i == numeroConfiguration){
                    System.out.println("Vous possedez la couronne");
                }else{
                    System.out.println(plateauDeJeu.getJoueur(i).getNom()+" possède la couronne");
                }
            }
        }


    };
    private void percevoirRessource(){
        for (int i = 0; i < plateauDeJeu.getNombreJoueurs(); i++) {
            Joueur joueur = plateauDeJeu.getJoueur(i);

            System.out.println(joueur.getNom() + ", voulez-vous :");
            System.out.println("[1] Prendre deux pièces d'or");
            System.out.println("[2] Piocher deux cartes de la pioche et en garder une");
            int choix = Interaction.lireUnEntier(1, 3);

            if (choix == 1) {
                joueur.ajouterPieces(2);
                System.out.println("Vous avez reçu deux pièces d'or.");
            } else if (choix == 2) {
                Quartier carte1 = plateauDeJeu.getPioche().piocher();
                Quartier carte2 = plateauDeJeu.getPioche().piocher();
                System.out.println("Vous avez pioché deux cartes : " + carte1.getNom() + " et " + carte2.getNom());
                System.out.println("Quelle carte voulez-vous garder ? (1 ou 2)");

                int choixCarte = Interaction.lireUnEntier(1, 3);
                if (choixCarte == 1) {
                    joueur.ajouterQuartierDansMain(carte1);
                } else {
                    joueur.ajouterQuartierDansMain(carte2);
                }
                System.out.println("Vous avez gardé une carte et vous défaussé l'autre.");
            }
        }
    }
    private void calculDesPoints(){};
}
