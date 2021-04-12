package com.memegenerator.backend.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ejb.DuplicateKeyException;

import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.web.dto.SmallUserDto;
import com.memegenerator.backend.web.dto.UserDto;

@Service
public interface UserService {

    UserDto createUser(UserDto user) throws NoSuchElementException, DuplicateKeyException;

    UserDto updateUser(UserDto user) throws NoSuchElementException, DuplicateKeyException;

    SmallUserDto getUserById(long userId) throws NoSuchElementException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void requestPasswordReset(String email) throws NoSuchElementException;

    void resetPassword(String confirmationToken, String password) throws NoSuchElementException;

    SmallUserDto activateUser(Long userId, String confirmationToken) throws NoSuchElementException;

    void updateUserPoints(Long userId, int pointsToAdd) throws NoSuchElementException;

    List<User> getAllUsers();
}