package com.memegenerator.backend.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import javax.ejb.DuplicateKeyException;

import com.memegenerator.backend.data.entity.Achievement;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.domain.service.UserService;
import com.memegenerator.backend.security.Role;
import com.memegenerator.backend.security.UserDetailsAdapter;
import com.memegenerator.backend.data.repository.AchievementRepository;
import com.memegenerator.backend.data.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final AchievementServiceImpl achievementService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    /**
     * @param user
     * @return User
     * @throws DuplicateKeyException
     */
    public User createUser(User user) throws DuplicateKeyException {

        if (userRepository.findByEmail(user.email).isPresent())
            throw new DuplicateKeyException("Email is already in use");

        if (userRepository.findUserByUsername(user.username).isPresent())
            throw new DuplicateKeyException("Username is already in use");

        user.role = Role.User;
        user.password = bCryptPasswordEncoder.encode(user.password);
        user.confirmationToken = this.randomInt();

        return userRepository.save(user);
    }

    /**
     * @return int
     */
    private int randomInt() {

        return new Random().nextInt(9000) + 1000;
    }

    /**
     * @param userId
     * @return SmallUser
     * @throws NoSuchElementException
     */
    public User getUserById(long userId) throws NoSuchElementException {

        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
    }

    /**
     * @param user
     * @return User
     * @throws NoSuchElementException
     * @throws DuplicateKeyException
     */
    public User updateUser(User user) throws NoSuchElementException, DuplicateKeyException {

        User foundUser = userRepository.findUserByUsername(user.username)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        if (!user.id.equals(foundUser.id)) {

            throw new DuplicateKeyException("Wrong user");
        }

        // Maybe this line should be fixed, it seems to reset user fields
        user = modelMapper.map(user, User.class);

        user.activated = true;
        user.role = Role.User;
        user.password = bCryptPasswordEncoder.encode(user.password);
        user.confirmationToken = this.randomInt();
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, User.class);
    }

    /**
     * @param email
     * @throws NoSuchElementException
     */
    public void requestPasswordReset(String email) throws NoSuchElementException {
        throw new UnsupportedOperationException();
    }

    /**
     * @param confirmationToken
     * @param password
     * @throws NoSuchElementException
     */
    public void resetPassword(String confirmationToken, String password) throws NoSuchElementException {
        throw new UnsupportedOperationException();
    }

    /**
     * @param userId
     * @param confirmationToken
     * @throws NoSuchElementException
     */
    public void activateUser(Long userId, String confirmationToken) throws NoSuchElementException {
        throw new UnsupportedOperationException();
    }

    /**
     * @param userId
     * @param pointsToAdd
     * @throws NoSuchElementException
     */
    public void updateUserPoints(Long userId, int pointsToAdd) throws NoSuchElementException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        user.points = user.points + pointsToAdd;

        userRepository.save(user);
    }

    /**
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsAdapter(userRepository.findUserByUsername(username));
    }

    /**
     * @return LIst<UserDto>
     */
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}