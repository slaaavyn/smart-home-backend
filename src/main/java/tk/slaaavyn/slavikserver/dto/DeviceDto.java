package tk.slaaavyn.slavikserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.slaaavyn.slavikserver.model.Device;
import tk.slaaavyn.slavikserver.model.component.BaseComponent;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDto {
    private Long id;
    private String uid;
    private String description;
    private Boolean isOnline;
    private List<BaseComponent> components;

    public static DeviceDto toDTO(Device device, boolean isOnline) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setUid(device.getUid());
        deviceDto.setDescription(device.getDescription());
        deviceDto.setOnline(isOnline);
        deviceDto.setComponents(device.getComponents());

        return deviceDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public List<BaseComponent> getComponents() {
        return components;
    }

    public void setComponents(List<BaseComponent> components) {
        this.components = components;
    }
}
