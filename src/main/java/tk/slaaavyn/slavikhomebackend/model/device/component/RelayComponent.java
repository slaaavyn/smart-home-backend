package tk.slaaavyn.slavikhomebackend.model.device.component;

import tk.slaaavyn.slavikhomebackend.model.ComponentType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RELAY")
public class RelayComponent extends BaseComponent {

    @Column(name = "status")
    private Boolean status;

    @Column(name = "default_status")
    private Boolean defaultStatus;

    public RelayComponent() {
        super.setType(ComponentType.RELAY);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Boolean defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    @Override
    public String toString() {
        return "RelayComponent{" +
                "status=" + status +
                ", defaultStatus=" + defaultStatus +
                "} " + super.toString();
    }
}
