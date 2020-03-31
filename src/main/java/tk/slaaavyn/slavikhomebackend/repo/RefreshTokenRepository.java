package tk.slaaavyn.slavikhomebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.slavikhomebackend.model.RefreshToken;

import java.util.Date;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findRefreshTokenByUsername(String username);
    RefreshToken findRefreshTokenByUsernameAndToken(String username, String token);
    void deleteRefreshTokensByExpiredDateBefore(Date date);
}
