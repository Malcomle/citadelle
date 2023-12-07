package modele;

import controleur.Interaction;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Assassin extends Personnage {

    public Assassin() {
        super("Assassin", 1, Caracteristiques.ASSASSIN);
    }

    @Override
    public void utiliserPouvoir() {
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();
        int personnageNbChoisi = 0;

        System.out.println("Quel personnage voulez-vous assassiner ?");

        for (int i = 0; i < nbPersonnage; i++) {
            System.out.println((i + 1) + ": " + plateau.getPersonnage(i).getNom());
        }

        boolean continu = true;
        do {
            try {
                personnageNbChoisi = Interaction.lireUnEntier(1, nbPersonnage + 1) - 1;

                if (personnageNbChoisi < 0 || personnageNbChoisi >= nbPersonnage) {
                    throw new InputMismatchException();
                }

                executerAssassinat(personnageNbChoisi);
                continu = false;
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer une valeur correcte");
            }
        } while (continu);
    }

    @Override
    public void utiliserPouvoirAvatar() {
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();

        Random random = new Random();
        int personnageNbChoisi;

        do {
            personnageNbChoisi = random.nextInt(nbPersonnage);
        } while (plateau.getPersonnage(personnageNbChoisi) instanceof Assassin);

        executerAssassinat(personnageNbChoisi);
    }

    private void executerAssassinat(int indexPersonnage) {
        PlateauDeJeu plateau = this.getPlateau();
        Personnage personnageChoisi = plateau.getPersonnage(indexPersonnage);

        System.out.println("Vous venez d'assassiner: " + personnageChoisi.getNom());
        personnageChoisi.setAssassine();
    }
}
