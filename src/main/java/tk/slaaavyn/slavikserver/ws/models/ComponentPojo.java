package tk.slaaavyn.slavikserver.ws.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import tk.slaaavyn.slavikserver.model.ComponentType;
import tk.slaaavyn.slavikserver.model.device.component.BaseComponent;
import tk.slaaavyn.slavikserver.model.device.component.RelayComponent;
import tk.slaaavyn.slavikserver.model.device.component.ThermometerComponent;

public class ComponentPojo {
    @SerializedName("index")
    @Expose
    private Integer index;

    @SerializedName("type")
    @Expose
    private ComponentType type;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("temperature")
    @Expose
    private Double temperature;

    @SerializedName("humidity")
    @Expose
    private Double humidity;

    public BaseComponent fromPojo() {
        switch (type) {
            case RELAY:
                return toRelay();

            case THERMOMETER:
                return toThermometer();
        }

        return null;
    }

    private RelayComponent toRelay() {
        RelayComponent component = new RelayComponent();
        component.setType(ComponentType.RELAY);
        component.setIndex(index);
        component.setStatus(status);

        return component;
    }

    private ThermometerComponent toThermometer() {
        ThermometerComponent component = new ThermometerComponent();
        component.setType(ComponentType.THERMOMETER);
        component.setIndex(index);
        component.setTemperature(temperature);
        component.setHumidity(humidity);

        return component;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

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
}
