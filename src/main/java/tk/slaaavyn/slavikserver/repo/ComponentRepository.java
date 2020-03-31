package tk.slaaavyn.slavikserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikserver.model.device.component.BaseComponent;

public interface ComponentRepository extends JpaRepository<BaseComponent, Long> {
    BaseComponent findBaseComponentById(Long id);
    BaseComponent findBaseComponentByDevice_UidAndIndex(String deviceUid, int deviceIndex);
}
