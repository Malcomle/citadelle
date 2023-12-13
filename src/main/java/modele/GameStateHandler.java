package modele;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GameStateHandler {
    private Map<String, Consumer<String>> waitingResponses;

    public GameStateHandler() {
        this.waitingResponses = new HashMap<>();
    }

    public void addWaitingResponse(String playerId, Consumer<String> responseHandler) {
        waitingResponses.put(playerId, responseHandler);
    }

    public void handleResponse(String playerId, String response) {
        if (waitingResponses.containsKey(playerId)) {
            Consumer<String> handler = waitingResponses.remove(playerId);
            handler.accept(response);
        }
    }
}
