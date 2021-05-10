package com.memegenerator.backend.web.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.memegenerator.backend.data.entity.Tag;
import com.memegenerator.backend.domain.service.TagService;
import com.memegenerator.backend.web.dto.TagDto;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final ModelMapper modelMapper;

    /**
     * @param tagDto
     * @return ResponseEntity<TagDto>
     */
    @PostMapping(path = "/")
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagDto tagDto) {

        Tag tag = tagService.createTag(modelMapper.map(tagDto, Tag.class));

        return new ResponseEntity<TagDto>(modelMapper.map(tag, TagDto.class), HttpStatus.CREATED);
    }

    /**
     * @return ResponseEntity<List<TagDto>>
     */
    @GetMapping(path = "/")
    public ResponseEntity<List<TagDto>> getTags() {

        List<Tag> tags = tagService.getTags();

        List<TagDto> tagDtos = tags.stream().map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<List<TagDto>>(tagDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/tag/{memeId}")
    public ResponseEntity<List<TagDto>> getMemeTags(@PathVariable long memeId) {

        Set<Tag> tags = tagService.getTagsForMeme(memeId);

        List<TagDto> tagDtos = tags.stream().map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<List<TagDto>>(tagDtos, HttpStatus.OK);
    }
}