package com.memegenerator.backend.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.memegenerator.backend.domain.service.impl.MemeServiceImpl;
import com.memegenerator.backend.domain.service.impl.UserServiceImpl;
import com.memegenerator.backend.web.dto.SocketResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ContextConfiguration()
public class LikeDislikeControllerTests {

    @Autowired
    private LikeDislikeController controller;

    @MockBean
    private MemeServiceImpl memeService;

    @MockBean
    private UserServiceImpl userService;

    private WebSocketStompClient webSocketStompClient;

    @BeforeEach
    public void setup() {
        this.webSocketStompClient = new WebSocketStompClient(
                new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));

        this.webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(username = "test", roles = { "User" })
    public void like_dislike() throws Exception {
        BlockingQueue<SocketResponseDto> blockingQueue = new ArrayBlockingQueue(1);

        SocketResponseDto response = new SocketResponseDto();
        response.isUpvote = true;
        response.memeId = 1l;
        response.userId = 1l;

        StompSession session = webSocketStompClient
        .connect("ws://localhost:8080/ws", new StompSessionHandlerAdapter() {})
        .get(1, TimeUnit.SECONDS);

        session.subscribe("/likedislike/", new StompFrameHandler(){
        
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println("Received message: " + payload);
                blockingQueue.add((SocketResponseDto) payload);
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return SocketResponseDto.class;
            }
        });

        session.send("/likedislike/", response);

        assertThat(response)
            .usingRecursiveComparison()
            .isEqualTo(blockingQueue.poll(1, TimeUnit.SECONDS));
    }
}