package tk.slaaavyn.slavikhomebackend.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikhomebackend.exception.ConflictException;
import tk.slaaavyn.slavikhomebackend.exception.NotFoundException;
import tk.slaaavyn.slavikhomebackend.model.Room;
import tk.slaaavyn.slavikhomebackend.repo.DeviceRepository;
import tk.slaaavyn.slavikhomebackend.repo.RoomRepository;
import tk.slaaavyn.slavikhomebackend.service.RoomService;
import tk.slaaavyn.slavikhomebackend.service.WsResponseService;
import tk.slaaavyn.slavikhomebackend.ws.models.response.MethodResponseToClient;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;
    private final WsResponseService wsResponseService;

    public RoomServiceImpl(RoomRepository roomRepository, DeviceRepository deviceRepository,
                           WsResponseService wsResponseService) {
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
        this.wsResponseService = wsResponseService;
    }

    @Override
    public Room create(String roomName) {
        if(roomRepository.findByName(roomName).isPresent()) {
            throw new ConflictException("room with name: " + roomName + " already exist");
        }

        Room room = new Room();
        room.setName(roomName);

        room = roomRepository.save(room);

        wsResponseService.emmitRoomToClient(room, MethodResponseToClient.UPDATE);
        return room;
    }

    @Override
    public Room getById(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("room with id :" + roomId + " not found"));
    }

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room update(long roomId, String newRoomName) {
        Room room = getById(roomId);

        room.setName(newRoomName);

        room = roomRepository.save(room);

        wsResponseService.emmitRoomToClient(room, MethodResponseToClient.UPDATE);
        return room;
    }

    @Override
    public void delete(long roomId) {
        Room room = getById(roomId);

        room.getDeviceList().forEach(device -> device.setRoom(null));
        deviceRepository.saveAll(room.getDeviceList());

        roomRepository.delete(room);

        wsResponseService.emmitRoomToClient(room, MethodResponseToClient.DELETE);
    }
}
