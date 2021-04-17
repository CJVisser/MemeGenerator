package com.memegenerator.backend.web.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

	@NotNull
	public String title;

	@NotNull
	public Long id;
}