package modele;

import controleur.Interaction;
import controleur.InteractionOnline;

import java.util.InputMismatchException;
import java.util.Random;

public class Assassin extends Personnage {

    public Assassin() {
        super("Assassin", 1, Caracteristiques.ASSASSIN);
    }

    @Override
    public void utiliserPouvoir(Server server, boolean estEnLigne) {
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();
        int personnageNbChoisi = 0;

        InteractionOnline.targetedMessage(server, estEnLigne, "Quel personnage voulez-vous assassiner ?", this.getJoueur().getNom()+" utilise son pouvoir", this.getJoueur().getNom());

        for (int i = 0; i < nbPersonnage; i++) {
            InteractionOnline.messageToOne(server, estEnLigne, (i + 1) + ": " + plateau.getPersonnage(i).getNom(), this.getJoueur().getNom());
        }

        boolean continu = true;
        do {
            try {
                personnageNbChoisi = InteractionOnline.lireUnEntier(server,estEnLigne, this.getJoueur().getNom(), "VOTRE CHOIX: ", 1,nbPersonnage + 1)-1;

                if (personnageNbChoisi < 0 || personnageNbChoisi >= nbPersonnage) {
                    throw new InputMismatchException();
                }

                executerAssassinat(personnageNbChoisi, server, estEnLigne);
                continu = false;
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer une valeur correcte");
            }
        } while (continu);
    }

    @Override
    public void utiliserPouvoirAvatar() {
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();

        Random random = new Random();
        int personnageNbChoisi;

        do {
            personnageNbChoisi = random.nextInt(nbPersonnage);
        } while (plateau.getPersonnage(personnageNbChoisi) instanceof Assassin);

        executerAssassinat(personnageNbChoisi, null, false);
    }

    private void executerAssassinat(int indexPersonnage, Server server, boolean estEnLigne) {
        PlateauDeJeu plateau = this.getPlateau();
        Personnage personnageChoisi = plateau.getPersonnage(indexPersonnage);

        InteractionOnline.targetedMessage(server,estEnLigne, "Vous venez d'assassiner: " + personnageChoisi.getNom(),
                personnageChoisi.getJoueur().getNom()+" a été eliminé", this.getJoueur().getNom());
        personnageChoisi.setAssassine();
    }
}
