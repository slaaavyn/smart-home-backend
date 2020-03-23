package tk.slaaavyn.slavikserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.slaaavyn.slavikserver.service.DeviceService;

@RestController
@RequestMapping(value = "/device")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteDevice(@PathVariable(name = "id") Long id) {
        if (!deviceService.removeDevice(id)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
