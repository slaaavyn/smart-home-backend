package tk.slaaavyn.slavikserver.ws;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions;

    private final Gson gson;

    public SocketHandler(Gson gson) {
        this.sessions = new CopyOnWriteArrayList<>();
        this.gson = gson;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        Map<String, String> value = gson.fromJson(message.getPayload(), Map.class);
        session.sendMessage(new TextMessage("Hello " + value.get("name") + " !"));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        this.sessions.remove(session);
    }


    private void sendMessageForAll(String message) throws IOException {
        for(WebSocketSession webSocketSession : this.sessions) {
            webSocketSession.sendMessage(new TextMessage(message));
        }
    }
}
