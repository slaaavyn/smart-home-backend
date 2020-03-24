package tk.slaaavyn.slavikserver.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tk.slaaavyn.slavikserver.model.component.BaseComponent;
import tk.slaaavyn.slavikserver.model.component.ThermometerComponent;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "temperature")
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "humidity")
    Double humidity;

    @Column(name = "creation_date")
    Date creationDate;

    @ManyToOne(targetEntity = BaseComponent.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "component_id", nullable = false)
    BaseComponent component;

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

    public BaseComponent getComponent() {
        return component;
    }

    public void setComponent(BaseComponent component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", creationDate=" + creationDate +
                ", description=" + component.getDescription() +
                ", componentId=" + component.getId() +
                ", deviceId=" + component.getDevice().getId() +
                '}';
    }
}
