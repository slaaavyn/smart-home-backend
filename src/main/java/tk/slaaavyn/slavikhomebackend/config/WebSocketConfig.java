package tk.slaaavyn.slavikhomebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import tk.slaaavyn.slavikhomebackend.ws.handler.ClientSocketHandler;
import tk.slaaavyn.slavikhomebackend.ws.handler.DeviceSocketHandler;
import tk.slaaavyn.slavikhomebackend.ws.handler.TemperatureSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TemperatureSocketHandler temperatureSocketHandler;
    private final DeviceSocketHandler deviceSocketHandler;
    private final ClientSocketHandler clientSocketHandler;

    public WebSocketConfig(TemperatureSocketHandler temperatureSocketHandler, DeviceSocketHandler deviceSocketHandler,
                           ClientSocketHandler clientSocketHandler) {
        this.temperatureSocketHandler = temperatureSocketHandler;
        this.deviceSocketHandler = deviceSocketHandler;
        this.clientSocketHandler = clientSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(temperatureSocketHandler, EndpointConstants.WS_TEMPERATURE);
        registry.addHandler(deviceSocketHandler, EndpointConstants.WS_DEVICE);
        registry.addHandler(clientSocketHandler, EndpointConstants.WS_CLIENT);
    }



}