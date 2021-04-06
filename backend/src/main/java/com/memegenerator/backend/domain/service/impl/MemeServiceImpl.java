package com.memegenerator.backend.domain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.repository.MemeRepository;
import com.memegenerator.backend.data.repository.UserRepository;
import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.web.dto.MemeDto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemeServiceImpl implements MemeService {
    private final MemeRepository memeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    
    /** 
     * @param memeDto
     * @param userId
     * @return MemeDto
     * @throws NoSuchElementException
     */
    public MemeDto createMeme(MemeDto memeDto, Long userId) throws NoSuchElementException {

        Meme meme = modelMapper.map(memeDto, Meme.class);

        meme.user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        Meme savedMeme = memeRepository.save(meme);

        return modelMapper.map(savedMeme, MemeDto.class);
    }

    
    /** 
     * @param memeId
     * @return MemeDto
     * @throws NoSuchElementException
     */
    public MemeDto getMemeById(long memeId) throws NoSuchElementException {

        Meme meme = memeRepository.findById(memeId).orElseThrow(() -> new NoSuchElementException("Meme not found"));

        return modelMapper.map(meme, MemeDto.class);
    }

    
    /** 
     * @param memeDto
     * @return MemeDto
     * @throws NoSuchElementException
     */
    public MemeDto updateMeme(MemeDto memeDto) throws NoSuchElementException {

        Meme meme = memeRepository.findById(memeDto.id).orElseThrow(() -> new NoSuchElementException("Meme not found"));

        modelMapper.map(memeDto, meme);

        Meme savedMeme = memeRepository.save(meme);

        return modelMapper.map(savedMeme, MemeDto.class);
    }

    
    /** 
     * @return List<MemeDto>
     */
    public List<MemeDto> getMemes() {

        List<Meme> allMemes = memeRepository.findAll();

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        List<MemeDto> checker = allMemes.stream().map(meme -> modelMapper.map(meme, MemeDto.class)).collect(Collectors.toList());

        return checker;
    }
}
