package tk.slaaavyn.slavikhomebackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikhomebackend.model.ComponentType;
import tk.slaaavyn.slavikhomebackend.model.Temperature;
import tk.slaaavyn.slavikhomebackend.model.device.component.ThermometerComponent;
import tk.slaaavyn.slavikhomebackend.repo.ComponentRepository;
import tk.slaaavyn.slavikhomebackend.repo.TemperatureRepository;
import tk.slaaavyn.slavikhomebackend.service.TemperatureService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService {
    private final Logger logger = LoggerFactory.getLogger(TemperatureServiceImpl.class);

    private final HashMap<Long, Temperature> temperatures = new HashMap<>();

    private final TemperatureRepository temperatureRepository;
    private final ComponentRepository componentRepository;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository, ComponentRepository componentRepository) {
        this.temperatureRepository = temperatureRepository;
        this.componentRepository = componentRepository;
    }

    @Override
    public Temperature save(Temperature temperature, String deviceUid, int componentIndex) {
        ThermometerComponent component = (ThermometerComponent) componentRepository
                .findBaseComponentByDevice_UidAndIndex(deviceUid, componentIndex);

        if (component == null || component.getType() != ComponentType.THERMOMETER
                || temperature == null || !isTimeToSave(component.getId())) {
            return null;
        }

        temperature.setComponent(component);
        temperature.setCreationDate(new Date());

        component.setTemperature(temperature.getTemperature());
        component.setHumidity(temperature.getHumidity());
        componentRepository.save(component);

        temperatures.put(component.getId(), temperature);

        return temperatureRepository.save(temperature);
    }

    @Override
    public List<Temperature> getAllByAfterDate(Long componentId, Date afterDate) {
        return temperatureRepository.findTemperaturesByComponent_IdAndCreationDateAfter(componentId, afterDate);
    }

    @Override
    public void removeAll() {
        temperatureRepository.deleteAll();
    }

    @Scheduled(cron = "* 0 1 * * *")
    private void removeOldData() {
        long ONE_MONTH = 2629800000L;
        Date dateMonthAgo = new Date(new Date().getTime() - ONE_MONTH);
        temperatureRepository.deleteAllByCreationDateBefore(dateMonthAgo);

        temperatures.clear();

        logger.info("Remove old temperature data");
    }

    private boolean isTimeToSave(long componentId) {
        if(!temperatures.containsKey(componentId)) {
            return true;
        } else return temperatures.containsKey(componentId)
                && (temperatures.get(componentId).getCreationDate().getTime() + 60000) <= new Date().getTime();
    }
}
