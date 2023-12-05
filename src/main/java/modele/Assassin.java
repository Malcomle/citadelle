package modele;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Assassin extends Personnage{
    private static Scanner sc = new Scanner(System.in);

    public Assassin() {super("Assassin", 3, Caracteristiques.ASSASSIN);}

    @Override
    public void utiliserPouvoir() {
        boolean continu = true;
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();
        int personnageNbChoisi = 0;
        Personnage personnageChoisi = null;

        System.out.println("Quel personnage voulez-vous assassiner ?");

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

                if(personnageChoisi instanceof Assassin){
                    System.out.println("Vous ne pouvez pas vous assassinez vous même");
                }else {
                    System.out.println("Vous venez d'assassiner: "+ personnageChoisi.getNom());
                    personnageChoisi.setAssassine();1
                    continu = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Veuillez entrer une valeur correct");
            }
        }while (continu);
    }
}
