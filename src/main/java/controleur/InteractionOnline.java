package controleur;

import modele.Server;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class InteractionOnline {

    public static int lireUnEntier(Server server, boolean estEnLigne, String to, String question) {
        if(estEnLigne) {
            do {
                try {
                    CompletableFuture<String> futureResponse = server.askPlayer(to, question);
                    String response = futureResponse.get();
                    return Integer.parseInt(response);

                } catch (InterruptedException | ExecutionException | NumberFormatException f) {
                    server.sendMessageToOne("server", "Entrez une valeur correct", to);
                }
            }while (true);
        }else{
            return Interaction.lireUnEntier();
        }
    }

    // renvoie un entier lu au clavier compris dans l'intervalle
    //     [borneMin, borneMax[
    public static int lireUnEntier(Server server, boolean estEnLigne, String to, String question, int borneMin, int borneMax) {
        if(estEnLigne) {
            do {
                try {
                    CompletableFuture<String> futureResponse = server.askPlayer(to, question);
                    String response = futureResponse.get();
                    int res = Integer.parseInt(response);
                    if(res >= borneMin || res < borneMax){
                        return res;
                    }
                } catch (InterruptedException | ExecutionException  | NumberFormatException f) {
                    server.sendMessageToOne("server", "Entrez une valeur correct", to);
                }
            }while (true);
        }else{
            return Interaction.lireUnEntier();
        }
    }

    public static boolean lireOuiOuNon(Server server) {
        String reponse;

        do {
            server.sendMessageToAll("server", "Veuillez répondre par 'oui' (o) ou 'non' (n):", "Jeu: ");

            Scanner sc = new Scanner(System.in);
            reponse = sc.nextLine().toLowerCase();
        } while (!reponse.equals("oui") && !reponse.equals("o") && !reponse.equals("non") && !reponse.equals("n"));

        return reponse.equals("oui") || reponse.equals("o");
    }

    // renvoie une cha�ne de caract�re lue au clavier:
    public static String lireUneChaine(Server server) {
        server.sendMessageToAll("server", "Veuillez entrer une chaîne de caractères :", "Jeu: ");

        System.out.print("Veuillez entrer une chaîne de caractères : ");
        Scanner sc = new Scanner(System.in);
        String retour = sc.nextLine();
        return retour;
    }

    public static void generalMessage(Server server, boolean estEnLigne, String contenu){
        if (estEnLigne) {
            server.sendMessageToAll("server", contenu, "Jeu: ");
        } else {
            System.out.println(contenu);
        }
    }

    public static void targetedMessage(Server server, boolean estEnLigne, String contenu, String other, String destinataire){
        if (estEnLigne) {
            server.sendMessageToOne("server", contenu, destinataire);

            server.sendMessageToAllExcept("server", other, destinataire);
        } else {
            System.out.println(contenu);
        }
    }

    public static void messageToOne(Server server, boolean estEnLigne, String contenu, String destinataire){
        if (estEnLigne) {
            server.sendMessageToOne("server", contenu, destinataire);
        } else {
            System.out.println(contenu);
        }
    }
}