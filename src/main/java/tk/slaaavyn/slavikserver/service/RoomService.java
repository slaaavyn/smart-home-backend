package tk.slaaavyn.slavikserver.service;

import tk.slaaavyn.slavikserver.model.Room;

import java.util.List;

public interface RoomService {
    Room create(String roomName);

    Room getById(long roomId);

    List<Room> getAll();

    Room update(long roomId, String newRoomName);

    boolean delete(long roomId);
}
