package tk.slaaavyn.slavikhomebackend.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikhomebackend.model.Device;
import tk.slaaavyn.slavikhomebackend.model.Room;
import tk.slaaavyn.slavikhomebackend.model.device.component.BaseComponent;
import tk.slaaavyn.slavikhomebackend.repo.ComponentRepository;
import tk.slaaavyn.slavikhomebackend.repo.DeviceRepository;
import tk.slaaavyn.slavikhomebackend.repo.RoomRepository;
import tk.slaaavyn.slavikhomebackend.service.DeviceService;
import tk.slaaavyn.slavikhomebackend.service.WsResponseService;
import tk.slaaavyn.slavikhomebackend.ws.models.response.MethodResponseToClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final List<Device> onlineDevices = new ArrayList<>();

    private final DeviceRepository deviceRepository;
    private final ComponentRepository componentRepository;
    private final RoomRepository roomRepository;
    private final WsResponseService wsResponseService;

    public DeviceServiceImpl(DeviceRepository deviceRepository, ComponentRepository componentRepository,
                             RoomRepository roomRepository, WsResponseService wsResponseService) {
        this.deviceRepository = deviceRepository;
        this.componentRepository = componentRepository;
        this.roomRepository = roomRepository;
        this.wsResponseService = wsResponseService;
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

        wsResponseService.emmitDeviceToClient(device, true, MethodResponseToClient.UPDATE);

        return device;
    }

    @Override
    public List<Device> disconnect(String deviceUid) {
        Device device = deviceRepository.findDeviceByUid(deviceUid);
        if (device == null) return onlineDevices;

        onlineDevices.removeIf(onlineDevice -> onlineDevice.getUid().equals(deviceUid));

        wsResponseService.emmitDeviceToClient(device, false, MethodResponseToClient.UPDATE);

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
    public Device updateDeviceDescription(long deviceId, String description) {
        Device device = deviceRepository.findDeviceById(deviceId);
        if (device == null) {
            return null;
        }

        device.setDescription(description != null ? description : device.getDescription());

        wsResponseService.emmitDeviceToClient(device, isDeviceOnline(device), MethodResponseToClient.UPDATE);

        return deviceRepository.save(device);
    }

    @Override
    public Device updateDeviceComponentDescription(long componentId, String description) {
        BaseComponent component = componentRepository.findBaseComponentById(componentId);
        if (component == null) {
            return null;
        }

        component.setDescription(description != null ? description : component.getDescription());
        componentRepository.save(component);

        Device device = getById(component.getDevice().getId());
        wsResponseService.emmitDeviceToClient(device, isDeviceOnline(device), MethodResponseToClient.UPDATE);

        return device;
    }

    @Override
    public Device setDeviceToRoom(long deviceId, long roomId) {
        Device device = deviceRepository.findDeviceById(deviceId);
        Room room = roomRepository.findRoomById(roomId);

        if (device == null || room == null) {
            return null;
        }

        device.setRoom(room);

        wsResponseService.emmitDeviceToClient(device, isDeviceOnline(device), MethodResponseToClient.UPDATE);

        return deviceRepository.save(device);
    }

    @Override
    public boolean removeDevice(long deviceId) {
        Device device = deviceRepository.findDeviceById(deviceId);
        if (device == null) {
            return false;
        }

        deviceRepository.delete(device);
        wsResponseService.emmitDeviceToClient(device, isDeviceOnline(device), MethodResponseToClient.DELETE);

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

            foundComponent.ifPresent(baseComponent -> component.setId(baseComponent.getId()));

            component.setDevice(device);
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