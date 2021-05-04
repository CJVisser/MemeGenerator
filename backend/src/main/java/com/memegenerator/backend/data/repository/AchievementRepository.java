package com.memegenerator.backend.data.repository;

import com.memegenerator.backend.data.entity.Achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Achievement findByTitle(String title);
}