package modele;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port, String nomJoueur) {
        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendMessage(nomJoueur);

            listenForServerMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void listenForServerMessages() {
        new Thread(() -> {
            try {
                String messageFromServer;
                while ((messageFromServer = in.readLine()) != null) {
                    System.out.println("Message du serveur : " + messageFromServer);
                    // Traiter le message reçu ici
                }
            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture des messages du serveur. Connexion peut-être perdue.");
                e.printStackTrace();
            }
        }).start();
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
