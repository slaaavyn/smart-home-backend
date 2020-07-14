package tk.slaaavyn.slavikhomebackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import tk.slaaavyn.slavikhomebackend.dto.auth.AuthRequestDto;
import tk.slaaavyn.slavikhomebackend.dto.auth.RefreshTokenRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseControllerTest {

    @Test
    public void auth() throws Exception {
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername(defaultAdminUsername);
        authRequestDto.setPassword(defaultAdminPassword);

        mockMvc.perform(post("/auth")
                .content(super.json(authRequestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void refreshToken() throws Exception {
        String refreshToken = extractRefreshToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        RefreshTokenRequestDto refreshTokenDto = new RefreshTokenRequestDto();
        refreshTokenDto.setUsername(defaultAdminUsername);
        refreshTokenDto.setRefreshToken(refreshToken);

        mockMvc.perform(post("/auth/refresh-token")
                .content(super.json(refreshTokenDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
