package modele;


public abstract class Personnage {
    private String nom;
    private int rang;
    private String caracteristiques;
    private Joueur joueur; 
    private boolean assassine;
    private boolean vole;

    private PlateauDeJeu plateau;

    public Personnage(String nom, int rang, String caracteristiques) {
        this.nom = nom;
        this.rang = rang;
        this.caracteristiques = caracteristiques;
        this.joueur = null; 
        this.assassine = false; 
        this.vole = false;
    }

    public String getNom() {
        return nom;
    }

    public int getRang() {
        return rang;
    }

    public String getCaracteristiques() {
        return caracteristiques;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public boolean isAssassine() {
        return assassine;
    }

    public boolean isVole() {
        return vole;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setAssassine(boolean assassine) {
        this.assassine = assassine;
    }

    public void setVole(boolean vole) {
        this.vole = vole;
    }

    public void ajouterPieces() {
        if (this.joueur != null && !this.assassine) {
            this.joueur.ajouterPiece(2);
        }
    }

    public void ajouterQuartier(Quartier nouveau) {
        if (this.joueur != null && !this.assassine) {
            this.joueur.ajouterQuartierDansMain(nouveau);
        }
    }

    public void construire(Quartier nouveau) {
        if (this.joueur != null && !this.assassine) {
            this.joueur.ajouterQuartierDansCite(nouveau);
        }
    }

    public void percevoirRessourcesSpecifiques() {
        if (this.joueur != null && !this.assassine) {
            System.out.println("aucune ressource spécifique");
        }
    }

    public abstract void utiliserPouvoir();


    public void reinitialiser() {
        this.joueur = null;
        this.assassine = false;
        this.vole = false;
    }
}
