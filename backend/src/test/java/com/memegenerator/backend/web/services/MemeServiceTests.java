package com.memegenerator.backend.web.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.*;

import com.memegenerator.backend.data.entity.Achievement;
import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.Tag;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.data.repository.AchievementRepository;
import com.memegenerator.backend.data.repository.CategoryRepository;
import com.memegenerator.backend.data.repository.MemeRepository;
import com.memegenerator.backend.data.repository.TagRepository;
import com.memegenerator.backend.data.repository.UserRepository;
import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.domain.service.TagService;
import com.memegenerator.backend.web.dto.MemeDto;
import com.memegenerator.backend.web.dto.RequestResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration()
public class MemeServiceTests {

    @Autowired
    private MemeService memeService;

    @MockBean
    private MemeRepository memeRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private AchievementRepository achievementRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TagRepository tagRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void creates_meme() {

        String testValue = "abc";
        byte[] testByte = new byte[1];
        MemeDto memeDto = new MemeDto() {
            {
                setTitle(testValue);
                setTags(new Tag[0]);
            }
        };

        Meme meme = new Meme(testValue, testByte);

        Category category = new Category(testValue);

        User user = new User(testValue, testValue, testValue, true);

        when(memeRepository.save(any())).thenReturn(meme);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(achievementRepository.findByTitle(any())).thenReturn(new Achievement(null));
        when(modelMapper.map(any(), eq(Meme.class))).thenReturn(meme);

        RequestResponse result = memeService.createMeme(memeDto, (long) 2);

        assertTrue(result.success);

        verify(memeRepository, times(1)).save(any());
        verify(userRepository, times(2)).findById(anyLong());
    }

//    @Test
//    public void gets_memes() {
//
//        int generations = new Random().nextInt(9) + 1;
//        List<Meme> memeList = new ArrayList<Meme>();
//        String mockTitle = "testtitle";
//        String testValue = "abc";
//        byte[] testByte = new byte[1];
//
//        User user = new User(testValue, testValue, testValue, true);
//        Category category = new Category(testValue);
//        for (int i = 0; i < generations; i++) {
//            memeList.add(
//                    new Meme(mockTitle, testByte, true, user, category));
//        }
//
//        when(memeRepository.findAll()).thenReturn(memeList);
//
//        List<Meme> result = memeService.getMemes();
//
//        assertEquals(result.size(), generations);
//        verify(memeService, times(1)).getMemes();
//    }

    @Test
    public void gets_meme() {
        String testValue = "abc";
        byte[] testByte = new byte[1];

        String mockTitle = "testtitle";

        User user = new User(testValue, testValue, testValue, true);
        Category category = new Category(testValue);
        Meme meme = new Meme(mockTitle, testByte, true, user, category);

        when(memeRepository.findById(anyLong())).thenReturn(Optional.of(meme));

        Meme result = memeService.getMemeById(anyLong());

        assertEquals(result.getTitle(), meme.getTitle());

        verify(memeRepository, times(1)).findById(anyLong());

    }

    @Test
    public void updates_meme() {

        String testValue = "abc";
        String mockTitle = "testtitle";
        byte[] testByte = new byte[1];

        User user = new User(testValue, testValue, testValue, true);
        Category category = new Category(testValue);
        Meme meme = new Meme(mockTitle, testByte, true, user, category);

        MemeDto memeDto = new MemeDto() {
            {
                setTitle(mockTitle);
                setTags(new Tag[0]);
            }
        };

        when(memeRepository.findById(any())).thenReturn(Optional.of(meme));
        when(memeRepository.save(any())).thenReturn(meme);

        Meme result = memeService.updateMeme(meme);

        assertEquals(result.getTitle(), memeDto.getTitle());
        verify(memeRepository, times(1)).findById(any());
        verify(memeRepository, times(1)).save(any());
    }

    @Test
    public void gets_filtered_memes() {

        int generations = new Random().nextInt(9) + 1;
        List<Meme> memeList = new ArrayList<Meme>();
        String testValue = "abc";
        String mockTitle = "testtitle";
        byte[] testByte = new byte[1];

        User user = new User(testValue, testValue, testValue, true);
        Category category = new Category(testValue);
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        for (int i = 0; i < generations; i++) {
            memeList.add(new Meme(mockTitle, testByte, true, user, category) {
                {
                    setTitle(mockTitle);
                    setCreatedat(ts);
                }
            });
        }

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        when(memeRepository.findAll()).thenReturn(memeList);

        List<Meme> result = memeService.getMemes();

        assertEquals(result.size(), generations);
    }
}