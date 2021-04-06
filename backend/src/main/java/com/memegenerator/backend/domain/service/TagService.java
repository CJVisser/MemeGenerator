package com.memegenerator.backend.domain.service;

import java.util.List;
import java.util.Set;

import com.memegenerator.backend.data.entity.Tag;

import org.springframework.stereotype.Service;

@Service
public interface TagService {

    Tag createTag(Tag tag);

    List<Tag> getTags();

    Set<Tag> getTagsForMeme(long memeId);
}