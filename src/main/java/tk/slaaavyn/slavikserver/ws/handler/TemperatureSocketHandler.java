package tk.slaaavyn.slavikserver.ws.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tk.slaaavyn.slavikserver.model.ComponentType;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.model.Temperature;
import tk.slaaavyn.slavikserver.model.device.component.ThermometerComponent;
import tk.slaaavyn.slavikserver.security.jwt.JwtTokenProvider;
import tk.slaaavyn.slavikserver.service.TemperatureService;
import tk.slaaavyn.slavikserver.ws.WsCommandParser;
import tk.slaaavyn.slavikserver.ws.models.commands.BaseCommand;
import tk.slaaavyn.slavikserver.ws.models.commands.ThermometerCommand;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TemperatureSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> clientSessions = new CopyOnWriteArrayList<>();

    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    private final TemperatureService temperatureService;
    private final JwtTokenProvider tokenProvider;

    public TemperatureSocketHandler(TemperatureService temperatureService, JwtTokenProvider tokenProvider) {
        this.temperatureService = temperatureService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        BaseCommand command = WsCommandParser.parseBaseCommand(message.getPayload());

        if (command == null || command.getToken() == null || isTokenInvalid(command.getToken())) return;

        switch (command.getType()) {
            case ACK:
                if (!clientSessions.contains(session)) {
                    clientSessions.add(session);
                }
                break;

            case THERMOMETER:
                ThermometerCommand thermometerCommand = WsCommandParser.parseThermometerCommand(message.getPayload());
                if(thermometerCommand == null) return;

                emmitForAll(thermometerCommand);

                temperatureService.save(parseTemperature(thermometerCommand), command.getUid(), command.getIndex());
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
        clientSessions.remove(session);
    }


    public void emmitForAll(ThermometerCommand thermometerCommand) {
        TextMessage message = new TextMessage(gson.toJson(thermometerCommand, ThermometerCommand.class));

        clientSessions.forEach(sessions -> {
            try {
                sessions.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean isTokenInvalid(String token) {
        return token == null || !tokenProvider.validateToken(token.substring(7));
    }

    private Temperature parseTemperature(ThermometerCommand command) {
        try {
            Device device = new Device();
            device.setUid(command.getUid());

            ThermometerComponent component = new ThermometerComponent();
            component.setIndex(command.getIndex());
            component.setHumidity(component.getHumidity());
            component.setTemperature(component.getTemperature());
            component.setDevice(device);

            Temperature temperature = new Temperature();
            temperature.setHumidity(command.getHumidity());
            temperature.setTemperature(command.getTemperature());
            temperature.setComponent(component);

            return temperature;
        } catch (NullPointerException e) {
            return null;
        }
    }
}
