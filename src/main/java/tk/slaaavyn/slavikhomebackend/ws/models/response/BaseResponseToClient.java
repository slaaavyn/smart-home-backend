package tk.slaaavyn.slavikhomebackend.ws.models.response;

import com.google.gson.annotations.Expose;

public abstract class BaseResponseToClient {
    @Expose
    private TypeResponseToClient type;

    @Expose
    private MethodResponseToClient method;

    public TypeResponseToClient getType() {
        return type;
    }

    public void setType(TypeResponseToClient type) {
        this.type = type;
    }

    public MethodResponseToClient getMethod() {
        return method;
    }

    public void setMethod(MethodResponseToClient method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "BaseResponseToClient{" +
                "type=" + type +
                ", method=" + method +
                '}';
    }
}
