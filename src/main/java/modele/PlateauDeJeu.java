package modele;

import java.util.ArrayList;

public class PlateauDeJeu {

    private Personnage[] listePersonnages;
    private Joueur[] listeJoueurs;
    private Pioche pioche;
    private int nombrePersonnages;
    private int nombreJoueurs;

    public PlateauDeJeu() {
        listePersonnages = new Personnage[8];
        listeJoueurs = new Joueur[8];
        nombrePersonnages = 0;
        nombreJoueurs = 0;
        pioche = new Pioche();
    }


    public void ajouterPersonnage(Personnage nouveau) {
        if (nombrePersonnages < listePersonnages.length) {
            listePersonnages[nombrePersonnages++] = nouveau;
        }
        else{
            //gerer l'erreur
        }
    }

    public Personnage getPersonnage(int i) {
        return listePersonnages[i];
        //gestion d'erreur à faire
    }

    public void ajouterJoueur(Joueur nouveau) {
        if (nombreJoueurs < listeJoueurs.length) {
            listeJoueurs[nombreJoueurs++] = nouveau;
        }else{
            //a gerer
        }
    }

    public Joueur getJoueur(int i) {
        if (i >= 0 && i < nombreJoueurs) {
            return listeJoueurs[i];
        }
        return null; // gerer l'erreur
    }


    public int getNombrePersonnages() {
        return nombrePersonnages;
    }

    public int getNombreJoueurs() {
        return nombreJoueurs;
    }

    public Pioche getPioche() {
        return pioche;
    }
}
