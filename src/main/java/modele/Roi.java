package modele;

public class Roi extends Personnage {

    public Roi() {
        // Appel au constructeur de la classe parent avec des valeurs spécifiques pour le Roi.
        super("Roi", 4, caracteristiques.ROI); // Assumons que Caracteristiques.ROI est une constante.
    }

    @Override
    public void percevoirRessourcesSpecifiques() {
        // Cette implémentation suppose l'existence d'une méthode compterQuartiersNobles() dans Joueur.
        if (this.getJoueur() != null) {
            int nbQuartiersNobles = this.getJoueur().compterQuartiersNobles();
            this.getJoueur().ajouterPieces(nbQuartiersNobles);
            System.out.println("Le Roi a reçu " + nbQuartiersNobles + " pièces d'or pour ses quartiers nobles.");
        }
    }

    @Override
    public void utiliserPouvoir() {
        System.out.println("Je prends la couronne.");
        // Cette implémentation suppose l'existence d'une méthode setPossedeCouronne dans Joueur.
        if (this.getJoueur() != null) {
            this.getJoueur().setPossedeCouronne(true);
        }
    }

    // Toutes les autres méthodes de Personnage sont héritées et ne sont pas redéfinies ici,
    // sauf si une fonctionnalité spécifique est requise par le diagramme UML.
}
