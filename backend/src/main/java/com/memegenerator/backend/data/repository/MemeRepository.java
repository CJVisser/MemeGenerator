package com.memegenerator.backend.data.repository;

import com.memegenerator.backend.data.entity.Meme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {
    @Query(value = "SELECT Count(*) FROM `meme` WHERE DATE(`createdat`) = :currentDate and `userid` = :userId", nativeQuery = true)
    Integer countAddedRecordsTodayByUser(@Param("currentDate") String currentDate, @Param("userId") Long userId);
}
