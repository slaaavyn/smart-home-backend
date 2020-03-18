package tk.slaaavyn.slavikserver.service;

import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikserver.utils.dhtxx.DHT11;
import tk.slaaavyn.slavikserver.utils.dhtxx.DHTxx;

@Service
@Profile("!dev")
public class DhtService {
    private DHTxx dht11 = new DHT11(RaspiPin.GPIO_07);

    private final Logger logger = LoggerFactory.getLogger(DhtService.class);

    public DhtService() {
        try {
            dht11.init();
            logger.info(dht11.toString());
        } catch (Exception e) {
            logger.error("Dht init: ", e);
        }
    }

    @Scheduled(fixedRate = 5000)
    private void checkTemp() {
        try {
            logger.info(dht11.getData().toString());
        } catch (Exception e) {
            logger.error("Dht checkTemp: ", e);
        }
    }
}
