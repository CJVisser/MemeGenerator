package com.memegenerator.backend.domain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.repository.MemeRepository;
import com.memegenerator.backend.data.repository.UserRepository;
import com.memegenerator.backend.domain.service.MemeService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemeServiceImpl implements MemeService {

    private static final String MEME_NOT_FOUND = "Meme not found";

    private final MemeRepository memeRepository;
    private final UserRepository userRepository;

    /**
     * @param meme
     * @param userId
     * @return Meme
     * @throws NoSuchElementException
     */
    public Meme createMeme(Meme meme, Long userId) throws NoSuchElementException {

        meme.user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Meme not found"));

        return memeRepository.save(meme);
    }

    /**
     * @param memeId
     * @return Meme
     * @throws NoSuchElementException
     */
    public Meme getMemeById(long memeId) throws NoSuchElementException {

        return memeRepository.findById(memeId).orElseThrow(() -> new NoSuchElementException("Meme not found"));
    }

    /**
     * @param meme
     * @return Meme
     * @throws NoSuchElementException
     */
    public Meme updateMeme(Meme meme) throws NoSuchElementException {

        if (!memeRepository.findById(meme.id).isPresent()) {
            throw new NoSuchElementException("Meme not found");
        }

        return memeRepository.save(meme);
    }

    /**
     * @return List<Meme>
     */
    public List<Meme> getMemes() {

        List<Meme> allMemes = memeRepository.findAll();

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        return allMemes;
    }

    public Meme flagMeme(long id) throws NoSuchElementException {

        Meme meme = getMemeById(id);

        meme.flag_points += 1;

        memeRepository.save(meme);

        return meme;
    }
}
