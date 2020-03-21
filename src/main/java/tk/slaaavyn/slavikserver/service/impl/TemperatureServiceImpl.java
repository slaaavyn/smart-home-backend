package tk.slaaavyn.slavikserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikserver.model.Temperature;
import tk.slaaavyn.slavikserver.repo.TemperatureRepository;
import tk.slaaavyn.slavikserver.service.TemperatureService;
import tk.slaaavyn.slavikserver.ws.TemperatureSocketHandler;

import java.util.Date;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService {
    private final Logger logger = LoggerFactory.getLogger(TemperatureServiceImpl.class);

    private final TemperatureRepository temperatureRepository;
    private final TemperatureSocketHandler temperatureSocketHandler;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository,
                                  TemperatureSocketHandler temperatureSocketHandler) {
        this.temperatureRepository = temperatureRepository;
        this.temperatureSocketHandler = temperatureSocketHandler;
    }

    @Override
    public void emmitToWebSocket(Temperature temperature) {
        if(isTemperatureValid(temperature)) {
            temperatureSocketHandler.sendMessageForAll(temperature);
        }
    }

    @Override
    public Temperature save(Temperature temperature) {
        if (!isTemperatureValid(temperature)) {
            return null;
        }

        temperature.setCreationDate(new Date());

        return temperatureRepository.save(temperature);
    }

    @Override
    public List<Temperature> getAllByAfterDate(Long deviceId, Date afterDate) {
        return temperatureRepository.findTemperaturesByDevice_IdAndCreationDateAfter(deviceId, afterDate);
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
                && temperature.getDevice() != null && temperature.getDevice().getId() != null;
    }
}
