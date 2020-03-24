package tk.slaaavyn.slavikserver.service;

import tk.slaaavyn.slavikserver.model.Device;

import java.util.List;

public interface DeviceService {
    Device connect(Device device);

    List<Device> disconnect(long deviceId);

    Device getById(long deviceId);

    List<Device> getAll();

    List<Device> getOnlineDevices();

    Device updateDescription(long deviceId, String description);

    Device updateDeviceComponentDescription(long componentId, String description);

    boolean removeDevice(long deviceId);

    boolean isDeviceOnline(Device device);
}
