package tk.slaaavyn.slavikserver.service;

import tk.slaaavyn.slavikserver.model.Temperature;

import java.util.Date;
import java.util.List;

public interface TemperatureService {
    void emmitToWebSocket(Temperature temperature);

    Temperature save(Temperature temperature, int componentIndex) ;

    List<Temperature> getAllByAfterDate(Long deviceId, Date afterDate);

    void removeAll();
}