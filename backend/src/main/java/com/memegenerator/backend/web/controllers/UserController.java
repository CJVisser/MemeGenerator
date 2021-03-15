package com.memegenerator.backend.web.controllers;

import java.util.NoSuchElementException;

import javax.ejb.DuplicateKeyException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.memegenerator.backend.web.dto.UserDto;
import com.memegenerator.backend.domain.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	
	/** 
	 * @param userDto
	 * @return ResponseEntity<String>
	 * @throws DuplicateKeyException
	 */
	@PostMapping()
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) throws DuplicateKeyException {

		try {

			userService.createUser(userDto);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}