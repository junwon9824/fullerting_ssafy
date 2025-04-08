package com.ssafy.fullerting;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.global.kafka.BidProducerService;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WebSocketWithKafkaTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private BidProducerService bidProducerService; // 카프카 프로듀서 서비스

    @Mock
    private WebSocketSession webSocketSession; // 웹소켓 세션

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testKafkaTransmissionSpeed() {
        long startTime = System.currentTimeMillis();

        // 카프카에 1000개의 메시지 전송
        for (int i = 0; i < 1000; i++) {
            bidProducerService.kafkaalarmproduce(null, null, "Message " + i);
            // 카프카 템플릿을 호출하는지 확인
            verify(kafkaTemplate, times(i + 1)).send(any(String.class), any(String.class));
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Kafka send time: " + (endTime - startTime) + " ms");

    }

    // 카프카 리스너를 테스트하여 SSE 전송
    @Test
    public void testKafkaListenerAndSSE() throws IOException {
        // Given
        String message = "{\"userId\":1,\"articleId\":1,\"redirectUrl\":\"http://example.com\"}";

        // When
        // When
        bidProducerService.kafkaalarmproduce(mock(MemberProfile.class), mock(ExArticle.class), message);

        // Then
        // 웹소켓을 통해 클라이언트에게 전송하는지 확인
        verify(webSocketSession).sendMessage(any(TextMessage.class));

    }
}
