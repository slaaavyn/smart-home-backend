package tk.slaaavyn.slavikserver.ws.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tk.slaaavyn.slavikserver.model.ComponentType;
import tk.slaaavyn.slavikserver.security.jwt.JwtTokenProvider;
import tk.slaaavyn.slavikserver.ws.WsCommandParser;
import tk.slaaavyn.slavikserver.ws.models.commands.BaseCommand;
import tk.slaaavyn.slavikserver.ws.models.commands.RelayCommand;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ClientSocketHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessions;
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    private final DeviceSocketHandler deviceSocketHandler;
    private final JwtTokenProvider tokenProvider;

    public ClientSocketHandler(@Lazy DeviceSocketHandler deviceSocketHandler, JwtTokenProvider tokenProvider) {
        this.deviceSocketHandler = deviceSocketHandler;
        this.tokenProvider = tokenProvider;

        this.sessions = new CopyOnWriteArrayList<>();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        BaseCommand command = WsCommandParser.parseBaseCommand(message.getPayload());
        if (command == null || command.getToken() == null || isTokenInvalid(command.getToken())) return;

        switch (command.getType()) {
            case ACK:
                if (!sessions.contains(session)) {
                    sessions.add(session);
                }
                break;

            case RELAY:
                RelayCommand relayCommand = WsCommandParser.parseRelayCommand(message.getPayload());
                deviceSocketHandler.emmitCommandToDevice(relayCommand);
                break;
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        BaseCommand command = new BaseCommand();
        command.setType(ComponentType.ACK);

        session.sendMessage(new TextMessage(gson.toJson(command, BaseCommand.class)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }

    public void emmitForAll(String message) {
        TextMessage textMessage = new TextMessage(message);

        sessions.forEach(session -> {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean isTokenInvalid(String token) {
        return token == null || !tokenProvider.validateToken(token.substring(7));
    }
}
