package com.ssafy.fullerting;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.kafka.BidConsumerService;
import com.ssafy.fullerting.global.kafka.BidRequestMessage;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

/**
 * 입찰희망 정합성유지여부를 확인하는 E2E 테스트
 * 
 * 테스트 시나리오:
 * 1. 동시 입찰 시 데이터 정합성 검증
 * 2. 입찰가 검증 로직 정합성 검증
 * 3. 입찰자 수 계산 정합성 검증
 * 4. Redis 캐시와 DB 데이터 정합성 검증
 * 5. 낙찰 후 상태 정합성 검증
 */
@SpringBootTest
@ActiveProfiles("test")
public class BidConsistencyE2ETest {

    @Autowired
    private BidConsumerService bidConsumerService;

    @Autowired
    private BidService bidService;

    @Autowired
    private ExArticleRepository exArticleRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ExArticle testArticle;
    private Deal testDeal;
    private List<MemberProfile> testBidders;
    private static final int INITIAL_PRICE = 1000;
    private static final int BID_INCREMENT = 100;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 설정
        setupTestData();
    }

    @AfterEach
    void tearDown() {
        // Redis 캐시 정리
        clearRedisCache();
    }

    private void setupTestData() {
        try {
            // 기존 사용자 조회 또는 생성
            MemberProfile seller = memberRepository.findByEmail("user001@example.com")
                    .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

            testBidders = Arrays.asList(
                    memberRepository.findByEmail("user002@example.com")
                            .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER)),
                    memberRepository.findByEmail("user003@example.com")
                            .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER)),
                    memberRepository.findByEmail("user004@example.com")
                            .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER))
            );

            // 테스트 게시글 조회
            testArticle = exArticleRepository.findById(4L)
                    .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

            testDeal = testArticle.getDeal();
            if (testDeal == null) {
                testDeal = Deal.builder()
                        .dealCurPrice(INITIAL_PRICE)
                        .bidderCount(0)
                        .exArticle(testArticle)
                        .build();
                testDeal = dealRepository.save(testDeal);
                testArticle.setdeal(testDeal);
                exArticleRepository.save(testArticle);
            } else {
                // 기존 거래 정보 초기화
                testDeal.setDealCurPrice(INITIAL_PRICE);
                testDeal.setBidderCount(0);
                dealRepository.save(testDeal);
            }

        } catch (Exception e) {
            throw new RuntimeException("테스트 데이터 설정 실패", e);
        }
    }

    private void clearRedisCache() {
        String redisKey = "auction:" + testArticle.getId() + ":logs";
        redisTemplate.delete(redisKey);
    }

    @Test
    @DisplayName("동시 입찰 시 데이터 정합성 검증 - 비관적 락 사용")
    void testConcurrentBidsDataConsistencyWithLock() throws InterruptedException {
        // given
        int numberOfThreads = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successfulBids = new AtomicInteger(0);
        AtomicInteger failedBids = new AtomicInteger(0);

        // when - 동시에 여러 입찰 요청
        for (int i = 0; i < numberOfThreads; i++) {
            int bidPrice = INITIAL_PRICE + (i + 1) * BID_INCREMENT;
            int bidderIndex = i % testBidders.size();
            MemberProfile bidder = testBidders.get(bidderIndex);

            executorService.submit(() -> {
                try {
                    BidRequestMessage message = new BidRequestMessage(
                            testArticle.getId(), 
                            bidPrice, 
                            bidder.getNickname()
                    );
                    bidConsumerService.consumeBidRequest(message);
                    successfulBids.incrementAndGet();
                } catch (Exception e) {
                    failedBids.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);
        executorService.shutdown();

        // then - 데이터 정합성 검증
        verifyDataConsistency();
        verifyBidOrderConsistency();
        verifyBidderCountConsistency();
    }

    @Test
    @DisplayName("동시 입찰 시 데이터 정합성 검증 - 락 미사용")
    void testConcurrentBidsDataConsistencyWithoutLock() throws InterruptedException {
        // given
        int numberOfThreads = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successfulBids = new AtomicInteger(0);
        AtomicInteger failedBids = new AtomicInteger(0);

        // when - 동시에 여러 입찰 요청 (락 미사용)
        for (int i = 0; i < numberOfThreads; i++) {
            int bidPrice = INITIAL_PRICE + (i + 1) * BID_INCREMENT;
            int bidderIndex = i % testBidders.size();
            MemberProfile bidder = testBidders.get(bidderIndex);

            executorService.submit(() -> {
                try {
                    BidRequestMessage message = new BidRequestMessage(
                            testArticle.getId(), 
                            bidPrice, 
                            bidder.getNickname()
                    );
                    bidConsumerService.consumeBidRequestwithouLock(message);
                    successfulBids.incrementAndGet();
                } catch (Exception e) {
                    failedBids.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);
        executorService.shutdown();

        // then - 데이터 정합성 검증
        verifyDataConsistency();
        verifyBidOrderConsistency();
        verifyBidderCountConsistency();
    }

    @Test
    @DisplayName("입찰가 검증 로직 정합성 검증")
    void testBidPriceValidationConsistency() {
        // given
        int invalidPrice = INITIAL_PRICE - 100; // 현재가보다 낮은 가격
        MemberProfile bidder = testBidders.get(0);

        // when & then - 낮은 가격으로 입찰 시도 시 예외 발생
        assertThatThrownBy(() -> {
            BidRequestMessage message = new BidRequestMessage(
                    testArticle.getId(), 
                    invalidPrice, 
                    bidder.getNickname()
            );
            bidConsumerService.consumeBidRequest(message);
        }).isInstanceOf(RuntimeException.class)
          .hasMessageContaining("현재가보다 높은 금액을 입력해주세요");

        // 입찰가가 변경되지 않았는지 확인
        Deal deal = dealRepository.findByExArticleId(testArticle.getId()).orElseThrow();
        assertThat(deal.getDealCurPrice()).isEqualTo(INITIAL_PRICE);
    }

    @Test
    @DisplayName("Redis 캐시와 DB 데이터 정합성 검증")
    void testRedisCacheAndDBDataConsistency() {
        // given - 입찰 데이터 생성
        MemberProfile bidder = testBidders.get(0);
        int bidPrice = INITIAL_PRICE + BID_INCREMENT;

        BidRequestMessage message = new BidRequestMessage(
                testArticle.getId(), 
                bidPrice, 
                bidder.getNickname()
        );
        bidConsumerService.consumeBidRequest(message);

        // when - Redis 캐시와 DB 데이터 조회
        String redisKey = "auction:" + testArticle.getId() + ":logs";
        List<Object> cachedBids = redisTemplate.opsForList().range(redisKey, 0, -1);
        
        List<BidLog> dbBids = bidRepository.findByDealId(testDeal.getId().toString());
        Deal deal = dealRepository.findByExArticleId(testArticle.getId()).orElseThrow();

        // then - 데이터 정합성 검증
        assertThat(cachedBids).isNotNull();
        assertThat(dbBids).hasSizeGreaterThan(0);
        assertThat(deal.getDealCurPrice()).isEqualTo(bidPrice);
        
        // Redis 캐시와 DB 데이터 개수 비교
        assertThat(cachedBids.size()).isEqualTo(dbBids.size());
    }

    @Test
    @DisplayName("입찰자 수 계산 정합성 검증")
    void testBidderCountCalculationConsistency() {
        // given - 여러 사용자가 입찰
        for (int i = 0; i < testBidders.size(); i++) {
            MemberProfile bidder = testBidders.get(i);
            int bidPrice = INITIAL_PRICE + (i + 1) * BID_INCREMENT;

            BidRequestMessage message = new BidRequestMessage(
                    testArticle.getId(), 
                    bidPrice, 
                    bidder.getNickname()
            );
            bidConsumerService.consumeBidRequest(message);
        }

        // when - 다양한 방법으로 입찰자 수 계산
        Deal deal = dealRepository.findByExArticleId(testArticle.getId()).orElseThrow();
        List<BidLog> allBids = bidRepository.findByDealId(deal.getId().toString());
        
        int uniqueBiddersFromBids = (int) allBids.stream()
                .map(BidLog::getUserId)
                .distinct()
                .count();
        
        int bidderCountFromService = bidService.getBidderCount(deal);
        int bidderCountFromDeal = deal.getBidderCount();

        // then - 모든 방법으로 계산한 입찰자 수가 일치하는지 확인
        assertThat(uniqueBiddersFromBids).isEqualTo(testBidders.size());
        assertThat(bidderCountFromService).isEqualTo(testBidders.size());
        assertThat(bidderCountFromDeal).isEqualTo(testBidders.size());
    }

    @Test
    @DisplayName("낙찰 후 상태 정합성 검증")
    void testAuctionCompletionStateConsistency() {
        // given - 입찰 데이터 생성
        MemberProfile bidder = testBidders.get(0);
        int bidPrice = INITIAL_PRICE + BID_INCREMENT;

        BidRequestMessage message = new BidRequestMessage(
                testArticle.getId(), 
                bidPrice, 
                bidder.getNickname()
        );
        bidConsumerService.consumeBidRequest(message);

        // when - 낙찰 처리
        List<BidLog> bids = bidRepository.findByDealId(testDeal.getId().toString());
        BidLog winningBid = bids.get(0); // 가장 높은 입찰가

        // 낙찰 처리 (게시글 완료 상태로 변경)
        testArticle.setDone(true);
        exArticleRepository.save(testArticle);

        // then - 상태 정합성 검증
        ExArticle completedArticle = exArticleRepository.findById(testArticle.getId()).orElseThrow();
        assertThat(completedArticle.isDone()).isTrue();
        
        // 낙찰된 입찰 정보 검증
        assertThat(winningBid.getBidLogPrice()).isEqualTo(bidPrice);
        assertThat(winningBid.getUserId()).isEqualTo(bidder.getId());
    }

    @Test
    @DisplayName("동일 사용자 재입찰 시 정합성 검증")
    void testSameUserRebidConsistency() {
        // given - 첫 번째 입찰
        MemberProfile bidder = testBidders.get(0);
        int firstBidPrice = INITIAL_PRICE + BID_INCREMENT;
        int secondBidPrice = firstBidPrice + BID_INCREMENT;

        // 첫 번째 입찰
        BidRequestMessage firstMessage = new BidRequestMessage(
                testArticle.getId(), 
                firstBidPrice, 
                bidder.getNickname()
        );
        bidConsumerService.consumeBidRequest(firstMessage);

        // when - 같은 사용자가 더 높은 가격으로 재입찰
        BidRequestMessage secondMessage = new BidRequestMessage(
                testArticle.getId(), 
                secondBidPrice, 
                bidder.getNickname()
        );
        bidConsumerService.consumeBidRequest(secondMessage);

        // then - 정합성 검증
        Deal deal = dealRepository.findByExArticleId(testArticle.getId()).orElseThrow();
        List<BidLog> userBids = bidRepository.findByDealId(deal.getId().toString())
                .stream()
                .filter(bid -> bid.getUserId().equals(bidder.getId()))
                .collect(Collectors.toList());

        // 현재가는 가장 높은 입찰가여야 함
        assertThat(deal.getDealCurPrice()).isEqualTo(secondBidPrice);
        
        // 해당 사용자의 입찰 내역이 2개여야 함
        assertThat(userBids).hasSize(2);
        
        // 입찰가가 오름차순으로 정렬되어야 함
        List<Integer> bidPrices = userBids.stream()
                .map(BidLog::getBidLogPrice)
                .sorted()
                .collect(Collectors.toList());
        assertThat(bidPrices).containsExactly(firstBidPrice, secondBidPrice);
    }

    /**
     * 데이터 정합성 검증 헬퍼 메서드
     */
    private void verifyDataConsistency() {
        Deal deal = dealRepository.findByExArticleId(testArticle.getId()).orElseThrow();
        List<BidLog> allBids = bidRepository.findByDealId(deal.getId().toString());

        // 입찰 내역이 존재하는지 확인
        assertThat(allBids).isNotEmpty();
        
        // 현재가가 올바르게 설정되었는지 확인
        int maxBidPrice = allBids.stream()
                .mapToInt(BidLog::getBidLogPrice)
                .max()
                .orElse(INITIAL_PRICE);
        assertThat(deal.getDealCurPrice()).isEqualTo(maxBidPrice);
    }

    /**
     * 입찰 순서 정합성 검증 헬퍼 메서드
     */
    private void verifyBidOrderConsistency() {
        List<BidLog> allBids = bidRepository.findByDealId(testDeal.getId().toString());
        
        // 입찰가가 오름차순으로 정렬되어야 함 (시간순)
        List<Integer> bidPrices = allBids.stream()
                .mapToInt(BidLog::getBidLogPrice)
                .boxed()
                .collect(Collectors.toList());
        
        List<Integer> sortedPrices = new ArrayList<>(bidPrices);
        Collections.sort(sortedPrices);
        
        assertThat(bidPrices).isEqualTo(sortedPrices);
    }

    /**
     * 입찰자 수 정합성 검증 헬퍼 메서드
     */
    private void verifyBidderCountConsistency() {
        Deal deal = dealRepository.findByExArticleId(testArticle.getId()).orElseThrow();
        List<BidLog> allBids = bidRepository.findByDealId(deal.getId().toString());
        
        int uniqueBidders = (int) allBids.stream()
                .map(BidLog::getUserId)
                .distinct()
                .count();
        
        assertThat(deal.getBidderCount()).isEqualTo(uniqueBidders);
    }
}
