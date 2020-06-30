package tk.slaaavyn.slavikhomebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import tk.slaaavyn.slavikhomebackend.dto.auth.AuthRequestDto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    protected String json(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }

    protected ResultActions login(String username, String password) throws Exception {
        final AuthRequestDto auth = new AuthRequestDto();
        auth.setUsername(username);
        auth.setPassword(password);

        return mockMvc.perform(
                post("/auth")
                        .content(json(auth))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
    }

    protected String extractRefreshToken(MvcResult result) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), "$.refreshToken");
    }
}
