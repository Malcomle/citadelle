package modele;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private ServerSocket serverSocket;
    private final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (!serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            new Thread(clientHandler).start();
        }
    }

    public void addClient(String playerId, ClientHandler clientHandler) {
        clients.put(playerId, clientHandler);
    }

    public void removeClient(String playerId) {
        clients.remove(playerId);
    }

    public void sendMessageToAll(String type, String contenu, String destinataire) {
        Message msg = new Message(type, contenu, destinataire);
        String m = JsonUtil.toJson(msg);
        for (ClientHandler client : clients.values()) {
            client.sendMessage(m);
        }
    }

    public ClientHandler getClientHandler(String clientName) {
        System.out.println("Récupération du ClientHandler pour : " + clientName);

        return clients.get(clientName);
    }
    public void demanderReponseAUnJoueur(String type, String contenu, String destinataire) {
        ClientHandler clientHandler = clients.get(destinataire);
        if (clientHandler != null) {
            Message message = new Message(type, contenu, destinataire);
            String messageJson = JsonUtil.toJson(message);
            clientHandler.sendMessage(messageJson);
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
