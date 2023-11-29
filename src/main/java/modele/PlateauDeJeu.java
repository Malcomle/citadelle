package modele;

import java.util.ArrayList;

public class PlateauDeJeu {

    private ArrayList<Personnage> listePersonnages;
    private ArrayList<Joueur> listeJoueurs;
    private Pioche pioche;
    private int nombrePersonnages;
    private int nombreJoueurs;

    public PlateauDeJeu() {
        listePersonnages = new ArrayList<>();
        listeJoueurs = new ArrayList<>();
        pioche = new Pioche();
        nombrePersonnages = 0;
        nombreJoueurs = 0;
    }

    public void ajouterPersonnage(Personnage nouveau) {
        listePersonnages.add(nouveau);
        nombrePersonnages++;
    }

    public Personnage getPersonnage(int i) {
        return listePersonnages.get(i);
    }

    public void ajouterJoueur(Joueur nouveau) {
        listeJoueurs.add(nouveau);
        nombreJoueurs++;
    }

    public Joueur getJoueur(int i) {
        return listeJoueurs.get(i);
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
