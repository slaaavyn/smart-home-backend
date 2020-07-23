package tk.slaaavyn.slavikhomebackend.service;

import tk.slaaavyn.slavikhomebackend.model.Device;

import java.util.List;

public interface DeviceService {
    void connect(Device device);

    void disconnect(String deviceUid);

    Device getById(long deviceId);

    List<Device> getAll();

    List<Device> getOnlineDevices();

    Device updateDeviceDescription(long deviceId, String description);

    Device updateDeviceComponentDescription(long componentId, String description);

    Device setDeviceToRoom(long deviceId, long roomId);

    void removeDevice(long deviceId);

    boolean isDeviceOnline(Device device);
}
