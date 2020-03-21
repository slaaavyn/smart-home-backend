package tk.slaaavyn.slavikserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikserver.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findDeviceById(Long id);
    Device findDeviceByUid(String id);
}
