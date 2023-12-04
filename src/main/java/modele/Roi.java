package modele;

public class Roi extends Personnage {
    public Roi() {
        super("Roi", 4, Caracteristiques.ROI);
    }

    @Override
    public void utiliserPouvoir() {
        System.out.println("Je prends la couronne");
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

}
