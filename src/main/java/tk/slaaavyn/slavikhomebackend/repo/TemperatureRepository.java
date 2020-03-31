package tk.slaaavyn.slavikhomebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikhomebackend.model.Temperature;

import java.util.Date;
import java.util.List;

public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
    Temperature findTemperatureById (Long id);
    List<Temperature> findTemperaturesByComponent_IdAndCreationDateAfter (Long id, Date date);
    void deleteAllByCreationDateBefore (Date date);
}
