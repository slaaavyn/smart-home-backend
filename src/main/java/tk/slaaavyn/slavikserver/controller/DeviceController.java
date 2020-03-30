package tk.slaaavyn.slavikserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.slavikserver.config.EndpointConstants;
import tk.slaaavyn.slavikserver.dto.DeviceDto;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.service.DeviceService;

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

        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean isDeviceOnline = deviceService.isDeviceOnline(result);

        return ResponseEntity.ok(DeviceDto.toDTO(result, isDeviceOnline));
    }

    @PutMapping(value = "/component/{id}")
    public ResponseEntity<DeviceDto> updateDeviceComponentDescription(@PathVariable(name = "id") Long id,
                                                                      @RequestParam("description") String description) {
        Device result = deviceService.updateDeviceComponentDescription(id, description);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean isDeviceOnline = deviceService.isDeviceOnline(result);

        return ResponseEntity.ok(DeviceDto.toDTO(result, isDeviceOnline));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<DeviceDto> updateDeviceDescription(@PathVariable(name = "id") Long id,
                                                             @RequestParam("description") String description) {

        Device result = deviceService.updateDeviceDescription(id, description);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean isDeviceOnline = deviceService.isDeviceOnline(result);

        return ResponseEntity.ok(DeviceDto.toDTO(result, isDeviceOnline));
    }

    @PutMapping(value = "{id}/set-room/{roomId}")
    public ResponseEntity<DeviceDto> setDeviceToRoom(@PathVariable(name = "id") Long id,
                                                             @PathVariable("roomId") Long roomId) {

        Device result = deviceService.setDeviceToRoom(id, roomId);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean isDeviceOnline = deviceService.isDeviceOnline(result);

        return ResponseEntity.ok(DeviceDto.toDTO(result, isDeviceOnline));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<DeviceDto> deleteDevice(@PathVariable(name = "id") Long id) {
        if (!deviceService.removeDevice(id)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
