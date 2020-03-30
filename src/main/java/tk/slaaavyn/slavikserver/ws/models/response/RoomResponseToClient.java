package tk.slaaavyn.slavikserver.ws.models.response;

import com.google.gson.annotations.Expose;
import tk.slaaavyn.slavikserver.model.Room;

public class RoomResponseToClient extends BaseResponseToClient {
    @Expose
    private Long roomId;

    @Expose
    private String roomName;

    public RoomResponseToClient(Room room, MethodResponseToClient method) {
        super.setMethod(method);
        super.setType(TypeResponseToClient.ROOM);

        roomId = room.getId();
        roomName = room.getName();
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
        return "RoomResponseToClient{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                "} " + super.toString();
    }
}
