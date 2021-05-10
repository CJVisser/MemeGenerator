package com.memegenerator.backend.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.domain.service.impl.MemeServiceImpl;
import com.memegenerator.backend.domain.service.impl.UserServiceImpl;
import com.memegenerator.backend.security.Role;
import com.memegenerator.backend.web.dto.LikeDislikeDto;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ContextConfiguration()
public class LikeDislikeControllerTests {

    @Autowired
    private LikeDislikeController controller;

    @Autowired
	private MockMvc mockMvc;

    @MockBean
    private MemeServiceImpl memeService;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void like() throws Exception {
        LikeDislikeDto mockLikeDislike = new LikeDislikeDto();
        mockLikeDislike.memeId = 1l;
        mockLikeDislike.userId = 1l;

        Category mockCategory = new Category("test");

        User mockUser = new User("test", "test", "test", Role.USER, true);
        Meme mockMeme = new Meme("title", null, true, mockUser, mockCategory);

        when(memeService.getMemeById(anyLong())).thenReturn(mockMeme);
        when(memeService.updateMeme(any())).thenReturn(mockMeme);

        mockMvc.perform(MockMvcRequestBuilders.post("/like").content(asJsonString(mockLikeDislike))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(memeService, times(1)).updateMeme(any());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void dislike() throws Exception {
        LikeDislikeDto mockLikeDislike = new LikeDislikeDto();
        mockLikeDislike.memeId = 1l;
        mockLikeDislike.userId = 1l;

        Category mockCategory = new Category("test");

        User mockUser = new User("test", "test", "test", Role.USER, true);
        Meme mockMeme = new Meme("title", null, true, mockUser, mockCategory);

        when(memeService.getMemeById(anyLong())).thenReturn(mockMeme);
        when(memeService.updateMeme(any())).thenReturn(mockMeme);

        mockMvc.perform(MockMvcRequestBuilders.post("/dislike").content(asJsonString(mockLikeDislike))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(memeService, times(1)).updateMeme(any());
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