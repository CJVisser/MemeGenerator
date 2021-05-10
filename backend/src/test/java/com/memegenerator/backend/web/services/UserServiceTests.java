package com.memegenerator.backend.web.services;

import com.memegenerator.backend.data.repository.UserRepository;
import com.memegenerator.backend.domain.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration()
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    // @Test
    // public void creates_user() throws DuplicateKeyException {

    //     String testValue = "abc";

    //     UserDto userDto = new UserDto() {
    //         {
    //             setUsername(testValue);
    //             setPassword(testValue);
    //             setEmail(testValue);
    //             setActivated(true);
    //         }
    //     };

    //     User user = new User() {
    //         {
    //             setUsername(testValue);
    //             setPassword(testValue);
    //             setEmail(testValue);
    //             setActivated(true);
    //         }
    //     };

    //     when(userRepository.save(any())).thenReturn(user);
    //     when(userRepository.save(any())).thenReturn(Optional.of(user));
    //     UserDto result = userService.createUser(userDto);

    //     assertEquals(result.getUsername(), userDto.getUsername());
    //     assertEquals(result.getPassword(), userDto.getPassword());
    //     assertEquals(result.getEmail(), userDto.getEmail());
    // }

    public void gets_tags() {

    }
}