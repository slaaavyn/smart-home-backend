package tk.slaaavyn.slavikhomebackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikhomebackend.dto.DeviceDto;
import tk.slaaavyn.slavikhomebackend.model.Device;
import tk.slaaavyn.slavikhomebackend.model.Room;
import tk.slaaavyn.slavikhomebackend.service.WsResponseService;
import tk.slaaavyn.slavikhomebackend.ws.handler.ClientSocketHandler;
import tk.slaaavyn.slavikhomebackend.ws.models.response.DeviceResponseToClient;
import tk.slaaavyn.slavikhomebackend.ws.models.response.MethodResponseToClient;
import tk.slaaavyn.slavikhomebackend.ws.models.response.RoomResponseToClient;

import java.io.IOException;

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
        } catch (IOException e) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
