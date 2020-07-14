package tk.slaaavyn.slavikhomebackend.service;

import tk.slaaavyn.slavikhomebackend.model.Room;

import java.util.List;

public interface RoomService {
    Room create(String roomName);

    Room getById(long roomId);

    List<Room> getAll();

    Room update(long roomId, String newRoomName);

    void delete(long roomId);
}
