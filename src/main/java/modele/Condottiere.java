package modele;
import controleur.Interaction;


public class Condottiere extends Personnage {
    public Condottiere() {
        super("Condottiere", 8, Caracteristiques.CONDOTTIERE);
    }

    @Override
    public void utiliserPouvoir() {
        PlateauDeJeu plateau = this.getPlateau();
        System.out.println("Voulez-vous utiliser votre pouvoir de destruction ? (o pour oui, n pour non)");
        boolean utiliserPouvoir = Interaction.lireOuiOuNon();

        if (utiliserPouvoir) {
            System.out.println("Voici la liste des joueurs et le contenu de leur cité :");
            for (int i = 0; i < plateau.getNombreJoueurs(); i++) {
                Joueur joueur = plateau.getJoueur(i);
                if (joueur != this.getJoueur()) { // Condottiere ne peut pas se cibler soi-même
                    System.out.print((i + 1) + " " + joueur.getNom() + ": ");
                    for (Quartier quartier : joueur.getCite()) {
                        if (quartier != null) { // Vérifier si le quartier est construit
                            System.out.print(quartier.getNom() + "(coût " + quartier.getCout() + "), ");
                        }
                    }
                    System.out.println();
                }
            }

            System.out.println("Pour information, vous avez " + this.getJoueur().nbPieces() + " pièces d’or dans votre trésor.");
            int choixJoueur = Interaction.lireUnEntier(0, plateau.getNombreJoueurs() + 1) - 1;

            if (choixJoueur >= 0 && choixJoueur < plateau.getNombreJoueurs()) {
                Joueur cible = plateau.getJoueur(choixJoueur);
                System.out.println("Quel quartier choisissez-vous ?");
                int choixQuartier = Interaction.lireUnEntier(1, cible.nbQuartiersDansCite() + 1) - 1;

                Quartier quartierACibler = cible.getCite()[choixQuartier];
                if (quartierACibler != null && this.getJoueur().nbPieces() >= quartierACibler.getCout()) {
                    this.getJoueur().retirerPieces(quartierACibler.getCout());
                    cible.retirerQuartierDansCite(quartierACibler.getNom());
                    System.out.println("=> On retire l’" + quartierACibler.getNom() + " à " + cible.getNom());
                    System.out.println("Pour information, votre trésor est constitué de " + this.getJoueur().nbPieces() + " pièce(s) d’or.");
                } else {
                    System.out.println("Votre trésor n’est pas suffisant ou le quartier n'existe pas.");
                }
            }
        }
    }
    @Override
    public void percevoirRessourcesSpecifiques() {
        if (this.getJoueur() != null) {
            int nbQuartiersMilitaires = 0;
            Quartier[] cite = this.getJoueur().getCite();
            for (Quartier quartier : cite) {
                if (quartier != null && "MILITAIRE".equals(quartier.getType())) {
                    nbQuartiersMilitaires++;
                }
            }
            this.getJoueur().ajouterPieces(nbQuartiersMilitaires);
            System.out.println(nbQuartiersMilitaires + " pièces ajoutées pour les quartiers nobles");
        }
    }

}
