package tk.slaaavyn.slavikhomebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikhomebackend.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findDeviceById(Long id);
    Device findDeviceByUid(String id);
}
