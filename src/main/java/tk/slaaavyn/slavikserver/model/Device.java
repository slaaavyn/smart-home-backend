package tk.slaaavyn.slavikserver.model;

import tk.slaaavyn.slavikserver.model.component.BaseComponent;

import javax.persistence.*;
import java.util.List;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String uid;

    String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEVICE_ID", nullable = false)
    List<BaseComponent> components;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BaseComponent> getComponents() {
        return components;
    }

    public void setComponents(List<BaseComponent> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", id='" + uid + '\'' +
                ", description='" + description + '\'' +
                ", components=" + components +
                '}';
    }
}
