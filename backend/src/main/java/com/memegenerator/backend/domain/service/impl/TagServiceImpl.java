package com.memegenerator.backend.domain.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.Tag;
import com.memegenerator.backend.data.repository.MemeRepository;
import com.memegenerator.backend.data.repository.TagRepository;
import com.memegenerator.backend.domain.service.TagService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final MemeRepository memeRepository;

    /**
     * @param tag
     * @return Tag
     */
    public Tag createTag(Tag tag) {

        return tagRepository.save(tag);
    }

    /**
     * @return List<Tag>
     */
    public List<Tag> getTags() {

        return tagRepository.findAll();
    }

    public Set<Tag> getTagsForMeme(long memeId) {

        Meme meme = memeRepository.findById(memeId).orElseThrow(() -> new NoSuchElementException("Meme not found"));

        return meme.getTags();
    }
}