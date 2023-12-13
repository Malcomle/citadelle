package modele;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Server {
    private  GameStateHandler gameStateHandler;
    private Map<String, CompletableFuture<String>> responseFutures = new ConcurrentHashMap<>();


    public Server() {
        this.gameStateHandler = new GameStateHandler();
    }

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


    public Set<String> getClients(){
        return clients.keySet();
    }

    public ClientHandler getClientHandler(String clientName) {
        System.out.println("Récupération du ClientHandler pour : " + clientName);

        return clients.get(clientName);
    }

    public void sendMessageToAll(String type, String contenu, String destinataire) {
        Message msg = new Message(type, contenu, destinataire);
        String m = JsonUtil.toJson(msg);
        for (ClientHandler client : clients.values()) {
            client.sendMessage("DEBUG: "+msg.getContenu());
        }
    }

    public void sendMessageToAllExcept(String type, String contenu, String destinataire) {
        Message msg = new Message(type, contenu, destinataire);
        String m = JsonUtil.toJson(msg);

        for (Map.Entry<String, ClientHandler> clientEntry : clients.entrySet()) {
            if (!clientEntry.getKey().equals(destinataire)) {
                clientEntry.getValue().sendMessage("DEBUG: "+contenu);
            }
        }
    }


    public void sendMessageToOne(String type, String contenu, String destinataire) {
        ClientHandler clientHandler = clients.get(destinataire);
        if (clientHandler != null) {
            Message message = new Message(type, contenu, destinataire);
            String messageJson = JsonUtil.toJson(message);
            clientHandler.sendMessage("DEBUG: "+ contenu);
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public CompletableFuture<String> askPlayer(String playerId, String question) {
        CompletableFuture<String> futureResponse = new CompletableFuture<>();
        responseFutures.put(playerId, futureResponse);
        ClientHandler client = clients.get(playerId);
        if (client != null) {
            client.sendMessage(question);
        }
        return futureResponse;
    }

    public void processClientResponse(ClientHandler playerId, String response) {
        CompletableFuture<String> future = responseFutures.get(playerId.playerId);
        if (future != null) {
            future.complete(response);
            responseFutures.remove(playerId.playerId);
        }
    }


}
