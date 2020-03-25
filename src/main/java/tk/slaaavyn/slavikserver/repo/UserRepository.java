package tk.slaaavyn.slavikserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikserver.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long userId);
    User findUserByUsername(String username);
    List<User> findAllByRole_Name(String roleName);
}
