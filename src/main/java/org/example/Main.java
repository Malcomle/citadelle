package org.example;

import controleur.Interaction;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");
        boolean b = Interaction.lireOuiOuNon();
        System.out.println(b);
    }
}