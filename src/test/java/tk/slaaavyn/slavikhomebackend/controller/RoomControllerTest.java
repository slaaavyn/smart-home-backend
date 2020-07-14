package tk.slaaavyn.slavikhomebackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tk.slaaavyn.slavikhomebackend.config.EndpointConstants;
import tk.slaaavyn.slavikhomebackend.model.Room;
import tk.slaaavyn.slavikhomebackend.service.impl.RoomServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoomControllerTest extends BaseControllerTest {

    @Autowired
    RoomServiceImpl roomService;

    @Test
    void create() throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        String roomName = "testRoom";

        mockMvc.perform(post(EndpointConstants.ROOM_ENDPOINT)
                .param("roomName", roomName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(roomName));
    }

    @Test
    void getAllRooms() throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        mockMvc.perform(get(EndpointConstants.ROOM_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getDevicesInRoom() throws Exception {
        Room room = roomService.create("deviceRoom");

        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        mockMvc.perform(get(EndpointConstants.ROOM_ENDPOINT + "/" + room.getId())
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateRoomName() throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        Room room = roomService.create("oldRoomName");

        String newRoomName = "newRoomName";

        mockMvc.perform(put(EndpointConstants.ROOM_ENDPOINT + "/" + room.getId())
                .param("roomName", newRoomName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newRoomName));
    }

    @Test
    void deleteRoom() throws Exception {
        String adminToken = extractToken(login(defaultAdminUsername, defaultAdminPassword).andReturn());

        Room room = roomService.create("roomForDelete");

        mockMvc.perform(delete(EndpointConstants.ROOM_ENDPOINT + "/" + room.getId())
                .header(HttpHeaders.AUTHORIZATION, adminToken))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}