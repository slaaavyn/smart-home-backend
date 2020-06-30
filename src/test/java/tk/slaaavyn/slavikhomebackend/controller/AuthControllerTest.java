package tk.slaaavyn.slavikhomebackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends AbstractControllerTest{

    @Autowired
    private AuthController authController;

    @Test
    public void auth() throws Exception {
        String jsonContent = "{ \"username\": \"root\", \"password\": \"toor\" }";

        mockMvc.perform(post("/auth")
                .content(jsonContent)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void refreshToken() throws Exception {
        String refreshToken = extractRefreshToken(login("root", "toor").andReturn());

        String jsonContent = "{ " +
                "\"username\": \"root\", " +
                "\"refreshToken\": \"" + refreshToken + "\" " +
                "}";

        mockMvc.perform(post("/auth/refresh-token")
                .content(jsonContent)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
