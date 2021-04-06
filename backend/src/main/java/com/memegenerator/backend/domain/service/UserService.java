package com.memegenerator.backend.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.memegenerator.backend.web.dto.SmallUserDto;
import com.memegenerator.backend.web.dto.UserDto;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ejb.DuplicateKeyException;

@Service
public interface UserService {

    UserDto createUser(UserDto userDto) throws NoSuchElementException, DuplicateKeyException;

    UserDto updateUser(UserDto userDto) throws NoSuchElementException, DuplicateKeyException;

    SmallUserDto getUserById(long userId) throws NoSuchElementException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void requestPasswordReset(String email) throws NoSuchElementException;
    
    void resetPassword(String confirmationToken, String password) throws NoSuchElementException;

    void activateUser(Long userId, String confirmationToken) throws NoSuchElementException;

    void updateUserPoints(Long userId, int pointsToAdd) throws NoSuchElementException;

    List<UserDto> getAllUsers();
}