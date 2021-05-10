package com.memegenerator.backend.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.memegenerator.backend.security.Role;
import com.memegenerator.backend.web.dto.MemeDto;
import com.memegenerator.backend.web.dto.RequestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.domain.service.CategoryService;
import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.domain.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class MemeControllerTests {

    @Autowired
    private MemeController controller;

    @MockBean
    private MemeService memeService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {

        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_memes() throws Exception {

        int iterations = new Random().nextInt(9) + 1;
        List<Meme> memeList = new ArrayList<Meme>();
        String mockTitle = "testtitle";
        Category mockCategory = new Category("test");
        User mockUser = new User("test", "test", "test", Role.USER, true);

        for (int i = 0; i < iterations; i++) {
            memeList.add(new Meme(mockTitle, null, true, mockUser, mockCategory));
        }

        when(memeService.getMemes()).thenReturn(memeList);

        var mvcResult = this.mockMvc
                .perform(get("/meme/").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get(0).get("title");

        assertThat(title).isEqualTo(mockTitle);
        verify(memeService, times(1)).getMemes();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void creates_meme() throws Exception {

        String mockTitle = "title";
        String mockMessage = "message";
        RequestResponse requestResponseMock = new RequestResponse(mockMessage);
        Category categoryMock = new Category(mockTitle);

        when(memeService.createMeme(any(), anyLong())).thenReturn(requestResponseMock);
        when(categoryService.getCategoryById(anyLong())).thenReturn(categoryMock);
        doNothing().when(userService).updateUserPoints(anyLong(), anyInt());

        MockMultipartFile fileMock = new MockMultipartFile("imageblob",
                "Hello, World!".getBytes());

        this.mockMvc
                .perform(multipart("/meme/")
                .file(fileMock)
                .param("title", mockTitle)
                .param("userId", "1")
                .param("tags", "[]")
                .param("description", "description")
                .param("categoryId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated()).andReturn();

        verify(memeService, times(1)).createMeme(any(), anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_meme_by_id() throws Exception {

        String mockTitle = "testtitle";
        Category mockCategory = new Category("test");
        User mockUser = new User("test", "test", "test", Role.USER, true);
        Meme mockMeme = new Meme(mockTitle, null, true, mockUser, mockCategory);

        when(memeService.getMemeById(5)).thenReturn(mockMeme);

        var mvcResult = this.mockMvc
            .perform(get("/meme/5").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        var json = mvcResult.getResponse().getContentAsString();

        Map<String, Object> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get("title");

        assertThat(title).isEqualTo(mockTitle);
        verify(memeService, times(1)).getMemeById(anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void updates_meme() throws Exception {
        String mockTitle = "acbdef";
        Category mockCategory = new Category("test");
        User mockUser = new User("test", "test", "test", Role.USER, true);
        Meme mockMeme = new Meme(mockTitle, new byte[8], true, mockUser, mockCategory);
        MemeDto mockMemeDto = new MemeDto();
        mockMemeDto.setTitle("test");

        when(memeService.updateMeme(any())).thenReturn(mockMeme);

        var mvcResult = this.mockMvc
            .perform(put("/meme/5").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .content(asJsonString(mockMemeDto)))
            .andExpect(status().isOk())
            .andReturn();

        var json = mvcResult.getResponse().getContentAsString();

        Map<String, Object> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get("title");

        assertThat(title).isEqualTo(mockTitle);
        verify(memeService, times(1)).updateMeme(any());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void flags_meme() throws Exception {

        Category mockCategory = new Category("test");
        User mockUser = new User("test", "test", "test", Role.USER, true);
        Meme mockMeme = new Meme("title", null, true, mockUser, mockCategory);

        when(memeService.flagMeme(anyLong())).thenReturn(mockMeme);

        this.mockMvc
            .perform(get("/meme/flag/5").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
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