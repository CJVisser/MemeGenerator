package com.memegenerator.backend.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ejb.DuplicateKeyException;

import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.web.dto.RequestResponse;

@Service
public interface UserService {

    RequestResponse createUser(User user);

    User updateUser(User user) throws NoSuchElementException, DuplicateKeyException;

    User getUserById(long userId) throws NoSuchElementException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void requestPasswordReset(String email) throws NoSuchElementException;

    void resetPassword(String confirmationToken, String password) throws NoSuchElementException;

    RequestResponse activateUser(Long userId, String confirmationToken);

    void updateUserPoints(Long userId, int pointsToAdd) throws NoSuchElementException;

    List<User> getAllUsers();

    void banUser(Long userId) throws NoSuchElementException, DuplicateKeyException;
}