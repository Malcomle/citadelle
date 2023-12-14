package modele;

import controleur.InteractionOnline;

import java.util.Random;

public class Roi extends Personnage {
    public Roi() {
        super("Roi", 4, Caracteristiques.ROI);
    }

    @Override
    public void utiliserPouvoir(Server server, boolean estEnLigne) {
        InteractionOnline.targetedMessage(server,estEnLigne, "Vous Prenez la couronne.", this.getJoueur().getNom()+" prend la couronne !", this.getJoueur().getNom());

        if (this.getJoueur() != null) {
            this.getJoueur().setPossedeCouronne(true);
        }
    }


    @Override
    public void percevoirRessourcesSpecifiques() {
        if (this.getJoueur() != null) {
            int nbQuartiersNobles = 0;
            Quartier[] cite = this.getJoueur().getCite();
            for (Quartier quartier : cite) {
                if (quartier != null && "NOBLE".equals(quartier.getType())) {
                    nbQuartiersNobles++;
                }
            }
            this.getJoueur().ajouterPieces(nbQuartiersNobles);
            System.out.println(nbQuartiersNobles + " pièces ajoutées pour les quartiers nobles");
        }
    }
    @Override
    public void utiliserPouvoirAvatar() {
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();

        Random random = new Random();
        int personnageNbChoisi;

        do {
            personnageNbChoisi = random.nextInt(nbPersonnage);
        } while (plateau.getPersonnage(personnageNbChoisi) instanceof Roi);

        executerRoi(personnageNbChoisi);
    }
    private void executerRoi(int indexPersonnage) {
        PlateauDeJeu plateau = this.getPlateau();
        Personnage personnageChoisi = plateau.getPersonnage(indexPersonnage);
    }

}
