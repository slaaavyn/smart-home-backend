package tk.slaaavyn.slavikhomebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.slavikhomebackend.config.EndpointConstants;
import tk.slaaavyn.slavikhomebackend.dto.TemperatureDto;
import tk.slaaavyn.slavikhomebackend.service.TemperatureService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.TEMPERATURE_ENDPOINT)
public class TemperatureController {

    private final TemperatureService temperatureService;

    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @GetMapping
    public ResponseEntity<List<TemperatureDto>> getAllAfterDate(@RequestParam(name = "componentId") Long componentId,
                                                                @RequestParam("date") Long date) {
        if(componentId == null || date == null) {
            return ResponseEntity.badRequest().build();
        }

        List<TemperatureDto> result = new ArrayList<>();

        temperatureService.getAllByAfterDate(componentId, new Date(date))
                .forEach(temperature -> result.add(TemperatureDto.toDTO(temperature)));

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/remove-all")
    public ResponseEntity<TemperatureDto> removeAll() {
        temperatureService.removeAll();
        return ResponseEntity.ok().build();
    }
}
