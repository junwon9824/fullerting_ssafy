package com.ssafy.fullerting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import static org.mockito.Mockito.*;

public class WebSocketWithoutKafkaTest {

    private WebSocketSession webSocketSession;

    @BeforeEach
    public void setUp() {
        webSocketSession = mock(WebSocketSession.class);
    }

    @Test
    public void testWebSocketDirectTransmissionSpeed() throws Exception {
        long startTime = System.currentTimeMillis();

        // 웹소켓을 통해 1000개의 메시지 전송
        for (int i = 0; i < 1000; i++) {

            webSocketSession.sendMessage(new TextMessage("Message " + i));
        }

        long endTime = System.currentTimeMillis();
        System.out.println("WebSocket direct send time: " + (endTime - startTime) + " ms");

    }

}
