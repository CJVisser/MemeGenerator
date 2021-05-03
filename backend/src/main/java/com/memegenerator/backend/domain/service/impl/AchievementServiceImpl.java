package com.memegenerator.backend.domain.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.memegenerator.backend.data.entity.Achievement;
import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.data.repository.AchievementRepository;
import com.memegenerator.backend.data.repository.UserRepository;
import com.memegenerator.backend.domain.service.AchievementService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;
    private static final String USER_NOT_FOUND = "User not found";
    private static final String ACHIEVEMENT_NOT_FOUND = "Achievement not found";
    /**
     * @return List<Category>
     */
    public List<Achievement> getAchievements() {

        return achievementRepository.findAll();
    }

    /**
     * @param achievementID
     * @return Category
     * @throws NoSuchElementException
     */
    public Achievement getAchievementByID(long achievementID) throws NoSuchElementException {

        return achievementRepository.findById(achievementID).orElseThrow(() -> new NoSuchElementException("Category not found"));
    }

    public void LinkAchievementToUser(long achievementID, long userID){

        User user = userRepository.findById(userID).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        Achievement achievement = achievementRepository.findById(achievementID).orElseThrow(() -> new NoSuchElementException(ACHIEVEMENT_NOT_FOUND));

        var achievements = user.getAchievements();
        if(achievements.contains(achievement)){
            achievements.add(achievement);
        }

        user.setAchievements(achievements);
        var achievementUsers = achievement.getUsers();

        if(!achievementUsers.contains(user)){
            achievementUsers.add(user);
            achievement.setUsers(achievementUsers);

        }

        userRepository.save(user);
        achievementRepository.save(achievement);
    }

    public Set<Achievement> GetAllAchievementsForUser(long userID){
        User user = userRepository.findById(userID).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        return user.getAchievements();
    }

    public Set<User> GetAllUsersForAchievement(long achievementID){
        Achievement achievement = achievementRepository.findById(achievementID).orElseThrow(() -> new NoSuchElementException(ACHIEVEMENT_NOT_FOUND));
        return achievement.getUsers();
    }
}