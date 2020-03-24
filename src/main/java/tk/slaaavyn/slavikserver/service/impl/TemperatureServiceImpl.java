package tk.slaaavyn.slavikserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikserver.model.Temperature;
import tk.slaaavyn.slavikserver.model.component.ThermometerComponent;
import tk.slaaavyn.slavikserver.repo.ComponentRepository;
import tk.slaaavyn.slavikserver.repo.TemperatureRepository;
import tk.slaaavyn.slavikserver.service.TemperatureService;
import tk.slaaavyn.slavikserver.ws.TemperatureSocketHandler;

import java.util.Date;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService {
    private final Logger logger = LoggerFactory.getLogger(TemperatureServiceImpl.class);

    private final TemperatureRepository temperatureRepository;
    private final ComponentRepository componentRepository;
    private final TemperatureSocketHandler temperatureSocketHandler;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository, ComponentRepository componentRepository,
                                  TemperatureSocketHandler temperatureSocketHandler) {
        this.temperatureRepository = temperatureRepository;
        this.componentRepository = componentRepository;
        this.temperatureSocketHandler = temperatureSocketHandler;
    }

    @Override
    public void emmitToWebSocket(Temperature temperature) {
        if(isTemperatureValid(temperature)) {
            temperatureSocketHandler.sendMessageForAll(temperature);
        }
    }

    @Override
    public Temperature save(Temperature temperature, long componentId) {
        ThermometerComponent component = (ThermometerComponent) componentRepository.findBaseComponentById(componentId);

        if (!isTemperatureValid(temperature) || component == null) {
            return null;
        }

        temperature.setComponent(component);
        temperature.setCreationDate(new Date());

        component.setTemperature(temperature.getTemperature());
        component.setHumidity(temperature.getHumidity());
        componentRepository.save(component);

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
        logger.info("Remove old temperature data");
    }

    private boolean isTemperatureValid(Temperature temperature) {
        return temperature != null && temperature.getTemperature() != null
                && temperature.getComponent() != null && temperature.getComponent().getId() != null;
    }
}
