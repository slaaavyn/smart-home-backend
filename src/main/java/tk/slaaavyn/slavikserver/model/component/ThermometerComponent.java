package tk.slaaavyn.slavikserver.model.component;

import tk.slaaavyn.slavikserver.model.ComponentType;

public class ThermometerComponent extends BaseComponent {

    private double temperature;
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
