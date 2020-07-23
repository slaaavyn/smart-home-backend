package tk.slaaavyn.slavikhomebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.slavikhomebackend.config.EndpointConstants;
import tk.slaaavyn.slavikhomebackend.dto.DeviceDto;
import tk.slaaavyn.slavikhomebackend.model.Device;
import tk.slaaavyn.slavikhomebackend.service.DeviceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.DEVICE_ENDPOINT)
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        List<DeviceDto> result = new ArrayList<>();

        deviceService.getAll().forEach(device -> {
            boolean isDeviceOnline = deviceService.isDeviceOnline(device);
            DeviceDto deviceDto = DeviceDto.toDTO(device, isDeviceOnline);

            result.add(deviceDto);
        });

        return ResponseEntity.ok(result);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<DeviceDto> getDevice(@PathVariable(name = "id") Long id) {
        Device result = deviceService.getById(id);

        boolean isDeviceOnline = deviceService.isDeviceOnline(result);

        return ResponseEntity.ok(DeviceDto.toDTO(result, isDeviceOnline));
    }

    @PutMapping(value = "/component/{id}")
    public ResponseEntity<DeviceDto> updateDeviceComponentDescription(@PathVariable(name = "id") Long id,
                                                                      @RequestParam("description") String description) {
        Device result = deviceService.updateDeviceComponentDescription(id, description);

        boolean isDeviceOnline = deviceService.isDeviceOnline(result);

        return ResponseEntity.ok(DeviceDto.toDTO(result, isDeviceOnline));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<DeviceDto> updateDeviceDescription(@PathVariable(name = "id") Long id,
                                                             @RequestParam("description") String description) {

        Device result = deviceService.updateDeviceDescription(id, description);

        boolean isDeviceOnline = deviceService.isDeviceOnline(result);

        return ResponseEntity.ok(DeviceDto.toDTO(result, isDeviceOnline));
    }

    @PutMapping(value = "{id}/set-room/{roomId}")
    public ResponseEntity<DeviceDto> setDeviceToRoom(@PathVariable(name = "id") Long id,
                                                             @PathVariable("roomId") Long roomId) {

        Device result = deviceService.setDeviceToRoom(id, roomId);

        boolean isDeviceOnline = deviceService.isDeviceOnline(result);

        return ResponseEntity.ok(DeviceDto.toDTO(result, isDeviceOnline));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteDevice(@PathVariable(name = "id") Long id) {
        deviceService.removeDevice(id);
        return ResponseEntity.ok().build();
    }
}
