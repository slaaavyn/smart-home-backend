package tk.slaaavyn.slavikhomebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikhomebackend.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long roleId);
    Role findRoleByName(String roleName);
}
