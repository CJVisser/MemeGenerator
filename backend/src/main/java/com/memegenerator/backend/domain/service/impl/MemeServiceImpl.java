package com.memegenerator.backend.domain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import com.memegenerator.backend.data.entity.Achievement;
import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.data.repository.AchievementRepository;
import com.memegenerator.backend.data.repository.MemeRepository;
import com.memegenerator.backend.data.repository.UserRepository;
import com.memegenerator.backend.domain.service.MemeService;

import org.hibernate.mapping.Set;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemeServiceImpl implements MemeService {

    private final MemeRepository memeRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;

    /**
     * @param meme
     * @param userId
     * @return Meme
     * @throws NoSuchElementException
     */
    public Meme createMeme(Meme meme, Long userId) throws NoSuchElementException {

        //meme.user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Achievement achievement = determineAchievement(user);

        // If achievement is not empty, add it to the user
        if(!achievement.title.equals("")){
            achievement.users.add(user);
        }

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

    private Achievement determineAchievement(User user){

        int amountOfMemes = user.getMemes().size();
        Achievement achievement = new Achievement();

        switch(amountOfMemes){
            case 0:
                achievement = achievementRepository.findByTitle("first meme");
                break;
            
            case 4:
                achievement = achievementRepository.findByTitle("fifth meme");
        }

        return achievement;
    }
}
