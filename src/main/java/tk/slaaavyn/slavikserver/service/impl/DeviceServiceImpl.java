package tk.slaaavyn.slavikserver.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.model.component.BaseComponent;
import tk.slaaavyn.slavikserver.repo.ComponentRepository;
import tk.slaaavyn.slavikserver.repo.DeviceRepository;
import tk.slaaavyn.slavikserver.service.DeviceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final List<Device> onlineDevices = new ArrayList<>();

    private final DeviceRepository deviceRepository;
    private final ComponentRepository componentRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, ComponentRepository componentRepository) {
        this.deviceRepository = deviceRepository;
        this.componentRepository = componentRepository;
    }

    @Override
    public Device connect(Device incomingDevice) {
        if (incomingDevice == null || incomingDevice.getUid() == null) return null;

        Device device = deviceRepository.findDeviceByUid(incomingDevice.getUid());

        if (device != null) {
            device = saveExistDevice(device, incomingDevice.getComponents());
        } else {
            device = saveNewDevice(incomingDevice);
        }

        if (!isDeviceOnline(device)) {
            onlineDevices.add(device);
        }

        return device;
    }

    @Override
    public List<Device> disconnect(long deviceId) {
        onlineDevices.removeIf(device -> device.getId().equals(deviceId));

        return onlineDevices;
    }

    @Override
    public Device getById(long deviceId) {
        return deviceRepository.findDeviceById(deviceId);
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
    public Device updateDescription(long deviceId, String description) {
        Device device = deviceRepository.findDeviceById(deviceId);
        if (device == null) {
            return null;
        }

        device.setDescription(description != null ? description : device.getDescription());

        return deviceRepository.save(device);
    }

    @Override
    public boolean removeDevice(long deviceId) {
        Device device = deviceRepository.findDeviceById(deviceId);
        if (device == null) {
            return false;
        }

        deviceRepository.delete(device);
        return true;
    }

    @Override
    public boolean isDeviceOnline(Device device) {
        return onlineDevices.stream().anyMatch(d -> d.getUid().equals(device.getUid()));
    }

    private Device saveNewDevice(Device device) {
        device = deviceRepository.save(device);

        for (BaseComponent component : device.getComponents()) {
            component.setDevice(device);
        }

        componentRepository.saveAll(device.getComponents());

        return device;
    }

    private Device saveExistDevice(Device device, List<BaseComponent> incomingComponents) {
        device.setComponents(updateDeviceComponents(device, incomingComponents));
        return  deviceRepository.save(device);
    }

    private List<BaseComponent> updateDeviceComponents(Device device, List<BaseComponent> incomingComponents) {
        List<BaseComponent> updatedComponents = new ArrayList<>();
        if (device == null || incomingComponents == null) {
            return updatedComponents;
        }

        incomingComponents.forEach(component -> {
            if(component.getIndex() == null || component.getType() == null) {
                return;
            }

            Optional<BaseComponent> foundComponent
                    = device.getComponents().stream().filter(c -> c.getIndex().equals(component.getIndex())).findAny();

            foundComponent.ifPresent(baseComponent -> {
                component.setId(baseComponent.getId());
                component.setDevice(device);
            });

            updatedComponents.add(component);
        });

        removeNotUsedComponents(device.getComponents(), updatedComponents);
        return updatedComponents;
    }

    private void removeNotUsedComponents(List<BaseComponent> oldComponents, List<BaseComponent> updatedComponents) {
        oldComponents.forEach(component -> {
            Optional<BaseComponent> foundComponent
                    = updatedComponents.stream().filter(c -> c.getIndex().equals(component.getIndex())).findAny();

            if(!foundComponent.isPresent()) {
                componentRepository.delete(component);
            }
        });
    }
}