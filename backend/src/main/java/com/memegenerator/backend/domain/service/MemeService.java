package com.memegenerator.backend.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.memegenerator.backend.web.dto.MemeDto;

import org.springframework.stereotype.Service;

@Service
public interface MemeService {
    
    MemeDto createMeme(MemeDto meme, Long id) throws NoSuchElementException;

    MemeDto getMemeById(long id) throws NoSuchElementException;

    MemeDto updateMeme(MemeDto meme) throws NoSuchElementException;

    List<MemeDto> getMemes();
}
