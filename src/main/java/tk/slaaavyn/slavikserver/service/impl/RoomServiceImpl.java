package tk.slaaavyn.slavikserver.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikserver.model.Room;
import tk.slaaavyn.slavikserver.repo.DeviceRepository;
import tk.slaaavyn.slavikserver.repo.RoomRepository;
import tk.slaaavyn.slavikserver.service.RoomService;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;

    public RoomServiceImpl(RoomRepository roomRepository, DeviceRepository deviceRepository) {
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Room create(String roomName) {
        Room room = roomRepository.findRoomByName(roomName);
        if (room != null) {
            return null;
        }

        room = new Room();
        room.setName(roomName);

        return roomRepository.save(room);
    }

    @Override
    public Room getById(long roomId) {
        return roomRepository.findRoomById(roomId);
    }

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room update(long roomId, String newRoomName) {
        Room room = roomRepository.findRoomById(roomId);
        if (room == null) {
            return null;
        }

        room.setName(newRoomName);

        return roomRepository.save(room);
    }

    @Override
    public boolean delete(long roomId) {
        Room room = roomRepository.findRoomById(roomId);
        if (room == null) {
            return false;
        }

        room.getDeviceList().forEach(device -> device.setRoom(null));
        deviceRepository.saveAll(room.getDeviceList());

        roomRepository.delete(room);
        return true;
    }
}
