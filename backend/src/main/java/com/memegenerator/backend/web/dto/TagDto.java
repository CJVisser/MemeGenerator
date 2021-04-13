package com.memegenerator.backend.web.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {
    
    public long id;

    @NotNull
    public String title;
}