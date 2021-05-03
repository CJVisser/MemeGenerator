package com.memegenerator.backend.web.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	public long id;

	@NotNull
	public String username;

	@NotNull
	public String password;

	@NotNull
	public String email;

	@NotNull
	public boolean activated;

	public boolean banned;

	public Timestamp createdat;

	public int points;
}