package tk.slaaavyn.slavikhomebackend.service;

import tk.slaaavyn.slavikhomebackend.model.Device;
import tk.slaaavyn.slavikhomebackend.model.Room;
import tk.slaaavyn.slavikhomebackend.ws.models.response.MethodResponseToClient;

public interface WsResponseService {
    void emmitDeviceToClient(Device device, boolean isDeviceOnline, MethodResponseToClient method);

    void emmitRoomToClient(Room room, MethodResponseToClient method);
}
