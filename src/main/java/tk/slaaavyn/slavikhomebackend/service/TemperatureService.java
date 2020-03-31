package tk.slaaavyn.slavikhomebackend.service;

import tk.slaaavyn.slavikhomebackend.model.Temperature;

import java.util.Date;
import java.util.List;

public interface TemperatureService {
    Temperature save(Temperature temperature, String deviceUid, int componentIndex) ;

    List<Temperature> getAllByAfterDate(Long componentId, Date afterDate);

    void removeAll();
}