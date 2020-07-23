package tk.slaaavyn.slavikhomebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikhomebackend.model.Device;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findById(Long id);
    Optional<Device> findByUid(String id);
}
