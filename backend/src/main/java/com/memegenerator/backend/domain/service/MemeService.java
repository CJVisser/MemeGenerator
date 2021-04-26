package com.memegenerator.backend.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.web.dto.MemeDto;
import com.memegenerator.backend.web.dto.RequestResponse;

import org.springframework.stereotype.Service;

@Service
public interface MemeService {

    RequestResponse createMeme(MemeDto meme, Long id) throws NoSuchElementException;

    Meme getMemeById(long id) throws NoSuchElementException;

    Meme updateMeme(Meme meme) throws NoSuchElementException;

    Meme flagMeme(long id) throws NoSuchElementException;

    List<Meme> getMemes();
}
