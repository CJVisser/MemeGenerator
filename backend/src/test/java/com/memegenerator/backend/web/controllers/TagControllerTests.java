package com.memegenerator.backend.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;
import com.memegenerator.backend.data.entity.Tag;
import com.memegenerator.backend.domain.service.TagService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration()
public class TagControllerTests {

    @Autowired
    private TagController controller;

    @MockBean
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {

        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_tags() throws Exception {

        int generations = new Random().nextInt(9) + 1;
        List<Tag> tagList = new ArrayList<Tag>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            tagList.add(new Tag(mockTitle));
        }

        when(tagService.getTags()).thenReturn(tagList);

        var mvcResult = this.mockMvc
                .perform(get("/tag/").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get(0).get("title");

        assertThat(title).isEqualTo(mockTitle);
        verify(tagService, times(1)).getTags();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void returns_tags_for_meme() throws Exception {

        int generations = new Random().nextInt(9) + 1;
        List<Tag> tagList = new ArrayList<Tag>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            tagList.add(new Tag(mockTitle));
        }

        when(tagService.getTagsForMeme(anyLong())).thenReturn(Set.copyOf(tagList));

        var mvcResult = this.mockMvc
                .perform(get("/tag/tag/5").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var json = mvcResult.getResponse().getContentAsString();

        List<Map<String, Object>> dataList = JsonPath.parse(json).read("$");
        String title = (String) dataList.get(0).get("title");

        assertThat(title).isEqualTo(mockTitle);
        verify(tagService, times(1)).getTagsForMeme(anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void creates_tag() throws Exception {

        String mockTitle = "acbdef";
        Tag tagMock = new Tag(mockTitle);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(tagMock);
        
        when(tagService.createTag(any())).thenReturn(tagMock);

        var mvcResult = this.mockMvc
            .perform(post("/tag/").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isCreated())
            .andReturn();

        var json = mvcResult.getResponse().getContentAsString();

        Map<String, Object> data = JsonPath.parse(json).read("$");
        String title = (String) data.get("title");

        assertThat(title).isEqualTo(mockTitle);
        verify(tagService, times(1)).createTag(any());
    }
}