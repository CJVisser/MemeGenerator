package com.memegenerator.backend.web.dto;

import javax.validation.constraints.NotNull;

import com.memegenerator.backend.data.entity.Achievement;
import com.memegenerator.backend.security.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmallUserDto {

	public long id;

	@NotNull
	public String username;

	@NotNull
	public String email;

	public int activated;

	public Role role;
	public int points;
	public Achievement[] achievements;
}