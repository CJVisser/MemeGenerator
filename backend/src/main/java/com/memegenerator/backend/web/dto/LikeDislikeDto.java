package com.memegenerator.backend.web.dto;

import javax.validation.constraints.NotNull;

public class LikeDislikeDto {
    @NotNull
    public Long memeId;
    public Long userId;
}