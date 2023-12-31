package modele;


public abstract class Personnage {
    private String nom;
    private int rang;
    private String caracteristiques;
    private Joueur joueur; 
    private boolean assassine;
    private boolean vole;
    private boolean selectionne = false;

    private PlateauDeJeu plateau;

    public Personnage(String nom, int rang, String caracteristiques) {
        this.nom = nom;
        this.rang = rang;
        this.caracteristiques = caracteristiques;
        this.joueur = null; 
        this.assassine = false; 
        this.vole = false;
    }

    // remise en question de ce controlleur innutile
    //forcé à cause du test assassin
    public Personnage(){
        this.nom = "";
        this.rang = 1;
        this.caracteristiques = "";
        this.joueur = null;
        this.assassine = false;
        this.vole = false;
    }
    public boolean estSelectionne() {
        return selectionne;
    }

    public void setSelectionne(boolean selectionne) {
        this.selectionne = selectionne;
    }
    public String getNom() {
        return nom;
    }

    public PlateauDeJeu getPlateau(){
        return plateau;
    }

    public void setPlateau(PlateauDeJeu plateau){
        this.plateau = plateau;
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

    public boolean getAssassine() {
        return assassine;
    }

    public boolean getVole() {
        return vole;
    }

    public void setJoueur(Joueur joueur) {

        this.joueur = joueur;
        this.joueur.monPersonnage = this;
    }

    public void setAssassine() {
        assassine = !assassine;
    }

    public void setVole() {
        this.vole = !vole;
    }

    public void ajouterPieces() {
        if (this.joueur != null && !this.assassine) {
            this.joueur.ajouterPieces(2);
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

    public abstract void utiliserPouvoir(Server server, boolean estEnLigne);
    public abstract void utiliserPouvoirAvatar();

    public void reinitialiser() {
        this.joueur = null;
        this.assassine = false;
        this.vole = false;
    }
}
