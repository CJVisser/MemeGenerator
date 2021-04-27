package com.memegenerator.backend.web.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.ejb.DuplicateKeyException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.memegenerator.backend.web.dto.RequestResponse;
import com.memegenerator.backend.web.dto.SmallUserDto;
import com.memegenerator.backend.web.dto.UserDto;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.domain.service.UserService;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final ModelMapper modelMapper;

	/**
	 * @param userDto
	 * @return ResponseEntity<String>
	 * @throws DuplicateKeyException
	 */
	@PostMapping()
	public ResponseEntity<RequestResponse> createUser(@Valid @RequestBody UserDto userDto) {

		try {

			User user = modelMapper.map(userDto, User.class);
			RequestResponse response = userService.createUser(user);

			return new ResponseEntity<RequestResponse>(response, HttpStatus.OK);
		} catch (NoSuchElementException e) {

			return new ResponseEntity<RequestResponse>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @param userDto
	 * @return ResponseEntity<String>
	 */
	@PutMapping()
	public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto) {

		try {

			// if(authenticatedUser == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

			User user = modelMapper.map(userDto, User.class);
			userService.updateUser(user);

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
	public ResponseEntity<RequestResponse> activateUser(@PathVariable long userId, @PathVariable String token) {

		try {

			return new ResponseEntity<RequestResponse>(userService.activateUser(userId, token), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RequestResponse>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @param userId
	 * @param token
	 * @return ResponseEntity<String>
	 */
	@PostMapping(path = "/user/reset")
	public ResponseEntity<String> resetPassword(@RequestParam("email") String email) {
		try {
			userService.requestPasswordReset(email);

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

			User user = userService.getUserById(userId);

			return new ResponseEntity<SmallUserDto>(modelMapper.map(user, SmallUserDto.class), HttpStatus.OK);
		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @return ResponseEntity<UserDto[]>
	 */
	@GetMapping(path = "/")
	public ResponseEntity<List<UserDto>> getUsers() {
		List<User> users = userService.getAllUsers();

		List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
	}

	/**
	 * 
	 */
	@PutMapping(path = "/ban")
	public ResponseEntity<String> banUser(@Valid @RequestBody Long userId) {

		try {

			userService.banUser(userId);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException | DuplicateKeyException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}