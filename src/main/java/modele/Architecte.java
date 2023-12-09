package modele;

import controleur.Interaction;
import java.util.ArrayList;

public class Architecte extends Personnage{

    public Architecte() {super("Architecte", 7, Caracteristiques.ARCHITECTE);}

    @Override
    public void utiliserPouvoir() {
        boolean continu = true;
        PlateauDeJeu plateau = this.getPlateau();
        Joueur joueur = plateau.getJoueurCourant();
        PiocheQuartier piocheQuartier = plateau.getPiocheQuartier();

        piocherCartesSupplementaires(joueur, piocheQuartier, 2);


    }

    private void piocherCartesSupplementaires(Joueur joueur, PiocheQuartier piocheQuartier, int nombreCartes) {
        List<CarteQuartier> cartesPiochees = piocheQuartier.piocherCartes(nombreCartes);
        joueur.ajouterCartesQuartier(cartesPiochees);
        System.out.println(joueur.getNom() + " a pioché " + nombreCartes + " cartes quartier supplémentaires.");
        System.out.println("Cartes piochées : " + cartesPiochees);
    }
    @Override
    public void utiliserPouvoirAvatar() {
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();

        Random random = new Random();
        int personnageNbChoisi;

        do {
            personnageNbChoisi = random.nextInt(nbPersonnage);
        } while (plateau.getPersonnage(personnageNbChoisi) instanceof Architecte);

        executerArchitecte(personnageNbChoisi);
    }

    private void executerArchitecte(int indexPersonnage) {
        PlateauDeJeu plateau = this.getPlateau();
        Personnage personnageChoisi = plateau.getPersonnage(indexPersonnage);

    }
}
