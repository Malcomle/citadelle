package modele;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final Server server;
    public String playerId;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            this.playerId = in.readLine();
            server.addClient(playerId, this);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                processClientInput(inputLine);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void processClientInput(String input) {
        server.processClientResponse(this, input);
    }


    public String sendRequestAndWaitForReply(String request) {
        sendMessage(request);
        try {
            return in.readLine(); // Attend la réponse du client
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void sendMessage(String message) {
        out.println(message);
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
