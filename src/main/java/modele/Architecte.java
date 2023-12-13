package modele;

import controleur.InteractionOnline;

import java.util.ArrayList;

public class Architecte extends Personnage{

    public Architecte() {super("Architecte", 7, Caracteristiques.ARCHITECTE);}

    @Override
    public void utiliserPouvoirAvatar() {
        if (this.getJoueur() != null && !this.getAssassine()) {
            piocherDeuxCartesSupplementaires(null,false);
        }
    }

    private void piocherDeuxCartesSupplementaires(Server server, boolean estEnLigne) {
        Pioche pioche = this.getPlateau().getPioche();
        for (int i = 0; i < 2; i++) {
            Quartier cartePiochee = pioche.piocher();

            if (cartePiochee != null) {
                this.getJoueur().ajouterQuartierDansMain(cartePiochee);

                InteractionOnline.targetedMessage(server,estEnLigne,"Vous avez pioché une carte: "+ cartePiochee.getNom(), "Un joueur à pioché une carte supplémentaire", this.getJoueur().getNom());
            } else {
                InteractionOnline.messageToOne(server,estEnLigne,"La pioche est vide", this.getJoueur().getNom());
            }
        }
    }

    @Override
    public void utiliserPouvoir(Server server, boolean estEnLigne) {
        if (this.getJoueur() != null && !this.getAssassine()) {
            piocherDeuxCartesSupplementaires(server,estEnLigne);
        }
    }

}