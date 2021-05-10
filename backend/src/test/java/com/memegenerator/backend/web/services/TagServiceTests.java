package com.memegenerator.backend.web.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.Tag;
import com.memegenerator.backend.data.repository.MemeRepository;
import com.memegenerator.backend.data.repository.TagRepository;
import com.memegenerator.backend.domain.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration()
public class TagServiceTests {

    @Autowired
    private TagService tagService;

    @MockBean
    private TagRepository tagRepository;

    @MockBean
    private MemeRepository memeRepository;

    @Test
    public void creates_tag() {

        String testValue = "abc";

        Tag tag = new Tag(testValue);

        when(tagRepository.save(any())).thenReturn(tag);

        Tag result = tagService.createTag(tag);

        assertThat(result.getTitle()).isEqualTo(tag.getTitle());
        verify(tagRepository, times(1)).save(any());
    }

    @Test
    public void gets_tags() {

        int generations = new Random().nextInt(9) + 1;
        List<Tag> tagList = new ArrayList<Tag>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            tagList.add(new Tag(mockTitle));
        }

        when(tagRepository.findAll()).thenReturn(tagList);
        
        List<Tag> result = tagService.getTags();

        assertThat(result.size()).isEqualTo(generations);
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    public void gets_tags_for_meme() {

        int generations = new Random().nextInt(9) + 1;
        Set<Tag> tagList = new HashSet<Tag>();
        String mockTitle = "testtitle";

        for (int i = 0; i < generations; i++) {
            tagList.add(new Tag(mockTitle));
        }

        Meme mockMeme = new Meme();
        mockMeme.setTags(tagList);

        when(memeRepository.findById(anyLong())).thenReturn(Optional.of(mockMeme));
        
        List<Tag> result = tagService.getTags();

        assertThat(result.get(0).getTitle()).isEqualTo(mockTitle);
        verify(memeRepository, times(1)).findById(anyLong());
    }
}