package tk.slaaavyn.slavikserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikserver.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findDeviceBy_id(Long id);
    Device findDeviceById(String id);
}
