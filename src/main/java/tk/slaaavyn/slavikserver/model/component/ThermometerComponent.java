package tk.slaaavyn.slavikserver.model.component;

import tk.slaaavyn.slavikserver.model.ComponentType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("THERMOMETER")
public class ThermometerComponent extends BaseComponent {
    @Column(name = "temperature")
    private double temperature;

    @Column(name = "humidity")
    private double humidity;

    public ThermometerComponent() {
        super.setType(ComponentType.THERMOMETER);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "ThermometerComponent{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
