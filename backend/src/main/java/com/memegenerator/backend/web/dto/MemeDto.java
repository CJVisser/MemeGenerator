package com.memegenerator.backend.web.dto;

import javax.validation.constraints.NotNull;

import com.memegenerator.backend.data.entity.Tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemeDto {

    public long id;

    @NotNull
    public String title;

    public String description;

    public byte[] imageblob;

    public int likes;

    public int dislikes;

    public Tag[] tags;

    public UserDto user;

    public long categoryId;
}