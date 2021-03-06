package tk.slaaavyn.slavikhomebackend.ws.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tk.slaaavyn.slavikhomebackend.model.ComponentType;
import tk.slaaavyn.slavikhomebackend.model.Device;
import tk.slaaavyn.slavikhomebackend.model.Role;
import tk.slaaavyn.slavikhomebackend.security.SecurityConstants;
import tk.slaaavyn.slavikhomebackend.security.jwt.JwtTokenProvider;
import tk.slaaavyn.slavikhomebackend.security.jwt.JwtUser;
import tk.slaaavyn.slavikhomebackend.service.DeviceService;
import tk.slaaavyn.slavikhomebackend.ws.models.DevicePojo;
import tk.slaaavyn.slavikhomebackend.ws.models.commands.BaseCommand;
import tk.slaaavyn.slavikhomebackend.ws.models.commands.RelayCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DeviceSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(DeviceSocketHandler.class);
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    private HashMap<WebSocketSession, String> sessions;

    private final DeviceService deviceService;
    private final JwtTokenProvider tokenProvider;

    public DeviceSocketHandler(DeviceService deviceService, JwtTokenProvider tokenProvider) {
        this.deviceService = deviceService;
        this.tokenProvider = tokenProvider;

        this.sessions = new HashMap<>();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        DevicePojo devicePojo = null;

        try {
            devicePojo = gson.fromJson(message.getPayload(), DevicePojo.class);
        } catch (IllegalStateException | JsonSyntaxException e) {
            logger.warn("parse incoming device: " + e);
        }

        if (devicePojo == null || devicePojo.getToken() == null || devicePojo.getUid() == null
                || isAccessDenied(devicePojo.getToken())) {
            return;
        }

        Device device = deviceService.connect(devicePojo.fromPojo());
        if (device == null) return;

        if(!sessions.containsValue(device.getUid())) {
            sessions.put(session, device.getUid());
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
        deviceService.disconnect(sessions.remove(session));
    }

    public void emmitCommandToDevice(BaseCommand command) {
        if(command == null || command.getUid() == null
                || command.getIndex() == null || command.getType() == null) return;

        WebSocketSession session = findSession(sessions, command.getUid());
        if(session == null) return;

        try {
            switch (command.getType()) {
                case RELAY:
                    session.sendMessage(new TextMessage(gson.toJson(command, RelayCommand.class)));
                    break;
            }
        } catch (IOException e) {
            logger.error("Send command to device: " + e);
        }
    }

    private WebSocketSession findSession(HashMap<WebSocketSession, String> sessions, String uid) {
        for (Map.Entry<WebSocketSession, String> entry : sessions.entrySet()) {
            if (entry.getValue().equals(uid)) {
                return entry.getKey();
            }
        }

        return null;
    }

    private boolean isAccessDenied(String token) {
        token = token.substring(7);

        return !tokenProvider.validateToken(token)
                || !JwtUser.userHasAuthority(
                        tokenProvider.getAuthentication(token).getAuthorities(),
                        Role.ROLE_DEVICE.name());
    }
}
