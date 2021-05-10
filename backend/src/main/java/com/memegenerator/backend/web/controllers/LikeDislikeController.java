package com.memegenerator.backend.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.memegenerator.backend.web.dto.LikeDislikeDto;

import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.domain.service.UserService;

@RestController
@RequiredArgsConstructor
public class LikeDislikeController {

    private final MemeService memeService;
    private final UserService userService;

    /**
     * @param response
     * @return ResponseEntity<String>
     */
    @PostMapping(path = "/like")
    public ResponseEntity<String> like(@RequestBody LikeDislikeDto likeDislikeDto) {

        Meme meme = memeService.getMemeById(likeDislikeDto.memeId);
        meme.setLikes(meme.getLikes() + 1);
        userService.updateUserPoints(likeDislikeDto.userId, 1);
        userService.updateUserPoints(meme.getUser().getId(), 1);

        memeService.updateMeme(meme);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param response
     * @return ResponseEntity<String>
     */
    @PostMapping(path = "/dislike")
    public ResponseEntity<String> dislike(@RequestBody LikeDislikeDto likeDislikeDto) {

        Meme meme = memeService.getMemeById(likeDislikeDto.memeId);
        meme.setDislikes(meme.getDislikes() + 1);

        memeService.updateMeme(meme);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}