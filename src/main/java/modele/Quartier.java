package org.example;

public class Quartier {
    private String nom;
    private String type;
    private int cout;
    private String caracteristiques;
    public static final String[] TYPE_QUARTIERS = {"RELIGIEUX", "MILITAIRE", "NOBLE", "COMMERCANT", "MERVEILLE"};

    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        for(int i = 0;  i < TYPE_QUARTIERS.length ; i += 1){
            if(type == TYPE_QUARTIERS[i]){
                this.type = type;
                return;
            }
        }
        this.type="";
    }
    public int getcout(){
        return this.cout;
    }
    public void setCout(int cout){
        if(cout >= 1 && cout <= 6){
            this.cout = cout;
        }
        else{
            this.cout = 0;
        }
    }
    public String getCaracteristiques(){
        return this.caracteristiques;
    }
    public void setCaracteristiques(String nom){
        this.caracteristiques = caracteristiques;
    }

    public Quartier(String nom, String type, int cout, String caracteristiques) {
        setNom(nom);
        setType(type);
        setCout(cout);
        setCaracteristiques(caracteristiques);
    }


}