package modele;
import java.util.ArrayList;

public class Joueur {
    private String nom;
    private int tresor;
    private Quartier[] cite;
    private int nbQuartiers;
    private ArrayList<Quartier> main;
    private boolean possedeCouronne;

    public int nbQuartiersDansCite() {
        return nbQuartiers;
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
                cite[i] = null;
                nbQuartiers--;
                return q;
            }
        }
        return null;
    }

    public void ajouterQuartierDansMain(Quartier quartier) {
        main.add(quartier);
    }

    public void retirerQuartierDansMain() {
        if (!main.isEmpty()) {
            main.remove(main.size() - 1);
        }
    }

    public int nbQuartiersDansMain() {
        return main.size();
    }

    public void reinitialiser() {
        cite = new Quartier[cite.length];
        nbQuartiers = 0;
        main.clear();
    }

    public void ajouterPiece(int nbPiece){
        if(nbPiece>0){
            this.tresor += nbPiece;
        }
    }

    public void retirerPiece(int nbPieces) {
        if (nbPieces > tresor) {
            tresor = 0;
        } else {
            tresor -= nbPieces;
        }
    }
}
