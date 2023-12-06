package modele;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Voleur extends Personnage{
    private static Scanner sc = new Scanner(System.in);

    public Voleur() {super("Voleur", 2, Caracteristiques.VOLEUR);}

    @Override
    public void utiliserPouvoir() {
        boolean continu = true;
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();
        int personnageNbChoisi = 0;
        Personnage personnageChoisi = null;

        System.out.println("Quel souhaitez vous voler ?");

        for (int i = 0; i < nbPersonnage; i++){
            System.out.println((i+1)+": "+plateau.getPersonnage(i).getNom());
        }
        do {
            try {
                System.out.print("Votre choix: ");
                personnageNbChoisi = sc.nextInt()-1;

                if(personnageNbChoisi < 0 || personnageNbChoisi >= nbPersonnage){
                    throw new InputMismatchException();
                }

                personnageChoisi = plateau.getPersonnage(personnageNbChoisi);

                if(personnageChoisi instanceof Voleur){
                    System.out.println("Vous ne pouvez pas vous voler vous même");
                }else if(personnageChoisi.getRang() == 1){
                    System.out.println("Vous ne pouvez pas voler un personnage de rang 1");
                }
                else {
                    Joueur joueurChoisi = personnageChoisi.getJoueur();

                    System.out.println("Vous venez de voler: "+ personnageChoisi.getNom());

                    personnageChoisi.setVole();

                    this.getJoueur().ajouterPieces(joueurChoisi.nbPieces());
                    joueurChoisi.retirerPieces(joueurChoisi.nbPieces());

                    continu = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Veuillez entrer une valeur correct");
            }
        }while (continu);
    }
}
