package application;

import controleur.Interaction;
import modele.*;

import java.util.*;

public class Jeu {
    private PlateauDeJeu plateauDeJeu;
    private int numeroConfiguration;
    private Random generateur;

    public Jeu(){plateauDeJeu = new PlateauDeJeu(false);}

    public boolean estEnLigne;
    private Joueur premierATerminer;
    private Server server = new Server();



    public void jouer(){
        int choice;

        System.out.println("Bienvenue dans citadelle !");
        System.out.println("Que souhaitez-vous faire");

        System.out.println("______________");
        System.out.println("[1]: Jouer une partie solo");
        System.out.println("[2]: Heberger une partie en ligne");
        System.out.println("[3]: Rejoindre une partie");
        System.out.println("[4]: Voir les règles");
        System.out.println("[5]: Quitter");
        System.out.println("______________");

        choice = Interaction.lireUnEntier(1,4);

        switch (choice){
            case 1:
                jouerPartie();
                break;
            case 2:
                hebergerPartie();
                break;
            case 3:
                rejoindrePartie();
                break;
            case 4:
                afficherLesRegles();
                break;
            case 5:
                quitterJeu();
                break;
        }
    }

    private void hebergerPartie() {
        int port = 6666;

        estEnLigne = !estEnLigne;

        new Thread(() -> {
            try {
                server.start(port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Client hostClient = new Client();
        System.out.println("Entrez votre nom de joueur : ");
        Scanner sc  = new Scanner(System.in);
        String nomJoueur = sc.nextLine();


        try {
            new Thread(() -> {
                try {
                    hostClient.startConnection("localhost", port, nomJoueur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        plateauDeJeu.setJeuEnLigne();
        jouerPartie();
    }

    private void rejoindrePartie() {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez votre nom de joueur : ");
        String nomJoueur = scanner.nextLine();

        System.out.println("Entrez l'adresse IP du serveur pour rejoindre une partie : ");
        String adresseIP = scanner.nextLine();

        int port = 6666;
        System.out.println("Connexion au serveur " + adresseIP + " sur le port " + port);

        try {
            new Thread(() -> {
                try {
                    client.startConnection("192.168.1.13", port, nomJoueur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
        initialisation();

        if (estEnLigne) {
            server.sendMessageToAll("server", "C'est partie !", "Jeu: ");
        } else {
            System.out.println("C'est partie !");
        }

        int numeroTour = 1;
        do {
            if (estEnLigne) {
                server.sendMessageToAll("server", "Tour numéro "+numeroTour, "Jeu: ");
            } else {
                System.out.println("Tour numéro "+numeroTour);
            }
            tourDeJeu();
            gestionCouronne();
            System.out.println("Fin du tour");
            numeroTour++;
        }
        while (!partieFinie());
        calculDesPoints();
        gestionCouronne();
    };
    private void initialisation(){
        Pioche pioche = Configuration.nouvellePioche();
        String nom;
        this.generateur = new Random();

        this.plateauDeJeu = this.plateauDeJeu.getJeuEnLigne() ? Configuration.configurationOnline(pioche, server) : Configuration.configurationDeBase(pioche);

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
        Joueur joueurRoiAssassine = null;

        for (int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++) {
            if (this.plateauDeJeu.getJoueur(i).getPossedeCouronne()) {
                joueurRoi = this.plateauDeJeu.getJoueur(i);
            }
            if (this.plateauDeJeu.getJoueur(i).getMonPersonnage() != null) {
                if (this.plateauDeJeu.getJoueur(i).getMonPersonnage().getNom().equals("Roi")) {
                    roiSelection = true;
                    if (this.plateauDeJeu.getJoueur(i).getMonPersonnage().getAssassine()) {
                        joueurRoiAssassine = this.plateauDeJeu.getJoueur(i);
                    }
                }
            }
        }


        if (!roiSelection) {
            if (joueurRoi != null) {
                System.out.println("Personne n'a choisi la classe Roi, la couronne est à " + joueurRoi.getNom() + ".");
            } else {
                System.out.println("La classe Roi n'a pas été sélectionnée, personne ne possède la couronne.");
            }
        } else {

            if (joueurRoiAssassine != null) {
                joueurRoiAssassine.setPossedeCouronne(true);
                System.out.println(joueurRoiAssassine.getNom() + " prend la couronne en tant qu'héritier du Roi assassiné.");
                if (joueurRoi != null) {
                    joueurRoi.setPossedeCouronne(false);
                }
            } else {

                for (int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++) {
                    if (this.plateauDeJeu.getJoueur(i).getMonPersonnage().getNom().equals("Roi")) {
                        System.out.println("\t\n" + this.plateauDeJeu.getJoueur(i).getNom() + " possède la couronne\n");
                        this.plateauDeJeu.getJoueur(i).setPossedeCouronne(true);
                        if (joueurRoi != null && joueurRoi != this.plateauDeJeu.getJoueur(i)) {
                            joueurRoi.setPossedeCouronne(false);
                        }
                    }
                }
            }
        }
    }

    private void reinitialisationPersonnages(){};
    private void tourDeJeu(){
        choixPersonnages();

        List<Joueur> joueursTries = new ArrayList<>();
        for (int i = 0; i < plateauDeJeu.getNombreJoueurs(); i++) {
            Joueur joueur = plateauDeJeu.getJoueur(i);
            if (joueur != null && joueur.getMonPersonnage() != null) {
                joueursTries.add(joueur);
            }
        }
        joueursTries.sort(Comparator.comparingInt(j -> j.getMonPersonnage().getRang()));


        for (int i = 0; i < joueursTries.size(); i++) {
            Joueur joueurRoiAssassine = null;

            Joueur joueurActuel = joueursTries.get(i);
            if (joueurActuel.getMonPersonnage() instanceof Roi && joueurActuel.getMonPersonnage().getAssassine()) {
                joueurRoiAssassine = joueurActuel;
                System.out.println("Le Roi a été assassiné et passe son tour.");
                continue;
            }

            if (!joueurActuel.getMonPersonnage().getAssassine()) {
                System.out.print("C'est au tour de " + joueurActuel.getNom() + " de jouer avec " + joueurActuel.getMonPersonnage().getNom());

                if (joueurActuel.getMonPersonnage().getVole()) {
                    int nbPiecesJoueur = joueurActuel.nbPieces();
                    for (int j = 0; j<this.plateauDeJeu.getNombreJoueurs(); j++) {
                        if (this.plateauDeJeu.getJoueur(j).getMonPersonnage().getNom().equals("Voleur")) {
                            this.plateauDeJeu.getJoueur(j).ajouterPieces(nbPiecesJoueur);
                        }
                    }
                    joueurActuel.retirerPieces(nbPiecesJoueur);
                }
                percevoirRessource(joueurActuel);

                joueurActuel.getMonPersonnage().percevoirRessourcesSpecifiques();
                System.out.println("Voulez-vous utiliser votre pouvoir ? \n 1 - oui \n 2 - non");
                int rep = Interaction.lireUnEntier();
                if (rep == 1) {
                    joueurActuel.getMonPersonnage().utiliserPouvoir();
                }
                System.out.println("Voulez-vous construire ? \n 1 - oui \n 2 - non");
                int repConstru = Interaction.lireUnEntier();
                int nbConstru = 1;
                if (joueurActuel.getMonPersonnage().getNom().equals("Architecte")) {
                    nbConstru = 3;
                }
                if (repConstru == 1) {
                    for (int h=0; h<nbConstru; h++) {
                        nbConstru--;
                        int nbQuartiers = joueurActuel.nbQuartiersDansMain()+1;
                        System.out.println("Choisissez un quartier à construire, choisissez "+ nbQuartiers +" pour ne rien construire.");
                        ArrayList<Quartier> listQuartiers = joueurActuel.getMain();
                        for (int l = 0; l<listQuartiers.size();l++) {
                            System.out.println(l + listQuartiers.get(l).getNom());

                        }
                        int choixQuartier = Interaction.lireUnEntier();
                        if (choixQuartier < nbQuartiers && listQuartiers.get(choixQuartier).getCout() < joueurActuel.nbPieces()) {
                            joueurActuel.ajouterQuartierDansCite(listQuartiers.get(choixQuartier));
                            joueurActuel.retirerPieces(listQuartiers.get(choixQuartier).getCout());
                            System.out.println("vous avez construit votre quartier !");
                        } else {
                            System.out.println("Vous n'avez pas assez de pièce !");
                        }

                    }
                }
            }
            else {
                System.out.println("Vous avez été assassiné ! Vous ne jouerez pas ce tour");
            }
            if (joueurRoiAssassine != null) {
                joueurRoiAssassine.setPossedeCouronne(true);
                System.out.println(joueurRoiAssassine.getNom() + " prend la couronne en tant qu'héritier du Roi assassiné.");
            }
        }

    };
    private boolean partieFinie() {
        for (int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++) {
            if (this.plateauDeJeu.getJoueur(i).nbQuartiersDansCite() >= 7) {
                System.out.println("\t\nFin de partie : " + this.plateauDeJeu.getJoueur(i).getNom() + " possède une cité complète de 7 quartiers ou plus. Bravo !\n");
                return true;
            }
        }
        return false;
    }


    private void choixPersonnages(){
        System.out.println("Choix des personnages : ");

        ArrayList<Personnage> listePerso = new ArrayList<>(Arrays.asList(plateauDeJeu.getListePersonnages()));
        Set<Integer> indicesEcartes = new HashSet<>();
        int index;
        int choix = 0;

        while (indicesEcartes.size() < 3) {
            index = generateur.nextInt(plateauDeJeu.getNombrePersonnages());
            indicesEcartes.add(index);
        }

        Iterator<Integer> it = indicesEcartes.iterator();
        String faceCache = plateauDeJeu.getPersonnage(it.next()).getNom();
        String faceVisible1 = plateauDeJeu.getPersonnage(it.next()).getNom();
        String faceVisible2 = plateauDeJeu.getPersonnage(it.next()).getNom();

        System.out.println("Le personnage `" + faceVisible1 + "` est écarté face visible");
        System.out.println("Le personnage `" + faceVisible2 + "` est écarté face visible");
        System.out.println("Un personnage est écarté face cachée");


        int couronne = 0;
        for (int i = 0; i < plateauDeJeu.getNombreJoueurs(); i++) {
            Joueur joueurActuel = plateauDeJeu.getJoueur(i);
            System.out.println(joueurActuel.getNom() + " Choisissez un personnage");

            for (int j = 0; j < listePerso.size(); j++) {
                String nomPerso = listePerso.get(j).getNom();
                if (!nomPerso.equals(faceCache) && !nomPerso.equals(faceVisible1) && !nomPerso.equals(faceVisible2)) {
                    System.out.println(j + " " + nomPerso);
                }
            }
            choix = Interaction.lireUnEntier(0, listePerso.size());
            Personnage persoChoisi = listePerso.get(choix);
            joueurActuel.choisirPersonnage(persoChoisi);
            listePerso.remove(choix);

            System.out.println(joueurActuel.getMonPersonnage().getNom());
        }



    };
    private void percevoirRessource(Joueur joueurActuel){
            Joueur joueur = joueurActuel;

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
    private void calculDesPoints() {
        for (int i = 0; i < plateauDeJeu.getNombreJoueurs(); i++) {
            Joueur joueur = plateauDeJeu.getJoueur(i);
            int score = 0;

            Quartier[] cite = joueur.getCite();
            Set<String> typesDeQuartiers = new HashSet<>();

            for (Quartier quartier : cite) {
                if (quartier != null) {
                    score += quartier.getCout();
                    typesDeQuartiers.add(quartier.getType());
                }
            }

            if (typesDeQuartiers.size() == 5) {
                score += 3;
            }

            if (joueur.nbQuartiersDansCite() >= 7) {
                if (joueur == premierATerminer) {
                    score += 4;
                } else {
                    score += 2;
                }
            }

            System.out.println(joueur.getNom() + " a un score de " + score + " points.");
        }
    }

}
