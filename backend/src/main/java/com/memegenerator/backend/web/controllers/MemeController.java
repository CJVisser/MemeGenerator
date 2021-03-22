package com.memegenerator.backend.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.web.dto.MemeDto;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final MemeService memeService;

    /** 
     * @return ResponseEntity<List<MemeDto>>
     */
    @GetMapping(path = "/")
    public ResponseEntity<List<MemeDto>> getMemes() {

        return new ResponseEntity<List<MemeDto>>(memeService.getMemes(), HttpStatus.OK);
    }

    
    /** 
     * @param imageblob
     * @param @RequestParam("title"
     * @return ResponseEntity<MemeDto>
     */
    @PostMapping(path = "/")
    public ResponseEntity<MemeDto> createMeme(@RequestParam("imageblob") MultipartFile imageblob, @RequestParam("title") String title,
        @RequestParam("userId") String userId, @RequestParam("categoryId") long categoryId,
        @RequestParam("description") String description) {

        MemeDto memeDto = new MemeDto();
        memeDto.title = title;
        memeDto.description = description;
        memeDto.likes = 0;
        memeDto.dislikes = 0;
        memeDto.categoryId = categoryId;

        try {

            memeDto.imageblob = imageblob.getBytes();
        } catch (IOException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        long userIdLong = Long.parseLong(userId);

        try {

            return new ResponseEntity<>(memeService.createMeme(memeDto, userIdLong), HttpStatus.CREATED);
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

            return new ResponseEntity<MemeDto>(memeService.getMemeById(memeId), HttpStatus.OK);
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

            return new ResponseEntity<MemeDto>(memeService.updateMeme(memeDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
