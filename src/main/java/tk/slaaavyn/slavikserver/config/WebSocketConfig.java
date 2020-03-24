package tk.slaaavyn.slavikserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler socketHandler;

    public WebSocketConfig(WebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler,
                EndpointConstants.WEB_SOCKET_ENDPOINT + EndpointConstants.TEMPERATURE_ENDPOINT);
    }

}