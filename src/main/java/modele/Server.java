package modele;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private ServerSocket serverSocket;
    private final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private ServerListener listener;

    public interface ServerListener { void onClientConnected(String clientInfo, String pseudo);}

    public void setServerListener(ServerListener listener) {
        this.listener = listener;
    }

    public void start(int port) {
        Thread serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Serveur démarré sur le port " + port);

                while (!serverSocket.isClosed()) {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    clientHandler.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }


    public void stop() {
        try {
            for (ClientHandler client : clients.values()) {
                client.closeConnection();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void onClientConnected(String clientInfo, String pseudo) {
        if (listener != null) {
            listener.onClientConnected(clientInfo, pseudo);
        }
    }

    void addClient(String playerId, ClientHandler handler) {
        clients.put(playerId, handler);
    }

    void removeClient(String playerId) {
        clients.remove(playerId);
    }

    private class ClientHandler extends Thread {
        private final Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private final Server server;
        private String playerId;

        public ClientHandler(Socket socket, Server server) {
            this.clientSocket = socket;
            this.server = server;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                this.playerId = in.readLine();
                server.addClient(playerId, this);
                server.onClientConnected(clientSocket.getInetAddress().getHostAddress(), playerId);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    // Gérer les messages reçus ici
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        void sendMessage(String message) {
            out.println(message);
        }

        void closeConnection() {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
                server.removeClient(this.playerId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
