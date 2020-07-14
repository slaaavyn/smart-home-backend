package tk.slaaavyn.slavikhomebackend.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tk.slaaavyn.slavikhomebackend.config.EndpointConstants;
import tk.slaaavyn.slavikhomebackend.dto.user.UpdatePasswordDto;
import tk.slaaavyn.slavikhomebackend.dto.user.UpdateUserInfoDto;
import tk.slaaavyn.slavikhomebackend.dto.user.UserRequestDto;
import tk.slaaavyn.slavikhomebackend.model.Role;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    static final Logger logger =
            LoggerFactory.getLogger(UserControllerTest.class);

    private int mockUserId = 0;
    private final String mockUserLogin = "user";
    private final String mockUserPassword = "password";

    @BeforeEach
    public void before() throws Exception {
        MvcResult createdUserResult = createUser(mockUserLogin, mockUserPassword);
        mockUserId = JsonPath.read(createdUserResult.getResponse().getContentAsString(), "$.id");

        Assert.assertNotEquals(mockUserId, 0);
    }

    @AfterEach
    public void after() throws Exception {
        deleteUser(mockUserId);

        mockUserId = 0;
    }

    @Test
    void getAll() throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        mockMvc.perform(get(EndpointConstants.USER_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getUserById() throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        mockMvc.perform(get(EndpointConstants.USER_ENDPOINT).param("id", String.valueOf(mockUserId))
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateInfo() throws Exception {
        String userToken = extractToken(login(mockUserLogin, mockUserPassword).andReturn());

        String newFirstName = "newFirstName";
        String newLastName = "newLastName";

        UpdateUserInfoDto updateUserDto = new UpdateUserInfoDto();
        updateUserDto.setFirstName(newFirstName);
        updateUserDto.setLastName(newLastName);

        mockMvc.perform(put(EndpointConstants.USER_ENDPOINT + "/update-info" + "/" + mockUserId)
                .content(super.json(updateUserDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, userToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newFirstName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(newLastName));
    }

    @Test
    void updatePassword() throws Exception {
        String userToken = extractToken(login(mockUserLogin, mockUserPassword).andReturn());

        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setOldPassword(mockUserPassword);
        updatePasswordDto.setNewPassword("newPassword");

        mockMvc.perform(put(EndpointConstants.USER_ENDPOINT + "/update-password")
                .content(super.json(updatePasswordDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, userToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateRole() throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        mockMvc.perform(put(EndpointConstants.USER_ENDPOINT + "/update-role/" + mockUserId)
                .param("role", Role.ROLE_ADMIN.name())
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(Role.ROLE_ADMIN.name()));
    }

    private MvcResult createUser(String username, String password) throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        UserRequestDto createUserDto = new UserRequestDto();
        createUserDto.setUsername(username);
        createUserDto.setPassword(password);
        createUserDto.setFirstName("user");
        createUserDto.setLastName("user");
        createUserDto.setRole(Role.ROLE_USER);

        return mockMvc.perform(post(EndpointConstants.USER_ENDPOINT + "/create")
                .content(super.json(createUserDto))
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    void deleteUser(int userId) throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        mockMvc.perform(delete(EndpointConstants.USER_ENDPOINT + "/" + userId)
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}