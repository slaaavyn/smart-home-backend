package tk.slaaavyn.slavikserver.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long _id;

    String id;

    String description;

    @OneToMany(targetEntity = BaseComponent.class)
    List<BaseComponent> components;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "_id=" + _id +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", components=" + components +
                '}';
    }
}
