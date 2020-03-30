package tk.slaaavyn.slavikserver.ws.handler;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tk.slaaavyn.slavikserver.dto.TemperatureDto;
import tk.slaaavyn.slavikserver.model.Temperature;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TemperatureSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions;

    private final Gson gson;

    public TemperatureSocketHandler(Gson gson) {
        this.sessions = new CopyOnWriteArrayList<>();
        this.gson = gson;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }


    public void sendMessageForAll(Temperature temperature) {
        TextMessage message = new TextMessage(gson.toJson(TemperatureDto.toDTO(temperature), TemperatureDto.class));

        sessions.forEach(sessions -> {
            try {
                sessions.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
