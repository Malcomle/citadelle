package controleur;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Interaction {
    private static Scanner sc = new Scanner(System.in);

    public static int lireUnEntier() {
        int i = 0;
        boolean continu = true;
        do {
            try {
                System.out.print("Votre choix:");
                i = sc.nextInt();
                continu = false;
            } catch (InputMismatchException e) {
                System.out.print("Entrez une valeur correct : ");
            }
        } while(continu);
        return i;
    }

    // renvoie un entier lu au clavier compris dans l'intervalle
    //     [borneMin, borneMax[
    public static int lireUnEntier(int borneMin, int borneMax) {
        int i = 0;
        boolean continu = true;
        do {
            try {
                System.out.print("Votre choix: ");
                i = sc.nextInt();

                if (i >= borneMin && i < borneMax) {
                    continu = false;
                } else {
                    System.out.println("Veuillez rentrer un chiffre entre " + borneMin + " et " + (borneMax - 1) + " : ");
                }

            } catch (InputMismatchException e) {
                System.out.print("Veuillez entrer une valeur correct");
            }
        } while(continu);
        return i;
    }

    // lit les r�ponses "oui", "non", "o" ou "n" et renvoie un bool�en
    public static boolean lireOuiOuNon() {
        String reponse;

        do {
            System.out.print("Veuillez répondre par 'oui' (o) ou 'non' (n): ");
            reponse = sc.nextLine().toLowerCase(); // Convertir la réponse en minuscules pour éviter la sensibilité à la casse
        } while (!reponse.equals("oui") && !reponse.equals("o") && !reponse.equals("non") && !reponse.equals("n"));

        return reponse.equals("oui") || reponse.equals("o");
    }

    // renvoie une cha�ne de caract�re lue au clavier:
    public static String lireUneChaine() {
        System.out.print("Veuillez entrer une chaîne de caractères : ");
        String retour = sc.nextLine();
        return retour;
    }
}