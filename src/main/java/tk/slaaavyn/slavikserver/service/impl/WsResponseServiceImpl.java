package tk.slaaavyn.slavikserver.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikserver.dto.DeviceDto;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.model.Room;
import tk.slaaavyn.slavikserver.service.WsResponseService;
import tk.slaaavyn.slavikserver.ws.handler.ClientSocketHandler;
import tk.slaaavyn.slavikserver.ws.models.response.DeviceResponseToClient;
import tk.slaaavyn.slavikserver.ws.models.response.MethodResponseToClient;
import tk.slaaavyn.slavikserver.ws.models.response.RoomResponseToClient;

@Service
public class WsResponseServiceImpl implements WsResponseService {
    private final ClientSocketHandler clientSocketHandler;

    public WsResponseServiceImpl(ClientSocketHandler clientSocketHandler) {
        this.clientSocketHandler = clientSocketHandler;
    }

    @Override
    public void emmitDeviceToClient(Device device, boolean isDeviceOnline, MethodResponseToClient method) {
        if (device == null || method == null) return;

        DeviceResponseToClient response = new DeviceResponseToClient(DeviceDto.toDTO(device, isDeviceOnline), method);

        try {
            String message = new ObjectMapper().writeValueAsString(response);
            clientSocketHandler.emmitForAll(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void emmitRoomToClient(Room room, MethodResponseToClient method) {
        if (room == null || method == null) return;

        RoomResponseToClient response = new RoomResponseToClient(room, method);

        try {
            String message = new ObjectMapper().writeValueAsString(response);
            clientSocketHandler.emmitForAll(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
