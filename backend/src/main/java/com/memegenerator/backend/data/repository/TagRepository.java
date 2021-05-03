package com.memegenerator.backend.data.repository;

import java.util.Optional;

import com.memegenerator.backend.data.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findById(String token);
}