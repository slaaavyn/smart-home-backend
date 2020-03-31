package tk.slaaavyn.slavikhomebackend.component;

import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.slaaavyn.slavikhomebackend.model.ComponentType;
import tk.slaaavyn.slavikhomebackend.model.Device;
import tk.slaaavyn.slavikhomebackend.model.Temperature;
import tk.slaaavyn.slavikhomebackend.model.device.component.BaseComponent;
import tk.slaaavyn.slavikhomebackend.model.device.component.ThermometerComponent;
import tk.slaaavyn.slavikhomebackend.service.DeviceService;
import tk.slaaavyn.slavikhomebackend.service.TemperatureService;
import tk.slaaavyn.slavikhomebackend.utils.dhtxx.DHT11;
import tk.slaaavyn.slavikhomebackend.utils.dhtxx.DHTxx;
import tk.slaaavyn.slavikhomebackend.utils.dhtxx.DhtData;
import tk.slaaavyn.slavikhomebackend.ws.handler.TemperatureSocketHandler;
import tk.slaaavyn.slavikhomebackend.ws.models.commands.ThermometerCommand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Profile("pi")
public class DhtDevice {
    private final Logger logger = LoggerFactory.getLogger(DhtDevice.class);

    private final DeviceService deviceService;
    private final TemperatureService temperatureService;
    private final TemperatureSocketHandler temperatureSocketHandler;

    private final DHTxx dht11;
    private final Device dhtDevice;

    public DhtDevice(DeviceService deviceService, TemperatureService temperatureService,
                     TemperatureSocketHandler temperatureSocketHandler) {
        this.deviceService = deviceService;
        this.temperatureService = temperatureService;
        this.temperatureSocketHandler = temperatureSocketHandler;

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

            temperatureService.save(temperature, dhtDevice.getUid(), component.getIndex());

            ThermometerCommand thermometerCommand = new ThermometerCommand();
            thermometerCommand.setUid(dhtDevice.getUid());
            thermometerCommand.setIndex(component.getIndex());
            thermometerCommand.setType(ComponentType.THERMOMETER);
            thermometerCommand.setTemperature(dhtData.getTemperature());
            thermometerCommand.setHumidity(dhtData.getHumidity());

            temperatureSocketHandler.emmitForAll(thermometerCommand);
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
}
