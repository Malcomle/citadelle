package controleur;

import modele.Server;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InteractionOnline {
    private static Scanner sc = new Scanner(System.in);


    public static int lireUnEntier(Server server) {
        int i = 0;
        boolean continu = true;
        do {
            try {
                server.sendMessageToAll("server", "Votre choix", "Jeu: ");

                System.out.print("Votre choix:");
                i = sc.nextInt();
                continu = false;

                return i;
            } catch (InputMismatchException e) {
                server.sendMessageToAll("server", "Entrez une valeur correct", "Jeu: ");

            }
        } while(continu);
        return i;
    }

    // renvoie un entier lu au clavier compris dans l'intervalle
    //     [borneMin, borneMax[
    public static int lireUnEntier(int borneMin, int borneMax, Server server) {
        int i = 0;
        boolean continu = true;
        do {
            try {
                server.sendMessageToAll("server", "Votre choix", "Jeu: ");

                i = sc.nextInt();

                if (i >= borneMin && i < borneMax) {
                    continu = false;
                } else {
                    server.sendMessageToAll("server", "Veuillez rentrer un chiffre entre " + borneMin + " et " + (borneMax - 1) + " : ", "Jeu: ");
                }

            } catch (InputMismatchException e) {
                server.sendMessageToAll("server", "Veuillez entrer une valeur correct", "Jeu: ");
            }
        } while(continu);
        return i;
    }

    // lit les r�ponses "oui", "non", "o" ou "n" et renvoie un bool�en
    public static boolean lireOuiOuNon(Server server) {
        String reponse;

        do {
            server.sendMessageToAll("server", "Veuillez répondre par 'oui' (o) ou 'non' (n):", "Jeu: ");

            reponse = sc.nextLine().toLowerCase(); // Convertir la réponse en minuscules pour éviter la sensibilité à la casse
        } while (!reponse.equals("oui") && !reponse.equals("o") && !reponse.equals("non") && !reponse.equals("n"));

        return reponse.equals("oui") || reponse.equals("o");
    }

    // renvoie une cha�ne de caract�re lue au clavier:
    public static String lireUneChaine(Server server) {
        server.sendMessageToAll("server", "Veuillez entrer une chaîne de caractères :", "Jeu: ");

        System.out.print("Veuillez entrer une chaîne de caractères : ");
        String retour = sc.nextLine();
        return retour;
    }
}