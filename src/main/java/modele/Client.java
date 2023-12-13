package modele;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String nomJoueur;

    public void startConnection(String ip, int port, String nomJoueur) throws IOException {
        System.out.println("Tentative de connexion au serveur...");

        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Connexion établie.");
        System.out.println("Le host de la partie doit appuyer sur entrée pour lancer la partie");

        this.nomJoueur = nomJoueur;
        sendMessage(nomJoueur);

        new Thread(this::listenForServerMessages).start();
        new Thread(this::sendUserInputToServer).start();

    }

    private void listenForServerMessages() {
        try {
            String messageDuServeur;
            while ((messageDuServeur = in.readLine()) != null) {
                System.out.println(messageDuServeur);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la réception du message du serveur.");
            e.printStackTrace();
        }
    }

    public void sendUserInputToServer() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String userInput = scanner.nextLine();
                sendMessage(userInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
        System.out.println("Connexion fermée.");
    }
}
