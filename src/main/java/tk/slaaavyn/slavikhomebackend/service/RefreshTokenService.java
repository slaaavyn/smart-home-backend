package tk.slaaavyn.slavikhomebackend.service;

import tk.slaaavyn.slavikhomebackend.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken create(String username);

    boolean validate(RefreshToken refreshToken);
}
