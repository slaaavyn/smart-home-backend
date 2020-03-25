package tk.slaaavyn.slavikserver.service;

import tk.slaaavyn.slavikserver.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken create(String username);

    boolean validate(RefreshToken refreshToken);
}
