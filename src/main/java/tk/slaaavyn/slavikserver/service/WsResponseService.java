package tk.slaaavyn.slavikserver.service;

import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.model.Room;
import tk.slaaavyn.slavikserver.ws.models.response.MethodResponseToClient;

public interface WsResponseService {
    void emmitDeviceToClient(Device device, boolean isDeviceOnline, MethodResponseToClient method);

    void emmitRoomToClient(Room room, MethodResponseToClient method);
}
