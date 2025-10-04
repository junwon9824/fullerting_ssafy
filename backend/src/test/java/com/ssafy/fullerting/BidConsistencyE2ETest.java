// package com.ssafy.fullerting;

// import com.ssafy.fullerting.bidLog.model.entity.BidLog;
// import com.ssafy.fullerting.bidLog.repository.BidRepository;
// import com.ssafy.fullerting.bidLog.service.BidService;
// import com.ssafy.fullerting.config.TestKafkaConfig;
// import com.ssafy.fullerting.deal.model.entity.Deal;
// import com.ssafy.fullerting.deal.repository.DealRepository;
// import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
// import com.ssafy.fullerting.exArticle.exception.ExArticleException;
// import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
// import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
// import com.ssafy.fullerting.global.kafka.BidConsumerService;
// import com.ssafy.fullerting.global.kafka.BidRequestMessage;
// import com.ssafy.fullerting.user.exception.UserErrorCode;
// import com.ssafy.fullerting.user.exception.UserException;
// import com.ssafy.fullerting.user.model.entity.MemberProfile;
// import com.ssafy.fullerting.user.repository.MemberRepository;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.context.annotation.Import;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDateTime;
// import java.util.*;
// import java.util.concurrent.*;
// import java.util.concurrent.atomic.AtomicInteger;
// import java.util.stream.Collectors;

// import static org.assertj.core.api.Assertions.*;

// /**
// * ?��찰희�? ?��?��?��?���??���?�? ?��?��?��?�� E2E ?��?��?��
// *
// * ?��?��?�� ?��?��리오:
// * 1. ?��?�� ?���? ?�� ?��?��?�� ?��?��?�� �?�? (카프카�?? ?��?�� 비동�? 처리)
// * 2. ?��찰�?? �?�? 로직 ?��?��?�� �?�?
// * 3. ?��찰자 ?�� 계산 ?��?��?�� �?�?
// * 4. Redis 캐시??? DB ?��?��?�� ?��?��?�� �?�?
// * 5. ?���? ?�� ?��?�� ?��?��?�� �?�?
// */
// @SpringBootTest
// @ActiveProfiles("test")
// @Import(TestKafkaConfig.class)
// public class BidConsistencyE2ETest {

// @Autowired
// private BidConsumerService bidConsumerService;

// @Autowired
// private BidService bidService;

// @Autowired
// private ExArticleRepository exArticleRepository;

// @Autowired
// private DealRepository dealRepository;

// @Autowired
// private MemberRepository memberRepository;

// @Autowired
// private BidRepository bidRepository;

// @Autowired
// private RedisTemplate<String, Object> redisTemplate;

// private ExArticle testArticle;
// private Deal testDeal;
// private List<MemberProfile> testBidders;
// private static final int INITIAL_PRICE = 1000;
// private static final int BID_INCREMENT = 100;

// @BeforeEach
// void setUp() {
// // ?��?��?�� ?��?��?�� ?��?��
// setupTestData();
// }

// @AfterEach
// void tearDown() {
// // Redis 캐시 ?���?
// clearRedisCache();
// }

// private void setupTestData() {
// try {
// // 기존 ?��?��?�� 조회 ?��?�� ?��?��
// MemberProfile seller = memberRepository.findByEmail("user001@example.com")
// .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

// testBidders = Arrays.asList(
// memberRepository.findByEmail("user002@example.com")
// .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER)),
// memberRepository.findByEmail("user003@example.com")
// .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER)),
// memberRepository.findByEmail("user004@example.com")
// .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER))
// );

// // ?��?��?�� 게시�? 조회
// testArticle = exArticleRepository.findById(4L)
// .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

// // ?��?��?��?�� 거래 ?���? ?��?��
// testDeal = Deal.builder()
// .dealCurPrice(INITIAL_PRICE)
// .bidderCount(0)
// .exArticle(testArticle)
// .build();
// testDeal = dealRepository.save(testDeal);

// } catch (Exception e) {
// throw new RuntimeException("?��?��?�� ?��?��?�� ?��?�� ?��?��", e);
// }
// }

// private void clearRedisCache() {
// String redisKey = "auction:" + testArticle.getId() + ":logs";
// redisTemplate.delete(redisKey);
// }

// @Test
// @DisplayName("?��?�� ?���? ?�� ?��?��?�� ?��?��?�� �?�? - 카프카�?? ?��?�� 비동�? 처리")
// void testConcurrentBidsDataConsistencyWithKafka() throws InterruptedException
// {
// // given
// int numberOfThreads = 20;
// ExecutorService executorService = Executors.newFixedThreadPool(10);
// CountDownLatch latch = new CountDownLatch(numberOfThreads);
// AtomicInteger successfulBids = new AtomicInteger(0);
// AtomicInteger failedBids = new AtomicInteger(0);

// // when - ?��?��?�� ?��?�� ?���? ?���??�� 카프카�?? ?��?�� 처리
// for (int i = 0; i < numberOfThreads; i++) {
// int bidPrice = INITIAL_PRICE + (i + 1) * BID_INCREMENT;
// int bidderIndex = i % testBidders.size();
// MemberProfile bidder = testBidders.get(bidderIndex);

// executorService.submit(() -> {
// try {
// // 카프�? 메시�?�? ?��?�� ?���? ?���? (?��?�� ?��?�� ?��경과 ?��?��)
// BidRequestMessage message = new BidRequestMessage(
// testArticle.getId(),
// bidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(message);
// successfulBids.incrementAndGet();
// } catch (Exception e) {
// failedBids.incrementAndGet();
// } finally {
// latch.countDown();
// }
// });
// }

// latch.await(15, TimeUnit.SECONDS);
// executorService.shutdown();

// // then - ?��?��?�� ?��?��?�� �?�?
// verifyDataConsistency();
// verifyBidOrderConsistency();
// verifyBidderCountConsistency();

// // ?��?��?�� 결과 �?�?
// assertThat(successfulBids.get()).isGreaterThan(0);
// assertThat(failedBids.get()).isLessThanOrEqualTo(numberOfThreads);
// }

// @Test
// @DisplayName("?��?�� ?���? ?�� ?��?��?�� ?��?��?�� �?�? - ?�� 미사?�� (카프�?)")
// void testConcurrentBidsDataConsistencyWithoutLock() throws
// InterruptedException {
// // given
// int numberOfThreads = 20;
// ExecutorService executorService = Executors.newFixedThreadPool(10);
// CountDownLatch latch = new CountDownLatch(numberOfThreads);
// AtomicInteger successfulBids = new AtomicInteger(0);
// AtomicInteger failedBids = new AtomicInteger(0);

// // when - ?��?��?�� ?��?�� ?���? ?���? (?�� 미사?��, 카프카�?? ?��?��)
// for (int i = 0; i < numberOfThreads; i++) {
// int bidPrice = INITIAL_PRICE + (i + 1) * BID_INCREMENT;
// int bidderIndex = i % testBidders.size();
// MemberProfile bidder = testBidders.get(bidderIndex);

// executorService.submit(() -> {
// try {
// BidRequestMessage message = new BidRequestMessage(
// testArticle.getId(),
// bidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequestwithouLock(message);
// successfulBids.incrementAndGet();
// } catch (Exception e) {
// failedBids.incrementAndGet();
// } finally {
// latch.countDown();
// }
// });
// }

// latch.await(15, TimeUnit.SECONDS);
// executorService.shutdown();

// // then - ?��?��?�� ?��?��?�� �?�?
// verifyDataConsistency();
// verifyBidOrderConsistency();
// verifyBidderCountConsistency();

// // ?��?��?�� 결과 �?�?
// assertThat(successfulBids.get()).isGreaterThan(0);
// assertThat(failedBids.get()).isLessThanOrEqualTo(numberOfThreads);
// }

// @Test
// @DisplayName("?��찰�?? �?�? 로직 ?��?��?�� �?�? - 카프�?")
// void testBidPriceValidationConsistency() {
// // given
// int invalidPrice = INITIAL_PRICE - 100; // ?��?���?보다 ?��??? �?�?
// MemberProfile bidder = testBidders.get(0);

// // when & then - ?��??? �?격으�? ?���? ?��?�� ?�� ?��?�� 발생
// assertThatThrownBy(() -> {
// BidRequestMessage message = new BidRequestMessage(
// testArticle.getId(),
// invalidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(message);
// }).isInstanceOf(RuntimeException.class)
// .hasMessageContaining("?��?���?보다 ?��??? 금액?�� ?��?��?��주세?��");

// // ?��찰�??�? �?경되�? ?��?��?���? ?��?��
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// assertThat(deal.getDealCurPrice()).isEqualTo(INITIAL_PRICE);
// }

// @Test
// @DisplayName("Redis 캐시??? DB ?��?��?�� ?��?��?�� �?�? - 카프�?")
// void testRedisCacheAndDBDataConsistency() {
// // given - ?���? ?��?��?�� ?��?�� (카프카�?? ?��?��)
// MemberProfile bidder = testBidders.get(0);
// int bidPrice = INITIAL_PRICE + BID_INCREMENT;

// BidRequestMessage message = new BidRequestMessage(
// testArticle.getId(),
// bidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(message);

// // when - Redis 캐시??? DB ?��?��?�� 조회
// String redisKey = "auction:" + testArticle.getId() + ":logs";
// List<Object> cachedBids = redisTemplate.opsForList().range(redisKey, 0, -1);

// List<BidLog> dbBids =
// bidRepository.findByDealId(testDeal.getId().toString());
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();

// // then - ?��?��?�� ?��?��?�� �?�?
// assertThat(cachedBids).isNotNull();
// assertThat(dbBids).hasSizeGreaterThan(0);
// assertThat(deal.getDealCurPrice()).isEqualTo(bidPrice);

// // Redis 캐시??? DB ?��?��?�� 개수 비교
// assertThat(cachedBids.size()).isEqualTo(dbBids.size());
// }

// @Test
// @DisplayName("?��찰자 ?�� 계산 ?��?��?�� �?�? - 카프�?")
// void testBidderCountCalculationConsistency() {
// // given - ?��?�� ?��?��?���? ?���? (카프카�?? ?��?��)
// for (int i = 0; i < testBidders.size(); i++) {
// MemberProfile bidder = testBidders.get(i);
// int bidPrice = INITIAL_PRICE + (i + 1) * BID_INCREMENT;

// BidRequestMessage message = new BidRequestMessage(
// testArticle.getId(),
// bidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(message);
// }

// // when - ?��?��?�� 방법?���? ?��찰자 ?�� 계산
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// List<BidLog> allBids = bidRepository.findByDealId(deal.getId().toString());

// int uniqueBiddersFromBids = (int) allBids.stream()
// .map(BidLog::getUserId)
// .distinct()
// .count();

// int bidderCountFromService = bidService.getBidderCount(deal);
// int bidderCountFromDeal = deal.getBidderCount();

// // then - 모든 방법?���? 계산?�� ?��찰자 ?���? ?��치하?���? ?��?��
// assertThat(uniqueBiddersFromBids).isEqualTo(testBidders.size());
// assertThat(bidderCountFromService).isEqualTo(testBidders.size());
// assertThat(bidderCountFromDeal).isEqualTo(testBidders.size());
// }

// @Test
// @DisplayName("?��?�� ?��?��?�� ?��?���? ?�� ?��?��?�� �?�? - 카프�?")
// void testSameUserRebidConsistency() {
// // given - �? 번째 ?���?
// MemberProfile bidder = testBidders.get(0);
// int firstBidPrice = INITIAL_PRICE + BID_INCREMENT;
// int secondBidPrice = firstBidPrice + BID_INCREMENT;

// // �? 번째 ?���? (카프카�?? ?��?��)
// BidRequestMessage firstMessage = new BidRequestMessage(
// testArticle.getId(),
// firstBidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(firstMessage);

// // when - 같�?? ?��?��?���? ?�� ?��??? �?격으�? ?��?���? (카프카�?? ?��?��)
// BidRequestMessage secondMessage = new BidRequestMessage(
// testArticle.getId(),
// secondBidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(secondMessage);

// // then - ?��?��?�� �?�?
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// List<BidLog> userBids = bidRepository.findByDealId(deal.getId().toString())
// .stream()
// .filter(bid -> bid.getUserId().equals(bidder.getId()))
// .collect(Collectors.toList());

// // ?��?���??�� �??�� ?��??? ?��찰�???��?�� ?��
// assertThat(deal.getDealCurPrice()).isEqualTo(secondBidPrice);

// // ?��?�� ?��?��?��?�� ?���? ?��?��?�� 2개여?�� ?��
// assertThat(userBids).hasSize(2);

// // ?��찰�??�? ?��름차?��?���? ?��?��?��?��?�� ?��
// List<Integer> bidPrices = userBids.stream()
// .map(BidLog::getBidLogPrice)
// .sorted()
// .collect(Collectors.toList());
// assertThat(bidPrices).containsExactly(firstBidPrice, secondBidPrice);
// }

// /**
// * ?��?��?�� ?��?��?�� �?�? ?��?�� 메서?��
// */
// private void verifyDataConsistency() {
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// List<BidLog> allBids = bidRepository.findByDealId(deal.getId().toString());

// // ?���? ?��?��?�� 존재?��?���? ?��?��
// assertThat(allBids).isNotEmpty();

// // ?��?���?�? ?��바르�? ?��?��?��?��?���? ?��?��
// int maxBidPrice = allBids.stream()
// .mapToInt(BidLog::getBidLogPrice)
// .max()
// .orElse(INITIAL_PRICE);
// assertThat(deal.getDealCurPrice()).isEqualTo(maxBidPrice);
// }

// /**
// * ?���? ?��?�� ?��?��?�� �?�? ?��?�� 메서?��
// */
// private void verifyBidOrderConsistency() {
// List<BidLog> allBids =
// bidRepository.findByDealId(testDeal.getId().toString());

// // ?��찰�??�? ?��름차?��?���? ?��?��?��?��?�� ?�� (?��간순)
// List<Integer> bidPrices = allBids.stream()
// .mapToInt(BidLog::getBidLogPrice)
// .boxed()
// .collect(Collectors.toList());

// List<Integer> sortedPrices = new ArrayList<>(bidPrices);
// Collections.sort(sortedPrices);

// assertThat(bidPrices).isEqualTo(sortedPrices);
// }

// /**
// * ?��찰자 ?�� ?��?��?�� �?�? ?��?�� 메서?��
// */
// private void verifyBidderCountConsistency() {
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// List<BidLog> allBids = bidRepository.findByDealId(deal.getId().toString());

// int uniqueBidders = (int) allBids.stream()
// .map(BidLog::getUserId)
// .distinct()
// .count();

// assertThat(deal.getBidderCount()).isEqualTo(uniqueBidders);
// }
// }
