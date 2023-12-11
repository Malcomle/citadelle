package modele;

import java.util.ArrayList;

public class Architecte extends Personnage{

    public Architecte() {super("Architecte", 7, Caracteristiques.ARCHITECTE);}

    @Override
    public void utiliserPouvoirAvatar() {
        if (this.getJoueur() != null && !this.getAssassine()) {
            piocherDeuxCartesSupplementaires();
        }
    }

    private void piocherDeuxCartesSupplementaires() {
        Pioche pioche = this.getPlateau().getPioche();
        for (int i = 0; i < 2; i++) {
            Quartier cartePiochee = pioche.piocher();

            if (cartePiochee != null) {
                this.getJoueur().ajouterQuartierDansMain(cartePiochee);
                System.out.println("Le joueur " + this.getJoueur().getNom() + " a pioché une carte : " + cartePiochee.getNom());
            } else {
                System.out.println("La pioche est vide.");
            }
        }
    }

    @Override
    public void utiliserPouvoir() {
        if (this.getJoueur() != null && !this.getAssassine()) {
            piocherDeuxCartesSupplementaires();
        }
    }

}