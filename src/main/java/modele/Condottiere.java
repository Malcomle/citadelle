package modele;
import controleur.Interaction;
import controleur.InteractionOnline;

import java.util.Random;


public class Condottiere extends Personnage {
    public Condottiere() {
        super("Condottiere", 8, Caracteristiques.CONDOTTIERE);
    }

    @Override
    public void utiliserPouvoir(Server server, boolean estEnLigne) {
        PlateauDeJeu plateau = this.getPlateau();

        InteractionOnline.messageToOne(server,estEnLigne, "Choisissez le joueur cible (entrez un numéro):", this.getJoueur().getNom());

        for (int i = 0; i < plateau.getNombreJoueurs(); i++) {
            Joueur joueur = plateau.getJoueur(i);
            if (!joueur.equals(this.getJoueur())) {
                InteractionOnline.messageToOne(server,estEnLigne, i + ": " + joueur.getNom(), this.getJoueur().getNom());
            }
        }

        int choixJoueur = InteractionOnline.lireUnEntier(server, estEnLigne,this.getJoueur().getNom(),"VOTRE CHOIX: ",0, plateau.getNombreJoueurs());
        Joueur cible = plateau.getJoueur(choixJoueur);
        if (cible.equals(this.getJoueur())) {
            InteractionOnline.messageToOne(server,estEnLigne, "Le Condottiere ne peut pas cibler sa propre cité.", this.getJoueur().getNom());
            return;
        }

        InteractionOnline.targetedMessage(server,estEnLigne, "Choisissez le quartier à détruire dans la cité de " + cible.getNom() + ":", "La condottiere cherche à detruire un quartier", this.getJoueur().getNom());

        Quartier[] cite = cible.getCite();
        for (int i = 0; i < cite.length; i++) {
            if (cite[i] != null) {
                InteractionOnline.messageToOne(server,estEnLigne, i + ": " + cite[i].getNom() + " (Coût: " + cite[i].getCout() + ")", this.getJoueur().getNom());
            }
        }

        int choixQuartier = Interaction.lireUnEntier(0, cite.length);
        Quartier quartierACibler = cite[choixQuartier];

        if (quartierACibler != null) {
            int coutDestruction = quartierACibler.getCout() - 1;
            if (this.getJoueur().nbPieces() >= coutDestruction) {
                this.getJoueur().retirerPieces(coutDestruction);
                cible.retirerQuartierDansCite(quartierACibler.getNom());
                InteractionOnline.generalMessage(server,estEnLigne,"Le Condottiere a détruit le quartier " + quartierACibler.getNom() + " en payant " + coutDestruction + " pièce(s) d'or.");
            } else {
                InteractionOnline.targetedMessage(server,estEnLigne, "Pas assez de pièces pour détruire ce quartier.", "La condottiere choisit de ne pas detruire de quartier", this.getJoueur().getNom());

            }
        } else {
            InteractionOnline.targetedMessage(server,estEnLigne, "Aucun quartier sélectionné pour la destruction.", "La condottiere choisit de ne pas detruire de quartier", this.getJoueur().getNom());
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
