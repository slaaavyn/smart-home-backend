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
    private Long roomId;
    private String roomName;

    public static DeviceDto toDTO(Device device, boolean isOnline) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setUid(device.getUid());
        deviceDto.setDescription(device.getDescription());
        deviceDto.setOnline(isOnline);
        deviceDto.setComponents(device.getComponents());
        deviceDto.setRoomId(device.getRoom() != null ? device.getRoom().getId() : null);
        deviceDto.setRoomName(device.getRoom() != null ? device.getRoom().getName() : null);

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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", description='" + description + '\'' +
                ", isOnline=" + isOnline +
                ", components=" + components +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
