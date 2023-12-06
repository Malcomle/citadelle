package modele;

public class Eveque extends Personnage {

    public Eveque() { super("Eveque", 5, Caracteristiques.EVEQUE); }


    @Override
    public void percevoirRessourcesSpecifiques() {
        if (this.getJoueur() != null) {
            int nbQuartiersReligieux = 0;
            Quartier[] cite = this.getJoueur().getCite();
            for (Quartier quartier : cite) {
                if (quartier != null && "RELIGIEUX".equals(quartier.getType())) {
                    nbQuartiersReligieux++;
                }
            }
            this.getJoueur().ajouterPieces(nbQuartiersReligieux);
            System.out.println(nbQuartiersReligieux + " pièces ajoutées pour les quartiers nobles");
        }


    }

    @Override
    public void utiliserPouvoir() {
    }
}
