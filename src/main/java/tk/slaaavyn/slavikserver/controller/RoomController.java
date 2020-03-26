package tk.slaaavyn.slavikserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.slavikserver.config.EndpointConstants;
import tk.slaaavyn.slavikserver.dto.DeviceDto;
import tk.slaaavyn.slavikserver.dto.RoomDto;
import tk.slaaavyn.slavikserver.model.Room;
import tk.slaaavyn.slavikserver.service.DeviceService;
import tk.slaaavyn.slavikserver.service.RoomService;

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
        if(result == null) {
            return ResponseEntity.badRequest().build();
        }

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
    public ResponseEntity<RoomDto> getDevice(@PathVariable(name = "id") Long id) {
        Room result = roomService.getById(id);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

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
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        List<DeviceDto> deviceDtoList = new ArrayList<>();
        result.getDeviceList().forEach(device -> {
            boolean isDeviceOnline = deviceService.isDeviceOnline(device);
            DeviceDto deviceDto = DeviceDto.toDTO(device, isDeviceOnline);
            deviceDtoList.add(deviceDto);
        });

        return ResponseEntity.ok(RoomDto.toDto(result, deviceDtoList));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<DeviceDto> deleteDevice(@PathVariable(name = "id") Long id) {
        if (!roomService.delete(id)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
