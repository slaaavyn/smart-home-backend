package tk.slaaavyn.slavikhomebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikhomebackend.model.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(Long id);
    Optional<Room> findByName(String roomName);
}
