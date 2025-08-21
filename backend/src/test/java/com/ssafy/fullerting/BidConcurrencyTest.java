package com.ssafy.fullerting;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.kafka.BidConsumerService;
import com.ssafy.fullerting.global.kafka.BidRequestMessage;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BidConcurrencyTest {

    @Autowired
    private BidConsumerService bidConsumerService;

    @Autowired
    private ExArticleRepository exArticleRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private MemberRepository memberRepository;

    private ExArticle testArticle;
    private MemberProfile testBidder;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 설정
        MemberProfile sellerProfile = MemberProfile.builder().nickname("testSeller").build();
        memberRepository.save(sellerProfile);

        testBidder = MemberProfile.builder().nickname("testBidder").build();
        memberRepository.save(testBidder);

        Deal deal = Deal.builder().dealCurPrice(100).bidderCount(0).build();
        testArticle = ExArticle.builder().user(sellerProfile).title("Test Article").content("Concurrency Test")
                .isDone(false).created_at(LocalDateTime.now()).deal(deal).build();
        deal.setexarticle(testArticle);
        exArticleRepository.save(testArticle);
    }

    @AfterEach
    void tearDown() {
        // 테스트 데이터 정리
        exArticleRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("여러 스레드가 동시에 입찰을 요청할 때 비관적 락이 데이터 정합성을 보장하는지 테스트")
    void testConcurrentBidsWithPessimisticLock() throws InterruptedException {
        // given
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        int initialPrice = 100;

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            int bidPrice = initialPrice + i + 1; // 101, 102, ..., 200
            executorService.submit(() -> {
                try {
                    BidRequestMessage message = new BidRequestMessage(testArticle.getId(), bidPrice,
                            testBidder.getNickname());
                    bidConsumerService.consumeBidRequest(message);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 작업을 마칠 때까지 대기
        executorService.shutdown();

        // then
        Deal finalDeal = dealRepository.findByExArticleId(testArticle.getId()).orElseThrow();
        int expectedFinalPrice = initialPrice + numberOfThreads; // 100 + 100 = 200

        assertThat(finalDeal.getDealCurPrice()).isEqualTo(expectedFinalPrice);
    }
}