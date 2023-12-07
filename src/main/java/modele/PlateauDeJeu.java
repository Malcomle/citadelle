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
        if (nouveau != null && nombrePersonnages < listePersonnages.length) {
            listePersonnages[nombrePersonnages++] = nouveau;
            nouveau.setPlateau(this);
        }
    }

    public void ajouterJoueur(Joueur nouveau) {
        if (nombreJoueurs < listeJoueurs.length) {
            listeJoueurs[nombreJoueurs++] = nouveau;
        }
    }

    public Personnage getPersonnage(int i) {
        if (i >= 0 && i < listePersonnages.length-1) {
            return listePersonnages[i];
        }
        return null;
    }

    public Joueur getJoueur(int i) {
        if (i >= 0 && i < listeJoueurs.length-1) {
            return listeJoueurs[i];
        }
        return null;
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

    public void setPioche(Pioche p) {
        this.pioche = p;
    }

}
