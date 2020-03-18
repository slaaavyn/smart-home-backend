package tk.slaaavyn.slavikserver.component;

import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.slaaavyn.slavikserver.utils.dhtxx.DHT11;
import tk.slaaavyn.slavikserver.utils.dhtxx.DHTxx;

@Component
@Profile("!dev")
public class DhtDevice {
    private final Logger logger = LoggerFactory.getLogger(DhtDevice.class);

    private DHTxx dht11;

    public DhtDevice() {
       dht11 = new DHT11(RaspiPin.GPIO_07);

        try {
            dht11.init();
            logger.info(dht11.toString());
        } catch (Exception e) {
            logger.error("Dht init: ", e);
        }
    }

    @Scheduled(fixedRate = 6000)
    private void checkTemp() {
        if (dht11 == null) {
            return;
        }

        try {
            logger.info(dht11.getData().toString());
        } catch (Exception e) {
            logger.error("Dht checkTemp: ", e);
        }
    }
}
