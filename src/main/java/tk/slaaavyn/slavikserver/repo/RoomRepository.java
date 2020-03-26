package tk.slaaavyn.slavikserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikserver.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findRoomById(Long id);
    Room findRoomByName(String roomName);
}
