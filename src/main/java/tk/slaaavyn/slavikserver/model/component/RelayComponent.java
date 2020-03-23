package tk.slaaavyn.slavikserver.model.component;

import tk.slaaavyn.slavikserver.model.ComponentType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RELAY")
public class RelayComponent extends BaseComponent {

    @Column(name = "status")
    private Boolean status;

    public RelayComponent() {
        super.setType(ComponentType.RELAY);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RelayComponent{" +
                "status=" + status +
                '}';
    }
}
