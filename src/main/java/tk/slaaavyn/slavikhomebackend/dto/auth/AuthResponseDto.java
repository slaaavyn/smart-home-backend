package tk.slaaavyn.slavikhomebackend.dto.auth;

import tk.slaaavyn.slavikhomebackend.dto.user.UserResponseDto;
import tk.slaaavyn.slavikhomebackend.model.RefreshToken;

import java.util.Date;

public class AuthResponseDto {
    private UserResponseDto user;
    private String token;
    private Date tokenExpired;
    private String refreshToken;
    private Date refreshTokenExpired;

    public static AuthResponseDto toDto(UserResponseDto user, String token,
                                        Date tokenExpired, RefreshToken refreshToken) {
        AuthResponseDto responseDto = new AuthResponseDto();
        responseDto.setUser(user);
        responseDto.setToken(token);
        responseDto.setTokenExpired(tokenExpired);
        responseDto.setRefreshToken(refreshToken.getToken());
        responseDto.setRefreshTokenExpired(refreshToken.getExpiredDate());

        return responseDto;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(Date tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getRefreshTokenExpired() {
        return refreshTokenExpired;
    }

    public void setRefreshTokenExpired(Date refreshTokenExpired) {
        this.refreshTokenExpired = refreshTokenExpired;
    }

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "user=" + user +
                ", token='" + token + '\'' +
                ", tokenExpired=" + tokenExpired +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshTokenExpired=" + refreshTokenExpired +
                '}';
    }
}
