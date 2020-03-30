package tk.slaaavyn.slavikserver.ws.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.model.device.component.BaseComponent;

import java.util.ArrayList;
import java.util.List;

public class DevicePojo {
    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("token")
    @Expose(serialize = false)
    private String token;

    @SerializedName("components")
    @Expose
    private List<ComponentPojo> components = null;

    public Device fromPojo() {
        Device device = new Device();
        device.setUid(uid);
        device.setComponents(new ArrayList<>());

        components.forEach(componentPojo -> {
            BaseComponent baseComponent = componentPojo.fromPojo();
            if(baseComponent != null) {
                device.getComponents().add(componentPojo.fromPojo());
            }
        });

        return device;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ComponentPojo> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentPojo> components) {
        this.components = components;
    }
}
