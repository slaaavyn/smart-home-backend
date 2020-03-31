package tk.slaaavyn.slavikhomebackend.service;

import tk.slaaavyn.slavikhomebackend.model.Device;

import java.util.List;

public interface DeviceService {
    Device connect(Device device);

    List<Device> disconnect(String deviceUid);

    Device getById(long deviceId);

    List<Device> getAll();

    List<Device> getOnlineDevices();

    Device updateDeviceDescription(long deviceId, String description);

    Device updateDeviceComponentDescription(long componentId, String description);

    Device setDeviceToRoom(long deviceId, long roomId);

    boolean removeDevice(long deviceId);

    boolean isDeviceOnline(Device device);
}
