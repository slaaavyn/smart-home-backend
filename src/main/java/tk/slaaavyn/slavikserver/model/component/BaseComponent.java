package tk.slaaavyn.slavikserver.model.component;

import tk.slaaavyn.slavikserver.model.ComponentType;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private Integer id;

    @Enumerated(EnumType.STRING)
    private ComponentType type;

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

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType componentType) {
        this.type = componentType;
    }
}
