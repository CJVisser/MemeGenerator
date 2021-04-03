package com.memegenerator.backend.web.controllers;

import java.util.NoSuchElementException;

import javax.ejb.DuplicateKeyException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memegenerator.backend.web.dto.SmallUserDto;
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

	/** 
	 * @param userDto
	 * @return ResponseEntity<String>
	 */
	@PutMapping()
	public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto) {

		try {

			userService.updateUser(userDto);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException | DuplicateKeyException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/** 
	 * @param userId
	 * @param token
	 * @return ResponseEntity<String>
	 */
	@GetMapping(path = "/activate/{userId}/{token}")
	public ResponseEntity<String> activateUser(@PathVariable long userId, @PathVariable String token) {

		try {

			userService.activateUser(userId, token);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/** 
	 * @param userId
	 * @return ResponseEntity<SmallUserDto>
	 */
	@GetMapping(path = "/{userId}")
	public ResponseEntity<SmallUserDto> getUserInfo(@PathVariable long userId) {
		try {

			return new ResponseEntity<SmallUserDto>(userService.getUserById(userId), HttpStatus.OK);
		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}