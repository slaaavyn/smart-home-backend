package tk.slaaavyn.slavikserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikserver.model.component.BaseComponent;

public interface ComponentRepository extends JpaRepository<BaseComponent, Long> {
    BaseComponent findBaseComponentBy_id(Long id);
    BaseComponent findBaseComponentById(Integer id);
}
