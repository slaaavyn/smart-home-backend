package tk.slaaavyn.slavikserver.ws.models.commands;

import com.google.gson.annotations.Expose;
import tk.slaaavyn.slavikserver.model.ComponentType;

public class RelayCommand extends BaseCommand {

    @Expose
    private boolean status;

    public RelayCommand() {
        super.setType(ComponentType.RELAY);
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RelayCommand{" +
                "status=" + status +
                "} " + super.toString();
    }
}
