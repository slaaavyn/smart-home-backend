package tk.slaaavyn.slavikhomebackend.model;

import tk.slaaavyn.slavikhomebackend.model.device.component.BaseComponent;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private String uid;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "device", orphanRemoval = true)
    List<BaseComponent> components;

    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "room_id")
    Room room;

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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", description='" + description + '\'' +
                ", components=" + components +
                ", roomId=" + (room != null ? room.getId() : null) +
                '}';
    }
}
