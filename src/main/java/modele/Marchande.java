package modele;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Marchande extends Personnage{

    public Marchande() {super("Marchande", 6, Caracteristiques.MARCHANDE);}

    @Override
    public void utiliserPouvoir() {
        boolean continu = true;
        do {
            try {
                if (this.getJoueur() != null) {
                    this.getJoueur().ajouterPieces(1);
                }

            }catch (InputMismatchException e){
                System.out.println("Error !");
            }
        }while (continu);
    }

    @Override
    public void utiliserPouvoirAvatar() {

    }

    @Override
    public void percevoirRessourcesSpecifiques(){
        if (this.getJoueur() != null) {
            int nbQuartiersCommercants = 0;
            Quartier[] cite = this.getJoueur().getCite();
            for (Quartier quartier : cite) {
                if (quartier != null && "COMMERCANT".equals(quartier.getType())) {
                    nbQuartiersCommercants++;
                }
            }
            this.getJoueur().ajouterPieces(nbQuartiersCommercants);
            System.out.println(nbQuartiersCommercants + " pièces ajoutées pour les quartiers commerçants");
        }
    }

}
