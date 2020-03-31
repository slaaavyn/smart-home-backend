package tk.slaaavyn.slavikhomebackend.ws.models.commands;

import com.google.gson.annotations.Expose;
import tk.slaaavyn.slavikhomebackend.model.ComponentType;

public class BaseCommand {
    @Expose
    private String uid;

    @Expose
    private Integer index;

    @Expose(serialize = false)
    private String token;

    @Expose
    private ComponentType type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BaseCommand{" +
                "uid='" + uid + '\'' +
                ", index=" + index +
                ", token='" + token + '\'' +
                ", type=" + type +
                '}';
    }
}
