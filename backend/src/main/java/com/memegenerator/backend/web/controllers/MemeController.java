package com.memegenerator.backend.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.domain.service.CategoryService;
import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.web.dto.MemeDto;

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
    private final ModelMapper modelMapper;

    /**
     * @return ResponseEntity<List<MemeDto>>
     */
    @GetMapping(path = "/")
    public ResponseEntity<List<MemeDto>> getMemes() {

        List<Meme> memes = memeService.getMemes();

        List<MemeDto> memeDtos = memes.stream().map(meme -> modelMapper.map(meme, MemeDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<List<MemeDto>>(memeDtos, HttpStatus.OK);
    }

    /**
     * @param imageblob
     * @param @RequestParam("title"
     * @return ResponseEntity<MemeDto>
     */
    @PostMapping(path = "/")
    public ResponseEntity<MemeDto> createMeme(@RequestParam("imageblob") MultipartFile imageblob,
            @RequestParam("title") String title, @RequestParam("userId") String userId,
            @RequestParam("categoryId") long categoryId, @RequestParam("description") String description) {

        Category category = categoryService.getCategoryById(categoryId);

        Meme meme = new Meme();
        meme.title = title;
        meme.description = description;
        meme.likes = 0;
        meme.dislikes = 0;
        meme.category = category;

        try {

            meme.imageblob = imageblob.getBytes();
        } catch (IOException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        long userIdLong = Long.parseLong(userId);

        try {

            Meme createdMeme = memeService.createMeme(meme, userIdLong);
            
            return new ResponseEntity<MemeDto>(modelMapper.map(createdMeme, MemeDto.class), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param memeId
     * @return ResponseEntity<MemeDto>
     */
    @GetMapping(path = "/{memeId}")
    public ResponseEntity<MemeDto> getMemeById(@PathVariable long memeId) {

        try {

            Meme meme = memeService.getMemeById(memeId);

            return new ResponseEntity<MemeDto>(modelMapper.map(meme, MemeDto.class), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param memeDto
     * @return ResponseEntity<MemeDto>
     */
    @PutMapping(path = "/update")
    public ResponseEntity<MemeDto> updateMeme(@Valid @RequestBody MemeDto memeDto) {

        try {

            Meme meme = memeService.updateMeme(modelMapper.map(memeDto, Meme.class));

            return new ResponseEntity<MemeDto>(modelMapper.map(meme, MemeDto.class), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
