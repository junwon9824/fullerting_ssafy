package com.ssafy.fullerting;

import com.ssafy.fullerting.global.config.BidProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProducerServiceTest {

    @Autowired
    private BidProducerService bidProducerService;

    @Test
    public void testBeanInjection() {
        assertNotNull(bidProducerService);
    }
}
