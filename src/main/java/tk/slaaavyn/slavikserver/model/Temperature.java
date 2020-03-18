package tk.slaaavyn.slavikserver.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double temperature;

    Double humidity;

    Date creationDate;

    @OneToOne
    Device device;

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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", creationDate=" + creationDate +
                ", device=" + device +
                '}';
    }
}
