package tk.slaaavyn.slavikserver.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private Integer id;

    @Enumerated(EnumType.STRING)
    private ComponentType componentType;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }
}
