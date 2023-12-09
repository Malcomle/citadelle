package modele;
import controleur.Interaction;

import java.util.Random;


public class Condottiere extends Personnage {
    public Condottiere() {
        super("Condottiere", 8, Caracteristiques.CONDOTTIERE);
    }

    @Override
    public void utiliserPouvoir() {
        PlateauDeJeu plateau = this.getPlateau();
        System.out.println("Choisissez le joueur cible (entrez un numéro):");

        for (int i = 0; i < plateau.getNombreJoueurs(); i++) {
            Joueur joueur = plateau.getJoueur(i);
            if (!joueur.equals(this.getJoueur())) {
                System.out.println(i + ": " + joueur.getNom());
            }
        }

        int choixJoueur = Interaction.lireUnEntier(0, plateau.getNombreJoueurs());
        Joueur cible = plateau.getJoueur(choixJoueur);
        if (cible.equals(this.getJoueur())) {
            System.out.println("Le Condottiere ne peut pas cibler sa propre cité.");
            return;
        }


        System.out.println("Choisissez le quartier à détruire dans la cité de " + cible.getNom() + ":");

        Quartier[] cite = cible.getCite();
        for (int i = 0; i < cite.length; i++) {
            if (cite[i] != null) {
                System.out.println(i + ": " + cite[i].getNom() + " (Coût: " + cite[i].getCout() + ")");
            }
        }

        int choixQuartier = Interaction.lireUnEntier(0, cite.length);
        Quartier quartierACibler = cite[choixQuartier];

        if (quartierACibler != null) {
            int coutDestruction = quartierACibler.getCout() - 1;
            if (this.getJoueur().nbPieces() >= coutDestruction) {
                this.getJoueur().retirerPieces(coutDestruction);
                cible.retirerQuartierDansCite(quartierACibler.getNom());
                System.out.println("Le Condottiere a détruit le quartier " + quartierACibler.getNom() + " en payant " + coutDestruction + " pièce(s) d'or.");
            } else {
                System.out.println("Pas assez de pièces pour détruire ce quartier.");
            }
        } else {
            System.out.println("Aucun quartier sélectionné pour la destruction.");
        }
    }


    @Override
    public void utiliserPouvoirAvatar() {
        PlateauDeJeu plateau = this.getPlateau();
        Random random = new Random();
        Joueur cible;
        Quartier quartierACibler;
        int choixJoueur;
        int choixQuartier;

        do {
            choixJoueur = random.nextInt(plateau.getNombreJoueurs());
            cible = plateau.getJoueur(choixJoueur);
        } while (cible == this.getJoueur() || cible.isEveque());

        do {
            choixQuartier = random.nextInt(cible.nbQuartiersDansCite());
            quartierACibler = cible.getCite()[choixQuartier];
        } while (quartierACibler == null || quartierACibler.getCout() == 1);

        int coutDestruction = quartierACibler.getCout() - 1;
        if (this.getJoueur().nbPieces() >= coutDestruction) {
            this.getJoueur().retirerPieces(coutDestruction);
            cible.retirerQuartierDansCite(quartierACibler.getNom());
            System.out.println("L'IA Condottiere a détruit le quartier " + quartierACibler.getNom() + " en payant " + coutDestruction + " pièce(s) d'or.");
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
