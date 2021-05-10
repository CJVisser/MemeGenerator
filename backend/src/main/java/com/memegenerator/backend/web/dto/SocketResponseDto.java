package com.memegenerator.backend.web.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocketResponseDto {

    @NotNull
    public Long memeId;

    public Boolean isUpvote;

    public Long userId;
}