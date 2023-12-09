package application;

import modele.*;

public class Configuration {

    public static Pioche nouvellePioche(){
        Pioche pioche = new Pioche();

        for (int i = 0; i < 6; i++) {
            pioche.ajouter(new Quartier("Temple", "RELIGIEUX", 1));
        }

        for (int i = 0; i < 8; i++) {
            pioche.ajouter(new Quartier("Caserne", "MILITAIRE", 2));
        }

        for (int i = 0; i < 6; i++) {
            pioche.ajouter(new Quartier("Château", "NOBLE", 5));
        }

        for (int i = 0; i < 10; i++) {
            pioche.ajouter(new Quartier("Échoppe", "COMMERCANT", 2));
        }

        //TODO revoir pioche et rajouter merveille

        pioche.melanger();
        return pioche;
    };

    public static PlateauDeJeu configurationDeBase(Pioche pioche){
        PlateauDeJeu plateau = new PlateauDeJeu();

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

}
