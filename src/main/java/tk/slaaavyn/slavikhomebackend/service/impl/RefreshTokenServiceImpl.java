package tk.slaaavyn.slavikhomebackend.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.slaaavyn.slavikhomebackend.model.RefreshToken;
import tk.slaaavyn.slavikhomebackend.repo.RefreshTokenRepository;
import tk.slaaavyn.slavikhomebackend.security.SecurityConstants;
import tk.slaaavyn.slavikhomebackend.service.RefreshTokenService;

import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken create(String username) {
        RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenByUsername(username);
        if (refreshToken == null) {
            refreshToken = new RefreshToken();
        }

        Date expiredDate = new Date(new Date().getTime() + SecurityConstants.REFRESH_TOKEN_VALIDITY_TIME);
        refreshToken.setExpiredDate(expiredDate);
        refreshToken.setUsername(username);
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public boolean validate(RefreshToken refreshToken) {
        if (refreshToken == null || refreshToken.getUsername() == null
                || refreshToken.getToken() == null) {
            return false;
        }

        return refreshTokenRepository
                .findRefreshTokenByUsernameAndToken(refreshToken.getUsername(), refreshToken.getToken()) != null;
    }

    @Scheduled(cron = "0 0 1 * * *")
    private void removeOldTokens() {
        refreshTokenRepository.deleteRefreshTokensByExpiredDateBefore(new Date());
    }
}
