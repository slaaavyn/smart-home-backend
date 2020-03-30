package tk.slaaavyn.slavikserver.model.device.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tk.slaaavyn.slavikserver.model.ComponentType;
import tk.slaaavyn.slavikserver.model.Device;

import javax.persistence.*;

@Entity
@Table(name = "component")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class BaseComponent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "index")
    private Integer index;

    @Column(name = "type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ComponentType type;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setType(ComponentType componentType) {
        this.type = componentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "BaseComponent{" +
                "id=" + id +
                ", index=" + index +
                ", type=" + type +
                ", description=" + description +
                ", deviceId=" + device.getId() +
                '}';
    }
}

