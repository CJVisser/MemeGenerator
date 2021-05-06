package com.memegenerator.backend.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.memegenerator.backend.data.entity.Achievement;
import com.memegenerator.backend.data.entity.Category;
import com.memegenerator.backend.domain.service.AchievementService;
import com.memegenerator.backend.domain.service.CategoryService;
import com.memegenerator.backend.domain.service.UserService;
import com.memegenerator.backend.web.dto.AchievementDto;
import com.memegenerator.backend.web.dto.CategoryDto;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final UserService userService;
    private final AchievementService achievementService;
    private final ModelMapper modelMapper;

    @GetMapping(path = "/getAchievmenetsFor/{id}")
    public ResponseEntity<List<AchievementDto>> getAchievementsForUser(@PathVariable long id) {
        var achievements = achievementService.GetAllAchievementsForUser(id);
        var achievmentsList = new ArrayList<>(achievements);

        List<AchievementDto> achievementDtos = achievmentsList.stream()
                .map(achievement -> modelMapper.map(achievement, AchievementDto.class)).collect(Collectors.toList());

        return new ResponseEntity<List<AchievementDto>>(achievementDtos, HttpStatus.OK);
    }

    @PostMapping(path = "/linkAchievementToUser/{achievementID}/{userID}")
    public void LinkAchievementsToUser(@PathVariable long achievmeentID, long userID){
        achievementService.LinkAchievementToUser(achievmeentID, userID);
    }
}