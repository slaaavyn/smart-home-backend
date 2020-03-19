package tk.slaaavyn.slavikserver.component;

import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.model.component.BaseComponent;
import tk.slaaavyn.slavikserver.model.component.ThermometerComponent;
import tk.slaaavyn.slavikserver.service.impl.DeviceService;
import tk.slaaavyn.slavikserver.utils.dhtxx.DHT11;
import tk.slaaavyn.slavikserver.utils.dhtxx.DHTxx;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("!dev")
public class DhtDevice {
    private final Logger logger = LoggerFactory.getLogger(DhtDevice.class);

    private final DeviceService deviceService;

    private final DHTxx dht11;
    private final Device dhtDevice;

    public DhtDevice(DeviceService deviceService) {
        this.deviceService = deviceService;

        dht11 = new DHT11(RaspiPin.GPIO_07);
        dhtDevice = connectDevice();

        try {
            dht11.init();
            logger.info(dht11.toString());
            connectDevice();
        } catch (Exception e) {
            logger.error("Dht init: ", e);
        }
    }

    @Scheduled(fixedRate = 6000)
    private void checkTemp() {
        if (dht11 == null || dhtDevice == null) {
            return;
        }

        try {
            logger.info(dht11.getData().toString());
        } catch (Exception e) {
            logger.error("Dht checkTemp: ", e);
        }
    }


    private Device connectDevice() {
        ThermometerComponent component = new ThermometerComponent();
        component.setId(1);
        component.setHumidity(0);
        component.setTemperature(0);

        List<BaseComponent> components = new ArrayList<>();
        components.add(component);

        Device device = new Device();
        device.setId("localDhtDevice");
        device.setComponents(components);

        return deviceService.connect(device);
    }
}
