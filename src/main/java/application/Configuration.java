package application;

import modele.*;

import java.util.List;
import java.util.Set;

public class Configuration {

    // Quartiers Religieux
    private static Quartier temple = new Quartier("temple",Quartier.TYPE_QUARTIERS[0],1);
    private static Quartier eglise = new Quartier("église",Quartier.TYPE_QUARTIERS[0],2);
    private static Quartier monastere = new Quartier("monastère",Quartier.TYPE_QUARTIERS[0],3);
    private static Quartier cathedrale = new Quartier("cathedrale",Quartier.TYPE_QUARTIERS[0],5);

    // Quartiers Militaires
    private static Quartier tourDeGuet = new Quartier("tour de guet",Quartier.TYPE_QUARTIERS[1],1);
    private static Quartier prison = new Quartier("prison",Quartier.TYPE_QUARTIERS[1],2);
    private static Quartier caserne = new Quartier("caserne",Quartier.TYPE_QUARTIERS[1],3);
    private static Quartier forteresse = new Quartier("forteresse",Quartier.TYPE_QUARTIERS[1],5);

    // Quartiers Nobles
    private static Quartier manoir = new Quartier("manoir",Quartier.TYPE_QUARTIERS[2],3);
    private static Quartier chateau = new Quartier("château",Quartier.TYPE_QUARTIERS[2],4);
    private static Quartier palais = new Quartier("palais",Quartier.TYPE_QUARTIERS[2],5);

    // Quartiers Commerçants
    private static Quartier taverne = new Quartier("taverne",Quartier.TYPE_QUARTIERS[3],1);
    private static Quartier echoppe = new Quartier("échoppe",Quartier.TYPE_QUARTIERS[3],2);
    private static Quartier marche = new Quartier("marché",Quartier.TYPE_QUARTIERS[3],2);
    private static Quartier comptoir = new Quartier("comptoir",Quartier.TYPE_QUARTIERS[3],3);
    private static Quartier port = new Quartier("port",Quartier.TYPE_QUARTIERS[3],4);
    private static Quartier hotelDeVille = new Quartier("hôtel de ville",Quartier.TYPE_QUARTIERS[3],5);

    public static Pioche nouvellePioche(){
        Pioche pioche = new Pioche();

        for (int i = 0; i < 2; i++) {
            pioche.ajouter(cathedrale);
            pioche.ajouter(forteresse);
            pioche.ajouter(hotelDeVille);
        }

        for (int i = 0; i < 3; i++) {
            pioche.ajouter(temple);
            pioche.ajouter(eglise);
            pioche.ajouter(monastere);
            pioche.ajouter(tourDeGuet);
            pioche.ajouter(prison);
            pioche.ajouter(caserne);
            pioche.ajouter(palais);
            pioche.ajouter(echoppe);
            pioche.ajouter(comptoir);
            pioche.ajouter(port);
        }

        for (int i = 0; i < 4; i++) {
            pioche.ajouter(chateau);
            pioche.ajouter(marche);
        }

        for (int i = 0; i < 5; i++) {
            pioche.ajouter(manoir);
            pioche.ajouter(taverne);
        }

        pioche.melanger();
        return pioche;
    };

    public static PlateauDeJeu configurationDeBase(Pioche pioche){
        PlateauDeJeu plateau = new PlateauDeJeu(false);

        plateau.ajouterPersonnage(new Assassin());
        plateau.ajouterPersonnage(new Condottiere());
        plateau.ajouterPersonnage(new Architecte());
        plateau.ajouterPersonnage(new Eveque());
        plateau.ajouterPersonnage(new Magicienne());
        plateau.ajouterPersonnage(new Marchande());
        plateau.ajouterPersonnage(new Roi());
        plateau.ajouterPersonnage(new Voleur());

        plateau.ajouterJoueur(new Joueur("Baron Valdemar"));
        plateau.ajouterJoueur(new Joueur("Marquis de Luneville"));
        plateau.ajouterJoueur(new Joueur("Dame Seraphina"));
        plateau.ajouterJoueur(new Joueur("Maître Corbin"));

        plateau.setPioche(pioche);

        return plateau;
    };

    public static PlateauDeJeu configurationOnline(Pioche pioche, Server server){
        PlateauDeJeu plateau = new PlateauDeJeu(true);

        plateau.ajouterPersonnage(new Assassin());
        plateau.ajouterPersonnage(new Condottiere());
        plateau.ajouterPersonnage(new Architecte());
        plateau.ajouterPersonnage(new Eveque());
        plateau.ajouterPersonnage(new Magicienne());
        plateau.ajouterPersonnage(new Marchande());
        plateau.ajouterPersonnage(new Roi());
        plateau.ajouterPersonnage(new Voleur());

        Set<String> clients = server.getClients();
        for (String client : clients) {
            plateau.ajouterJoueur(new Joueur(client));
        }

        plateau.setPioche(pioche);

        return plateau;
    };
}
