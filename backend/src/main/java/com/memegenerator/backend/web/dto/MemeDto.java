package com.memegenerator.backend.web.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.memegenerator.backend.data.entity.Category;
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

    public Timestamp createdat;
    public long categoryId;

    public String memestatus;

    public Category category;
    public Integer flag_points;
}