package tk.slaaavyn.slavikserver.component;

import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.model.Temperature;
import tk.slaaavyn.slavikserver.model.device.component.BaseComponent;
import tk.slaaavyn.slavikserver.model.device.component.ThermometerComponent;
import tk.slaaavyn.slavikserver.service.DeviceService;
import tk.slaaavyn.slavikserver.service.TemperatureService;
import tk.slaaavyn.slavikserver.utils.dhtxx.DHT11;
import tk.slaaavyn.slavikserver.utils.dhtxx.DHTxx;
import tk.slaaavyn.slavikserver.utils.dhtxx.DhtData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Profile("!dev")
public class DhtDevice {
    private final Logger logger = LoggerFactory.getLogger(DhtDevice.class);

    private Date lastSaveTemperature;

    private final DeviceService deviceService;
    private final TemperatureService temperatureService;

    private final DHTxx dht11;
    private final Device dhtDevice;

    public DhtDevice(DeviceService deviceService, TemperatureService temperatureService) {
        this.deviceService = deviceService;
        this.temperatureService = temperatureService;

        dht11 = new DHT11(RaspiPin.GPIO_07);
        dhtDevice = connectDevice();

        try {
            dht11.init();
        } catch (Exception e) {
            logger.error("Dht init: ", e);
        }
    }

    @Scheduled(fixedRate = 16000)
    private void checkTemp() {
        if (dht11 == null || dhtDevice == null) {
            return;
        }

        try {
            DhtData dhtData = dht11.getData();
            dhtData.setTemperature(dhtData.getTemperature() - 2);

            ThermometerComponent component = (ThermometerComponent) dhtDevice.getComponents().get(0);

            Temperature temperature = new Temperature();
            temperature.setComponent(component);
            temperature.setTemperature(dhtData.getTemperature());
            temperature.setHumidity(dhtData.getHumidity());
            temperature.setCreationDate(new Date());

            saveTemperature(temperature);

            temperatureService.emmitToWebSocket(temperature);
            logger.info(dhtData.toString());
        } catch (Exception e) {
            logger.error("Dht checkTemp: ", e);
        }
    }


    private Device connectDevice() {
        ThermometerComponent component = new ThermometerComponent();
        component.setIndex(1);
        component.setHumidity(0);
        component.setTemperature(0);

        List<BaseComponent> components = new ArrayList<>();
        components.add(component);

        Device device = new Device();
        device.setUid("localDhtDevice");
        device.setComponents(components);

        return deviceService.connect(device);
    }

    private void saveTemperature(Temperature temperature) {
        if (lastSaveTemperature != null && new Date().getTime() < (lastSaveTemperature.getTime() + 60000)) {
            return;
        }

        temperature = temperatureService.save(temperature, dhtDevice.getComponents().get(0).getId());

        if(temperature != null) {
            lastSaveTemperature = temperature.getCreationDate();
        }
    }
}
