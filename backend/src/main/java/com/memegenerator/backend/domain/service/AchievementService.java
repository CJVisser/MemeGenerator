package com.memegenerator.backend.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.memegenerator.backend.data.entity.Achievement;

import com.memegenerator.backend.data.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AchievementService {

    List<Achievement> getAchievements();

    Achievement getAchievementByID(long id) throws NoSuchElementException;

    Set<Achievement> GetAllAchievementsForUser(long userID) throws NoSuchElementException;

    Set<User> GetAllUsersForAchievement(long achievementID) throws NoSuchElementException;

    void LinkAchievementToUser(long achievementID, long userID) throws NoSuchElementException;
}