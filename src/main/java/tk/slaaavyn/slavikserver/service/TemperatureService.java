package tk.slaaavyn.slavikserver.service;

import tk.slaaavyn.slavikserver.model.Temperature;

import java.util.Date;
import java.util.List;

public interface TemperatureService {
    Temperature save(Temperature temperature, String deviceUid, int componentIndex) ;

    List<Temperature> getAllByAfterDate(Long componentId, Date afterDate);

    void removeAll();
}