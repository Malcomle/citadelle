package modele;

import controleur.Interaction;
import controleur.InteractionOnline;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
public class Voleur extends Personnage{
    Random random = new Random();

    public Voleur() {super("Voleur", 2, Caracteristiques.VOLEUR);}

    @Override
    public void utiliserPouvoir(Server server, boolean estEnLigne) {
        boolean continu = true;
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();
        int personnageNbChoisi = 0;

        InteractionOnline.messageToOne(server,estEnLigne, "Quel souhaitez vous voler ?", this.getJoueur().getNom());

        for (int i = 0; i < nbPersonnage; i++){
            InteractionOnline.messageToOne(server,estEnLigne, (i+1)+": "+plateau.getPersonnage(i).getNom(), this.getJoueur().getNom());

        }
        do {
            try {
                personnageNbChoisi = InteractionOnline.lireUnEntier(server,estEnLigne,this.getJoueur().getNom(),"VOTRE CHOIX:",1, nbPersonnage+1);
                personnageNbChoisi--;

                if(personnageNbChoisi < 0 || personnageNbChoisi >= nbPersonnage){
                    throw new InputMismatchException();
                }

                continu = executePouvoir(personnageNbChoisi, plateau,server,estEnLigne);

            }catch (InputMismatchException e){
                InteractionOnline.messageToOne(server,estEnLigne, "Veuillez entrer une valeur correct", this.getJoueur().getNom());

            }
        }while (continu);
    }

    @Override
    public void utiliserPouvoirAvatar() {
        boolean continu = true;
        PlateauDeJeu plateau = this.getPlateau();
        int nbPersonnage = plateau.getNombrePersonnages();
        int personnageNbChoisi = 0;


        System.out.println("Quel souhaitez vous voler ?");

        for (int i = 0; i < nbPersonnage; i++){
            System.out.println((i+1)+": "+plateau.getPersonnage(i).getNom());
        }
        do {
            personnageNbChoisi = random.nextInt(nbPersonnage);

            continu = executePouvoir(personnageNbChoisi, plateau, null, false);
        }while (continu);
    }

    private boolean executePouvoir(int personnageNbChoisi, PlateauDeJeu plateau, Server server, boolean estEnLigne){
        Personnage personnageChoisi = plateau.getPersonnage(personnageNbChoisi);

        if (personnageChoisi != null) {
            if(personnageChoisi instanceof Voleur){
                InteractionOnline.messageToOne(server,estEnLigne, "Vous ne pouvez pas vous voler vous même", this.getJoueur().getNom());
                return true;
            }else if(personnageChoisi.getRang() == 1){
                InteractionOnline.messageToOne(server,estEnLigne, "Vous ne pouvez pas vous voler un personnage de rang 1", this.getJoueur().getNom());

                return true;
            }

            Joueur joueurChoisi = personnageChoisi.getJoueur();

            if (joueurChoisi != null) {
                InteractionOnline.targetedMessage(server,estEnLigne, "Vous venez de voler: " + personnageChoisi.getNom(), "La voleur viens de voler "+personnageChoisi.getNom(), this.getJoueur().getNom());

                personnageChoisi.setVole();

                this.getJoueur().ajouterPieces(joueurChoisi.nbPieces());
                joueurChoisi.retirerPieces(joueurChoisi.nbPieces());

                return false;
            } else {
                InteractionOnline.targetedMessage(server,estEnLigne, "Pas de chance ce personnage n'est pas attribué", "La voleur n'a pas pu voler ", this.getJoueur().getNom());
            }
        } else {
            InteractionOnline.targetedMessage(server,estEnLigne, "Pas de chance ce personnage n'est pas attribué", "La voleur n'a pas pu voler ", this.getJoueur().getNom());

        }

        return false;
    }

}
