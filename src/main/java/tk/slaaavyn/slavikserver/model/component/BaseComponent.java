package tk.slaaavyn.slavikserver.model.component;

import tk.slaaavyn.slavikserver.model.ComponentType;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer index;

    @Enumerated(EnumType.STRING)
    private ComponentType type;

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

    @Override
    public String toString() {
        return "BaseComponent{" +
                "id=" + id +
                ", index=" + index +
                ", type=" + type +
                '}';
    }
}
