package com.memegenerator.backend.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.memegenerator.backend.data.entity.Meme;

import org.springframework.stereotype.Service;

@Service
public interface MemeService {

    Meme createMeme(Meme meme, Long id) throws NoSuchElementException;

    Meme getMemeById(long id) throws NoSuchElementException;

    Meme updateMeme(Meme meme) throws NoSuchElementException;

    List<Meme> getMemes();
}
