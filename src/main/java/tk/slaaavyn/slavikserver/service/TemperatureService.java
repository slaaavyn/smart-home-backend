package tk.slaaavyn.slavikserver.service;

import tk.slaaavyn.slavikserver.model.Temperature;

import java.util.Date;
import java.util.List;

public interface TemperatureService {
    void emmitToWebSocket(Temperature temperature);

    Temperature save(Temperature temperature, long componentId) ;

    List<Temperature> getAllByAfterDate(Long componentId, Date afterDate);

    void removeAll();
}