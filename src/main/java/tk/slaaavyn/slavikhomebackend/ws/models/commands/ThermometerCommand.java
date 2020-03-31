package tk.slaaavyn.slavikhomebackend.ws.models.commands;

import com.google.gson.annotations.Expose;

public class ThermometerCommand extends BaseCommand {
    @Expose
    private Double temperature;

    @Expose
    private Double humidity;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "ThermometerCommand{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                "} " + super.toString();
    }
}
