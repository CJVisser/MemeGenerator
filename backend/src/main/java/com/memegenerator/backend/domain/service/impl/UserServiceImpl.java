package com.memegenerator.backend.domain.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.ejb.DuplicateKeyException;

import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.domain.service.UserService;
import com.memegenerator.backend.security.Role;
import com.memegenerator.backend.security.UserDetailsAdapter;
import com.memegenerator.backend.web.dto.RequestResponse;
import com.memegenerator.backend.data.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import com.memegenerator.backend.domain.service.JavaMailSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    /**
     * @param user
     * @return User
     * @throws DuplicateKeyException
     */
    public RequestResponse createUser(User user) {

        RequestResponse response = new RequestResponse("");

        // Check if email is already in use
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            response.errors.add("This email is already in use.");

        // Check if username is already in use
        if (userRepository.findUserByUsername(user.getUsername()).isPresent())
            response.errors.add("This username is already in use.");

        if (!response.errors.isEmpty())
            return response;

        user.setRole(Role.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmationToken(this.randomInt());

        User savedUser = userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javaminor@cornevisser.nl");
        message.setTo(savedUser.getEmail());
        message.setSubject("Bedankt voor het registreren");

        String url = "http://localhost:8080/user/activate/" + savedUser.getId() + "/"
                + savedUser.getConfirmationToken();

        message.setText("Klik hier om uw account te activeren: " + url);

        javaMailSender.getJavaMailSender().send(message);

        response.message = "You successfully signed up!";

        response.success = true;

        return response;
    }

    /**
     * @return int
     */
    private int randomInt() {

        return new Random().nextInt(9000) + 1000;
    }

    /**
     * @param userId
     * @return User
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

        User foundUser = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        if (!user.getId().equals(foundUser.getId())) {

            throw new DuplicateKeyException("Wrong user");
        }

        user.setActivated(true);
        user.setRole(Role.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmationToken(this.randomInt());
        user.setBanned(false);
        if(!user.getPassword().equals("")){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }else{
            user.setPassword(foundUser.getPassword());
        }

        user.achievements = foundUser.achievements;

        return userRepository.save(user);
    }

    /**
     * @param email
     * @throws NoSuchElementException
     */
    public void requestPasswordReset(String email) throws NoSuchElementException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        byte[] array = new byte[10];
        new Random().nextBytes(array);
        String token = new String(array, StandardCharsets.UTF_8);

        user.setToken(token);
        
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javaminor@cornevisser.nl");
        message.setTo(user.getEmail());
        message.setSubject("Password reset token");
        message.setText("Your password reset token: " + user.getToken());
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

        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setToken(null);

        userRepository.save(user);
    }

    /**
     * @param userId
     * @param confirmationToken
     * @throws NoSuchElementException
     */
    public RequestResponse activateUser(Long userId, String confirmationToken) throws NoSuchElementException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        RequestResponse response = new RequestResponse("");

        if (!user.getId().equals(userId)) {
            throw new NoSuchElementException(USER_NOT_FOUND);
        }

        user.setActivated(true);

        userRepository.save(user);

        response.message = "Your account is activated!";
        response.success = true;

        return response;
    }

    /**
     * @param userId
     * @param pointsToAdd
     * @throws NoSuchElementException
     */
    public void updateUserPoints(Long userId, int pointsToAdd) throws NoSuchElementException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        user.setPoints(user.getPoints() + pointsToAdd);

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
     * @return List<User>
     */
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public void banUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        
        user.setBanned(!user.isBanned());

        userRepository.save(user);
    }
}