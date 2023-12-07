package modele;

import controleur.Interaction;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Voleur extends Personnage{
    Random random = new Random();

    public Voleur() {super("Voleur", 2, Caracteristiques.VOLEUR);}

    @Override
    public void utiliserPouvoir() {
        boolean continu = true;
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();
        int personnageNbChoisi = 0;

        System.out.println("Quel souhaitez vous voler ?");

        for (int i = 0; i < nbPersonnage; i++){
            System.out.println((i+1)+": "+plateau.getPersonnage(i).getNom());
        }
        do {
            try {
                personnageNbChoisi = Interaction.lireUnEntier(1, nbPersonnage+1);
                personnageNbChoisi--;

                if(personnageNbChoisi < 0 || personnageNbChoisi >= nbPersonnage){
                    throw new InputMismatchException();
                }

                continu = executePouvoir(personnageNbChoisi, plateau);

            }catch (InputMismatchException e){
                System.out.println("Veuillez entrer une valeur correct");
            }
        }while (continu);
    }

    @Override
    public void utiliserPouvoirAvatar() {
        boolean continu = true;
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();
        int personnageNbChoisi = 0;


        System.out.println("Quel souhaitez vous voler ?");

        for (int i = 0; i < nbPersonnage; i++){
            System.out.println((i+1)+": "+plateau.getPersonnage(i).getNom());
        }
        do {
            personnageNbChoisi = random.nextInt(nbPersonnage);

            continu = executePouvoir(personnageNbChoisi, plateau);
        }while (continu);
    }

    private boolean executePouvoir(int personnageNbChoisi, PlateauDeJeu plateau){
        Personnage personnageChoisi = null;

        personnageChoisi = plateau.getPersonnage(personnageNbChoisi);

        if(personnageChoisi instanceof Voleur){
            System.out.println("Vous ne pouvez pas vous voler vous même");
            return true;
        }else if(personnageChoisi.getRang() == 1){
            System.out.println("Vous ne pouvez pas voler un personnage de rang 1");
            return true;
        }
        else {
            Joueur joueurChoisi = personnageChoisi.getJoueur();

            System.out.println("Vous venez de voler: "+ personnageChoisi.getNom());

            personnageChoisi.setVole();

            this.getJoueur().ajouterPieces(joueurChoisi.nbPieces());
            joueurChoisi.retirerPieces(joueurChoisi.nbPieces());

            return false;
        }
    }
}
