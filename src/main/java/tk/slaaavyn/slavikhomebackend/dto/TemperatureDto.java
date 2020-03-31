package tk.slaaavyn.slavikhomebackend.dto;

import tk.slaaavyn.slavikhomebackend.model.Temperature;

import java.util.Date;

public class TemperatureDto {
    private Long id;
    private Double temperature;
    private Double humidity;
    private Date creationDate;
    private String description;
    private Long deviceId;
    private String deviceUid;
    private Long componentId;

    public static TemperatureDto toDTO(Temperature temperature) {
        TemperatureDto temperatureDto = new TemperatureDto();
        temperatureDto.setId(temperature.getId());
        temperatureDto.setTemperature(temperature.getTemperature());
        temperatureDto.setHumidity(temperature.getHumidity());
        temperatureDto.setCreationDate(temperature.getCreationDate());
        temperatureDto.setDescription(temperature.getComponent().getDescription());
        temperatureDto.setDeviceId(temperature.getComponent().getDevice().getId());
        temperatureDto.setDeviceUid(temperature.getComponent().getDevice().getUid());
        temperatureDto.setComponentId(temperature.getComponent().getId());

        return temperatureDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    @Override
    public String toString() {
        return "TemperatureDto{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                ", deviceId=" + deviceId +
                ", deviceUid='" + deviceUid + '\'' +
                ", componentId=" + componentId +
                '}';
    }
}
