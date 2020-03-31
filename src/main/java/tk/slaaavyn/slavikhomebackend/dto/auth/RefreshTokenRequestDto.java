package tk.slaaavyn.slavikhomebackend.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.slaaavyn.slavikhomebackend.model.RefreshToken;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshTokenRequestDto {
    private String username;
    private String refreshToken;

    public RefreshToken fromDto() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setToken(this.refreshToken);

        return refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequestDto{" +
                "username='" + username + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
