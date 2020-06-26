package tk.slaaavyn.slavikhomebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikhomebackend.model.Role;
import tk.slaaavyn.slavikhomebackend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long userId);
    Optional<User> findUserByUsername(String username);
    List<User> findAllByRole(Role role);
}
