package com.memegenerator.backend.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.google.gson.Gson;
import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.Tag;
import com.memegenerator.backend.domain.service.CategoryService;
import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.domain.service.UserService;
import com.memegenerator.backend.web.dto.MemeDto;
import com.memegenerator.backend.web.dto.RequestResponse;
import com.memegenerator.backend.web.dto.SmallMemeDto;
import com.memegenerator.backend.web.dto.TagDto;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("meme")
@RequiredArgsConstructor
public class MemeController {

    private final CategoryService categoryService;
    private final MemeService memeService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping(path = "/")
    public ResponseEntity<List<MemeDto>> getMemes() {

        List<Meme> memes = memeService.getMemes();

        List<MemeDto> memeDtos = memes.stream().map(meme -> modelMapper.map(meme, MemeDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<List<MemeDto>>(memeDtos, HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<RequestResponse> createMeme(@RequestParam("imageblob") MultipartFile imageblob,
            @RequestParam("title") String title, @RequestParam("userId") String userId,
            @RequestParam("categoryId") long categoryId, @RequestParam("tags") String tagsString, @RequestParam("description") String description) {

        Category category = categoryService.getCategoryById(categoryId);

        MemeDto memeDto = new MemeDto();
        memeDto.title = title;
        memeDto.description = description;
        memeDto.likes = 0;
        memeDto.dislikes = 0;
        memeDto.category = category;
        memeDto.flag_points = 0;

        Gson gson = new Gson();
        TagDto[] tags = gson.fromJson(tagsString, TagDto[].class);
        long userIdLong = Long.parseLong(userId);

        memeDto.tags = new Tag[tags.length];

        for (int i = 0; i < tags.length; i++) {

            Tag newTag = new Tag(tags[i].title);
            newTag.setId(tags[i].id);
            memeDto.tags[i] = newTag;
        }

        try {

            memeDto.imageblob = imageblob.getBytes();
        } catch (IOException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            RequestResponse response = memeService.createMeme(memeDto, userIdLong);

            userService.updateUserPoints(userIdLong, 1);
        
			return new ResponseEntity<RequestResponse>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{memeId}")
    public ResponseEntity<MemeDto> getMemeById(@PathVariable long memeId) {

        try {

            Meme meme = memeService.getMemeById(memeId);

            return new ResponseEntity<MemeDto>(modelMapper.map(meme, MemeDto.class), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{memeId}")
    public ResponseEntity<MemeDto> updateMeme(@Valid @RequestBody MemeDto memeDto) {

        try {

            Meme meme = memeService.updateMeme(modelMapper.map(memeDto, Meme.class));

            return new ResponseEntity<MemeDto>(modelMapper.map(meme, MemeDto.class), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/flag")
    public ResponseEntity<MemeDto> flagMeme(@Valid @RequestBody SmallMemeDto memeDto) {
        Meme meme = memeService.flagMeme(memeDto.Id);

        return new ResponseEntity<MemeDto>(modelMapper.map(meme, MemeDto.class), HttpStatus.OK);
    }
}
