package com.example.memegenerator.domain.service.impl;

import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.ejb.DuplicateKeyException;

import com.example.memegenerator.data.entity.User;
import com.example.memegenerator.domain.service.JavaMailSender;
import com.example.memegenerator.domain.service.UserService;
import com.example.memegenerator.security.Role;
import com.example.memegenerator.security.UserDetailsAdapter;
import com.example.memegenerator.data.repository.UserRepository;
import com.example.memegenerator.web.dto.SmallUserDto;
import com.example.memegenerator.web.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;
    private final ModelMapper modelMapper;

    
    /** 
     * @param userDto
     * @return UserDto
     * @throws DuplicateKeyException
     */
    public UserDto createUser(UserDto userDto) throws DuplicateKeyException {
        
        if(userRepository.findByEmail(userDto.email).isPresent()) throw new DuplicateKeyException("Email is already in use");

        if(userRepository.findUserByUsername(userDto.username).isPresent()) throw new DuplicateKeyException("Username is already in use");

        User user = modelMapper.map(userDto, User.class);
        user.role =Role.User;
        user.password = bCryptPasswordEncoder.encode(userDto.password);
        user.confirmationToken = this.randomInt();

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
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
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new UserDetailsAdapter(userRepository.findUserByUsername(username));
    }
}