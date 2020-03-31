package tk.slaaavyn.slavikhomebackend.dto;

import tk.slaaavyn.slavikhomebackend.model.Room;

import java.util.List;

public class RoomDto {
    private Long id;
    private String name;
    private List<DeviceDto> devices;

    public static RoomDto toDto(Room room, List<DeviceDto> deviceDtoList) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setDevices(deviceDtoList);

        return roomDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DeviceDto> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceDto> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", devices=" + devices +
                '}';
    }
}
