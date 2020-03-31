package tk.slaaavyn.slavikhomebackend.ws.models.response;

import com.google.gson.annotations.Expose;
import tk.slaaavyn.slavikhomebackend.dto.DeviceDto;

public class DeviceResponseToClient extends BaseResponseToClient {

    @Expose
    private DeviceDto device;

    public DeviceResponseToClient(DeviceDto device, MethodResponseToClient method) {
        super.setType(TypeResponseToClient.DEVICE);
        super.setMethod(method);
        this.device = device;
    }

    public DeviceDto getDevice() {
        return device;
    }

    public void setDevice(DeviceDto device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "DeviceResponseToClient{" +
                "device=" + device +
                "} " + super.toString();
    }
}
