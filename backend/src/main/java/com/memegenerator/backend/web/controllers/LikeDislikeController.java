package com.memegenerator.backend.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.memegenerator.backend.web.dto.SocketResponseDto;

import java.util.NoSuchElementException;

import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.domain.service.UserService;

@RestController
@RequestMapping("likedislike")
@RequiredArgsConstructor
public class LikeDislikeController {

    private final MemeService memeService;
    private final UserService userService;

    /** 
     * @param response
     * @return ResponseEntity<SocketResponseDto>
     */
    @MessageMapping("/")
    public ResponseEntity<SocketResponseDto> likedislike(@RequestBody SocketResponseDto response) {

        try {

            Meme meme = memeService.getMemeById(response.memeId);
            
            if(response.userId.equals(meme.getUser().getId())){
                return new ResponseEntity<SocketResponseDto>(response, HttpStatus.OK);
            }

            if (response.isUpvote) {

                meme.setLikes(meme.getLikes() + 1);
                userService.updateUserPoints(response.userId, 1);
                userService.updateUserPoints(meme.getUser().getId(), 1);
            } else {

                meme.setDislikes(meme.getDislikes() + 1);
                userService.updateUserPoints(response.userId, 1);
            }

            memeService.updateMeme(meme);

            return new ResponseEntity<SocketResponseDto>(response, HttpStatus.OK);

        } catch (NoSuchElementException e) {

            return new ResponseEntity<SocketResponseDto>(response, HttpStatus.NOT_FOUND);
        }
    }
}