package com.memegenerator.backend.domain.service.impl;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import javax.ejb.DuplicateKeyException;

import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.domain.service.JavaMailSender;
import com.memegenerator.backend.domain.service.UserService;
import com.memegenerator.backend.security.Role;
import com.memegenerator.backend.security.UserDetailsAdapter;
import com.memegenerator.backend.data.repository.UserRepository;
import com.memegenerator.backend.web.dto.SmallUserDto;
import com.memegenerator.backend.web.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;
    private final ModelMapper modelMapper;

    /**
     * @param userDto
     * @return UserDto
     * @throws DuplicateKeyException
     */
    public UserDto createUser(UserDto userDto) throws DuplicateKeyException {

        if (userRepository.findByEmail(userDto.email).isPresent())
            throw new DuplicateKeyException("Email is already in use");

        if (userRepository.findUserByUsername(userDto.username).isPresent())
            throw new DuplicateKeyException("Username is already in use");

        User user = modelMapper.map(userDto, User.class);
        user.role = Role.User;
        user.password = bCryptPasswordEncoder.encode(userDto.password);
        user.confirmationToken = this.randomInt();

        User savedUser = userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javaminor@cornevisser.nl");
        message.setTo(savedUser.email);
        message.setSubject("Thank you for signing up");

        String url = "http://localhost:8080/user/activate/" + savedUser.id + "/" + savedUser.confirmationToken;

        message.setText("Click here to activate your account: " + url);

        javaMailSender.getJavaMailSender().send(message);

        return modelMapper.map(savedUser, UserDto.class);
    }

    /**
     * @return int
     */
    private int randomInt() {

        return new Random().nextInt(9000) + 1000;
    }

    /**
     * @param userId
     * @return SmallUserDto
     * @throws NoSuchElementException
     */
    public SmallUserDto getUserById(long userId) throws NoSuchElementException {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        return modelMapper.map(user, SmallUserDto.class);
    }

    /**
     * @param userDto
     * @return UserDto
     * @throws NoSuchElementException
     * @throws DuplicateKeyException
     */
    public UserDto updateUser(UserDto userDto) throws NoSuchElementException, DuplicateKeyException {

        User user = userRepository.findUserByUsername(userDto.username)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        if (user.id != userDto.id) {

            throw new DuplicateKeyException("Wrong user");
        }

        // Maybe this line should be fixed, it seems to reset user fields
        user = modelMapper.map(userDto, User.class);

        user.activated = true;
        user.role = Role.User;
        user.password = bCryptPasswordEncoder.encode(userDto.password);
        user.confirmationToken = this.randomInt();
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    /**
     * @param email
     * @throws NoSuchElementException
     */
    public void requestPasswordReset(String email) throws NoSuchElementException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        byte[] array = new byte[10];
        new Random().nextBytes(array);
        String token = new String(array, Charset.forName("UTF-8"));

        user.token = token;
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javaminor@cornevisser.nl");
        message.setTo(user.email);
        message.setSubject("Password reset token");
        message.setText("Your password reset token: " + user.token);
        javaMailSender.getJavaMailSender().send(message);
    }

    /**
     * @param confirmationToken
     * @param password
     * @throws NoSuchElementException
     */
    public void resetPassword(String confirmationToken, String password) throws NoSuchElementException {
        User user = userRepository.findByToken(confirmationToken)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        user.password = bCryptPasswordEncoder.encode(password);
        user.token = null;

        userRepository.save(user);
    }

    /**
     * @param userId
     * @param confirmationToken
     * @throws NoSuchElementException
     */
    public SmallUserDto activateUser(Long userId, String confirmationToken) throws NoSuchElementException {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        if (user.id != userId) {
            throw new NoSuchElementException(USER_NOT_FOUND);
        }

        user.activated = true;

        userRepository.save(user);

        return modelMapper.map(user, SmallUserDto.class);
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
    public List<UserDto> getAllUsers(){

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}