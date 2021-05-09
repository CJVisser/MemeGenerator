package com.memegenerator.backend.web.controllers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.domain.service.UserService;
import com.memegenerator.backend.web.dto.RequestResponse;
import com.memegenerator.backend.web.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class UserControllerTests {

    @Autowired
    private UserController controller;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper mockModelMapper;

    @Test
    public void contextLoads() throws Exception {

        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void creates_user() throws Exception {

        RequestResponse mockResponse = new RequestResponse("");

        UserDto mockUserDto = new UserDto();
        mockUserDto.setUsername("testUser");
        mockUserDto.setPassword("testPassword");
        mockUserDto.setEmail("test@test.test");

        User mockUser = new User("testUser", "testPassword", "test@test.test", false);

        when(userService.createUser(any())).thenReturn(mockResponse);
        when(mockModelMapper.map(any(), any())).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/user").content(asJsonString(mockUserDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void updates_user() throws Exception {

        String mockValue = "acbdef";
        User userMock = new User(mockValue, mockValue, mockValue, true);

        User mockUser = new User("testUser", "testPassword", "test@test.test", false);

        when(userService.updateUser(any())).thenReturn(userMock);
        when(mockModelMapper.map(any(), any())).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/user").content(asJsonString(userMock))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void activates_user() throws Exception {

        this.mockMvc.perform(get("/user/activate/1/2").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}