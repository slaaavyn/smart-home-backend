package tk.slaaavyn.slavikhomebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.slavikhomebackend.config.EndpointConstants;
import tk.slaaavyn.slavikhomebackend.dto.DeviceDto;
import tk.slaaavyn.slavikhomebackend.dto.RoomDto;
import tk.slaaavyn.slavikhomebackend.model.Room;
import tk.slaaavyn.slavikhomebackend.service.DeviceService;
import tk.slaaavyn.slavikhomebackend.service.RoomService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.ROOM_ENDPOINT)
public class RoomController {

    private final RoomService roomService;
    private final DeviceService deviceService;

    public RoomController(RoomService roomService, DeviceService deviceService) {
        this.roomService = roomService;
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<RoomDto> create(@RequestParam("roomName") String roomName) {
        Room result = roomService.create(roomName);

        return ResponseEntity.ok(RoomDto.toDto(result, new ArrayList<>()));
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAll() {
        List<RoomDto> result = new ArrayList<>();

        roomService.getAll().forEach(room -> {
            List<DeviceDto> deviceList = new ArrayList<>();

            room.getDeviceList().forEach(device -> {
                boolean isDeviceOnline = deviceService.isDeviceOnline(device);
                DeviceDto deviceDto = DeviceDto.toDTO(device, isDeviceOnline);
                deviceList.add(deviceDto);
            });

            result.add(RoomDto.toDto(room, deviceList));
        });

        return ResponseEntity.ok(result);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<RoomDto> getDevicesInRoom(@PathVariable(name = "id") Long id) {
        Room result = roomService.getById(id);

        List<DeviceDto> deviceDtoList = new ArrayList<>();
        result.getDeviceList().forEach(device -> {
            boolean isDeviceOnline = deviceService.isDeviceOnline(device);
            DeviceDto deviceDto = DeviceDto.toDTO(device, isDeviceOnline);
            deviceDtoList.add(deviceDto);
        });

        return ResponseEntity.ok(RoomDto.toDto(result, deviceDtoList));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<RoomDto> updateRoomName(@PathVariable(name = "id") Long id,
                                                             @RequestParam("roomName") String roomName) {

        Room result = roomService.update(id, roomName);

        List<DeviceDto> deviceDtoList = new ArrayList<>();
        result.getDeviceList().forEach(device -> {
            boolean isDeviceOnline = deviceService.isDeviceOnline(device);
            DeviceDto deviceDto = DeviceDto.toDTO(device, isDeviceOnline);
            deviceDtoList.add(deviceDto);
        });

        return ResponseEntity.ok(RoomDto.toDto(result, deviceDtoList));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable(name = "id") Long id) {
        roomService.delete(id);

        return ResponseEntity.ok().build();
    }
}
