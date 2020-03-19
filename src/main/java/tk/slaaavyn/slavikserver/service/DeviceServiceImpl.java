package tk.slaaavyn.slavikserver.service;

import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.repo.DeviceRepository;
import tk.slaaavyn.slavikserver.service.impl.DeviceService;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final List<Device> onlineDevices = new ArrayList<>();

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device connect(Device incomingDevice) {
        if (incomingDevice == null || incomingDevice.getId() == null) return null;

        Device device = deviceRepository.findDeviceById(incomingDevice.getId());

        if (device == null) {
            device = deviceRepository.save(incomingDevice);
        } else {
            device.setComponents(incomingDevice.getComponents());
            device.setDescription(incomingDevice.getDescription());
            device = deviceRepository.save(device);
        }

        if (!isDeviceOnline(device)) {
            onlineDevices.add(device);
        }

        return device;
    }

    @Override
    public List<Device> disconnect(long deviceId) {
        onlineDevices.removeIf(device -> device.get_id().equals(deviceId));

        return onlineDevices;
    }

    @Override
    public Device getById(long deviceId) {
        return deviceRepository.findDeviceBy_id(deviceId);
    }

    @Override
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> getOnlineDevices() {
        return onlineDevices;
    }

    @Override
    public void removeDevice(long deviceId) {
        Device device = deviceRepository.findDeviceBy_id(deviceId);
        if (device == null) {
            return;
        }

        deviceRepository.delete(device);
    }

    private boolean isDeviceOnline(Device device) {
        return onlineDevices.stream().anyMatch(d -> d.getId().equals(device.getId()));
    }
}