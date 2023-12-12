package modele;
import java.util.ArrayList;
import java.util.Random;

public class Joueur {
    private String nom;
    private int tresor;
    private Quartier[] cite;
    private int nbQuartiers;
    private ArrayList<Quartier> main;
    private boolean possedeCouronne;


    protected Personnage monPersonnage;



    public boolean isEveque() {
        return this.monPersonnage instanceof Eveque;
    }
    public Joueur(String nom){
        this.nom = nom;
        this.tresor = 0;
        this.nbQuartiers = 0;
        this.possedeCouronne = false;
        this.cite = new Quartier[8];
        this.main = new ArrayList<Quartier>();
        this.monPersonnage = null;
    }

    public void choisirPersonnage(Personnage personnage) {
        this.monPersonnage = personnage;
        personnage.setJoueur(this);
    }
    public String getNom(){
        return this.nom;
    }

    public Personnage getMonPersonnage(){
        return this.monPersonnage;
    }

    public ArrayList<Quartier> getMain(){
        return main;
    }

    public Quartier[] getCite(){
        return cite;
    }

    public boolean getPossedeCouronne(){
        return possedeCouronne;
    }

    public void setPossedeCouronne(boolean b){
        this.possedeCouronne = b;
    }

    public void ajouterQuartierDansCite(Quartier quartier) {
        if (!quartierPresentDansCite(quartier.getNom()) && nbQuartiers < cite.length) {
            cite[nbQuartiers++] = quartier;
        }
    }

    public boolean quartierPresentDansCite(String nom) {
        for (Quartier q : cite) {
            if (q != null && q.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    public Quartier retirerQuartierDansCite(String nom) {
        for (int i = 0; i < cite.length; i++) {
            Quartier q = cite[i];
            if (q != null && q.getNom().equals(nom)) {
                // Décaler les quartiers suivants d'une position vers l'avant
                for (int j = i; j < cite.length - 1; j++) {
                    cite[j] = cite[j + 1];
                }
                // Mettre le dernier élément à null
                cite[cite.length - 1] = null;
                nbQuartiers--;
                return q;
            }
        }
        return null;
    }

    public void ajouterQuartierDansMain(Quartier quartier) {
        main.add(quartier);
    }

    public Quartier retirerQuartierDansMain() {
        if (!main.isEmpty()) {
            Random generateur = new Random();
            int numeroHasard = generateur.nextInt(this.nbQuartiersDansMain());
            return main.remove(numeroHasard);
        } else {
            return null;
        }
    }


    public void reinitialiser() {
        cite = new Quartier[cite.length];
        nbQuartiers = 0;
        tresor = 0;
        main.clear();
    }

    public void ajouterPieces(int nbPiece){
        if(nbPiece>0){
            this.tresor += nbPiece;
        }
    }

    public void retirerPieces(int nbPieces) {
        if (nbPieces <= tresor && nbPieces > 0) {
            tresor -= nbPieces;
        }
    }

    public int nbPieces(){
        return tresor;
    }

    public int nbQuartiersDansMain(){
        return main.size();
    }

    public int nbQuartiersDansCite(){
        int count = 0;
        for (Quartier q : cite) {
            if (q != null) {
                count++;
            }
        }
        return count;
    }

}
