package modele;

import java.util.InputMismatchException;

public class Marchande extends Personnage{

    public Marchande() {super("Marchande", 6, Caracteristiques.MARCHANDE);}

    @Override
    public void utiliserPouvoir() {
                if (this.getJoueur() != null) {
                    this.getJoueur().ajouterPieces(1);
                    System.out.println("Ajouter 1 pièce");
                }
    }

    @Override
    public void utiliserPouvoirAvatar() {
        utiliserPouvoir();
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
            System.out.println("Ajouter " + nbQuartiersCommercants + " pièce" + (nbQuartiersCommercants > 1 ? "s" : ""));
        }
    }

}
