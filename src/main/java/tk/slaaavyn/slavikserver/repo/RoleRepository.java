package tk.slaaavyn.slavikserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikserver.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long roleId);
    Role findRoleByName(String roleName);
}
