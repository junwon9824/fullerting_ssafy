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
// * ?…ì°°í¬ë§? ? •?•©?„±?œ ì§??—¬ë¶?ë¥? ?™•?¸?•˜?Š” E2E ?…Œ?Š¤?Š¸
// *
// * ?…Œ?Š¤?Š¸ ?‹œ?‚˜ë¦¬ì˜¤:
// * 1. ?™?‹œ ?…ì°? ?‹œ ?°?´?„° ? •?•©?„± ê²?ì¦? (ì¹´í”„ì¹´ë?? ?†µ?•œ ë¹„ë™ê¸? ì²˜ë¦¬)
// * 2. ?…ì°°ê?? ê²?ì¦? ë¡œì§ ? •?•©?„± ê²?ì¦?
// * 3. ?…ì°°ì ?ˆ˜ ê³„ì‚° ? •?•©?„± ê²?ì¦?
// * 4. Redis ìºì‹œ??? DB ?°?´?„° ? •?•©?„± ê²?ì¦?
// * 5. ?‚™ì°? ?›„ ?ƒ?ƒœ ? •?•©?„± ê²?ì¦?
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
// // ?…Œ?Š¤?Š¸ ?°?´?„° ?„¤? •
// setupTestData();
// }

// @AfterEach
// void tearDown() {
// // Redis ìºì‹œ ? •ë¦?
// clearRedisCache();
// }

// private void setupTestData() {
// try {
// // ê¸°ì¡´ ?‚¬?š©? ì¡°íšŒ ?˜?Š” ?ƒ?„±
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

// // ?…Œ?Š¤?Š¸ ê²Œì‹œê¸? ì¡°íšŒ
// testArticle = exArticleRepository.findById(4L)
// .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

// // ?…Œ?Š¤?Š¸?š© ê±°ë˜ ? •ë³? ?ƒ?„±
// testDeal = Deal.builder()
// .dealCurPrice(INITIAL_PRICE)
// .bidderCount(0)
// .exArticle(testArticle)
// .build();
// testDeal = dealRepository.save(testDeal);

// } catch (Exception e) {
// throw new RuntimeException("?…Œ?Š¤?Š¸ ?°?´?„° ?„¤? • ?‹¤?Œ¨", e);
// }
// }

// private void clearRedisCache() {
// String redisKey = "auction:" + testArticle.getId() + ":logs";
// redisTemplate.delete(redisKey);
// }

// @Test
// @DisplayName("?™?‹œ ?…ì°? ?‹œ ?°?´?„° ? •?•©?„± ê²?ì¦? - ì¹´í”„ì¹´ë?? ?†µ?•œ ë¹„ë™ê¸? ì²˜ë¦¬")
// void testConcurrentBidsDataConsistencyWithKafka() throws InterruptedException
// {
// // given
// int numberOfThreads = 20;
// ExecutorService executorService = Executors.newFixedThreadPool(10);
// CountDownLatch latch = new CountDownLatch(numberOfThreads);
// AtomicInteger successfulBids = new AtomicInteger(0);
// AtomicInteger failedBids = new AtomicInteger(0);

// // when - ?™?‹œ?— ?—¬?Ÿ¬ ?…ì°? ?š”ì²??„ ì¹´í”„ì¹´ë?? ?†µ?•´ ì²˜ë¦¬
// for (int i = 0; i < numberOfThreads; i++) {
// int bidPrice = INITIAL_PRICE + (i + 1) * BID_INCREMENT;
// int bidderIndex = i % testBidders.size();
// MemberProfile bidder = testBidders.get(bidderIndex);

// executorService.submit(() -> {
// try {
// // ì¹´í”„ì¹? ë©”ì‹œì§?ë¥? ?†µ?•´ ?…ì°? ?š”ì²? (?‹¤? œ ?š´?˜ ?™˜ê²½ê³¼ ?™?¼)
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

// // then - ?°?´?„° ? •?•©?„± ê²?ì¦?
// verifyDataConsistency();
// verifyBidOrderConsistency();
// verifyBidderCountConsistency();

// // ?…Œ?Š¤?Š¸ ê²°ê³¼ ê²?ì¦?
// assertThat(successfulBids.get()).isGreaterThan(0);
// assertThat(failedBids.get()).isLessThanOrEqualTo(numberOfThreads);
// }

// @Test
// @DisplayName("?™?‹œ ?…ì°? ?‹œ ?°?´?„° ? •?•©?„± ê²?ì¦? - ?½ ë¯¸ì‚¬?š© (ì¹´í”„ì¹?)")
// void testConcurrentBidsDataConsistencyWithoutLock() throws
// InterruptedException {
// // given
// int numberOfThreads = 20;
// ExecutorService executorService = Executors.newFixedThreadPool(10);
// CountDownLatch latch = new CountDownLatch(numberOfThreads);
// AtomicInteger successfulBids = new AtomicInteger(0);
// AtomicInteger failedBids = new AtomicInteger(0);

// // when - ?™?‹œ?— ?—¬?Ÿ¬ ?…ì°? ?š”ì²? (?½ ë¯¸ì‚¬?š©, ì¹´í”„ì¹´ë?? ?†µ?•´)
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

// // then - ?°?´?„° ? •?•©?„± ê²?ì¦?
// verifyDataConsistency();
// verifyBidOrderConsistency();
// verifyBidderCountConsistency();

// // ?…Œ?Š¤?Š¸ ê²°ê³¼ ê²?ì¦?
// assertThat(successfulBids.get()).isGreaterThan(0);
// assertThat(failedBids.get()).isLessThanOrEqualTo(numberOfThreads);
// }

// @Test
// @DisplayName("?…ì°°ê?? ê²?ì¦? ë¡œì§ ? •?•©?„± ê²?ì¦? - ì¹´í”„ì¹?")
// void testBidPriceValidationConsistency() {
// // given
// int invalidPrice = INITIAL_PRICE - 100; // ?˜„?¬ê°?ë³´ë‹¤ ?‚®??? ê°?ê²?
// MemberProfile bidder = testBidders.get(0);

// // when & then - ?‚®??? ê°?ê²©ìœ¼ë¡? ?…ì°? ?‹œ?„ ?‹œ ?˜ˆ?™¸ ë°œìƒ
// assertThatThrownBy(() -> {
// BidRequestMessage message = new BidRequestMessage(
// testArticle.getId(),
// invalidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(message);
// }).isInstanceOf(RuntimeException.class)
// .hasMessageContaining("?˜„?¬ê°?ë³´ë‹¤ ?†’??? ê¸ˆì•¡?„ ?…? ¥?•´ì£¼ì„¸?š”");

// // ?…ì°°ê??ê°? ë³?ê²½ë˜ì§? ?•Š?•˜?Š”ì§? ?™•?¸
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// assertThat(deal.getDealCurPrice()).isEqualTo(INITIAL_PRICE);
// }

// @Test
// @DisplayName("Redis ìºì‹œ??? DB ?°?´?„° ? •?•©?„± ê²?ì¦? - ì¹´í”„ì¹?")
// void testRedisCacheAndDBDataConsistency() {
// // given - ?…ì°? ?°?´?„° ?ƒ?„± (ì¹´í”„ì¹´ë?? ?†µ?•´)
// MemberProfile bidder = testBidders.get(0);
// int bidPrice = INITIAL_PRICE + BID_INCREMENT;

// BidRequestMessage message = new BidRequestMessage(
// testArticle.getId(),
// bidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(message);

// // when - Redis ìºì‹œ??? DB ?°?´?„° ì¡°íšŒ
// String redisKey = "auction:" + testArticle.getId() + ":logs";
// List<Object> cachedBids = redisTemplate.opsForList().range(redisKey, 0, -1);

// List<BidLog> dbBids =
// bidRepository.findByDealId(testDeal.getId().toString());
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();

// // then - ?°?´?„° ? •?•©?„± ê²?ì¦?
// assertThat(cachedBids).isNotNull();
// assertThat(dbBids).hasSizeGreaterThan(0);
// assertThat(deal.getDealCurPrice()).isEqualTo(bidPrice);

// // Redis ìºì‹œ??? DB ?°?´?„° ê°œìˆ˜ ë¹„êµ
// assertThat(cachedBids.size()).isEqualTo(dbBids.size());
// }

// @Test
// @DisplayName("?…ì°°ì ?ˆ˜ ê³„ì‚° ? •?•©?„± ê²?ì¦? - ì¹´í”„ì¹?")
// void testBidderCountCalculationConsistency() {
// // given - ?—¬?Ÿ¬ ?‚¬?š©?ê°? ?…ì°? (ì¹´í”„ì¹´ë?? ?†µ?•´)
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

// // when - ?‹¤?–‘?•œ ë°©ë²•?œ¼ë¡? ?…ì°°ì ?ˆ˜ ê³„ì‚°
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// List<BidLog> allBids = bidRepository.findByDealId(deal.getId().toString());

// int uniqueBiddersFromBids = (int) allBids.stream()
// .map(BidLog::getUserId)
// .distinct()
// .count();

// int bidderCountFromService = bidService.getBidderCount(deal);
// int bidderCountFromDeal = deal.getBidderCount();

// // then - ëª¨ë“  ë°©ë²•?œ¼ë¡? ê³„ì‚°?•œ ?…ì°°ì ?ˆ˜ê°? ?¼ì¹˜í•˜?Š”ì§? ?™•?¸
// assertThat(uniqueBiddersFromBids).isEqualTo(testBidders.size());
// assertThat(bidderCountFromService).isEqualTo(testBidders.size());
// assertThat(bidderCountFromDeal).isEqualTo(testBidders.size());
// }

// @Test
// @DisplayName("?™?¼ ?‚¬?š©? ?¬?…ì°? ?‹œ ? •?•©?„± ê²?ì¦? - ì¹´í”„ì¹?")
// void testSameUserRebidConsistency() {
// // given - ì²? ë²ˆì§¸ ?…ì°?
// MemberProfile bidder = testBidders.get(0);
// int firstBidPrice = INITIAL_PRICE + BID_INCREMENT;
// int secondBidPrice = firstBidPrice + BID_INCREMENT;

// // ì²? ë²ˆì§¸ ?…ì°? (ì¹´í”„ì¹´ë?? ?†µ?•´)
// BidRequestMessage firstMessage = new BidRequestMessage(
// testArticle.getId(),
// firstBidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(firstMessage);

// // when - ê°™ì?? ?‚¬?š©?ê°? ?” ?†’??? ê°?ê²©ìœ¼ë¡? ?¬?…ì°? (ì¹´í”„ì¹´ë?? ?†µ?•´)
// BidRequestMessage secondMessage = new BidRequestMessage(
// testArticle.getId(),
// secondBidPrice,
// bidder.getNickname()
// );
// bidConsumerService.consumeBidRequest(secondMessage);

// // then - ? •?•©?„± ê²?ì¦?
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// List<BidLog> userBids = bidRepository.findByDealId(deal.getId().toString())
// .stream()
// .filter(bid -> bid.getUserId().equals(bidder.getId()))
// .collect(Collectors.toList());

// // ?˜„?¬ê°??Š” ê°??¥ ?†’??? ?…ì°°ê???—¬?•¼ ?•¨
// assertThat(deal.getDealCurPrice()).isEqualTo(secondBidPrice);

// // ?•´?‹¹ ?‚¬?š©??˜ ?…ì°? ?‚´?—­?´ 2ê°œì—¬?•¼ ?•¨
// assertThat(userBids).hasSize(2);

// // ?…ì°°ê??ê°? ?˜¤ë¦„ì°¨?ˆœ?œ¼ë¡? ? •? ¬?˜?–´?•¼ ?•¨
// List<Integer> bidPrices = userBids.stream()
// .map(BidLog::getBidLogPrice)
// .sorted()
// .collect(Collectors.toList());
// assertThat(bidPrices).containsExactly(firstBidPrice, secondBidPrice);
// }

// /**
// * ?°?´?„° ? •?•©?„± ê²?ì¦? ?—¬?¼ ë©”ì„œ?“œ
// */
// private void verifyDataConsistency() {
// Deal deal = dealRepository.findById(testDeal.getId()).orElseThrow();
// List<BidLog> allBids = bidRepository.findByDealId(deal.getId().toString());

// // ?…ì°? ?‚´?—­?´ ì¡´ì¬?•˜?Š”ì§? ?™•?¸
// assertThat(allBids).isNotEmpty();

// // ?˜„?¬ê°?ê°? ?˜¬ë°”ë¥´ê²? ?„¤? •?˜?—ˆ?Š”ì§? ?™•?¸
// int maxBidPrice = allBids.stream()
// .mapToInt(BidLog::getBidLogPrice)
// .max()
// .orElse(INITIAL_PRICE);
// assertThat(deal.getDealCurPrice()).isEqualTo(maxBidPrice);
// }

// /**
// * ?…ì°? ?ˆœ?„œ ? •?•©?„± ê²?ì¦? ?—¬?¼ ë©”ì„œ?“œ
// */
// private void verifyBidOrderConsistency() {
// List<BidLog> allBids =
// bidRepository.findByDealId(testDeal.getId().toString());

// // ?…ì°°ê??ê°? ?˜¤ë¦„ì°¨?ˆœ?œ¼ë¡? ? •? ¬?˜?–´?•¼ ?•¨ (?‹œê°„ìˆœ)
// List<Integer> bidPrices = allBids.stream()
// .mapToInt(BidLog::getBidLogPrice)
// .boxed()
// .collect(Collectors.toList());

// List<Integer> sortedPrices = new ArrayList<>(bidPrices);
// Collections.sort(sortedPrices);

// assertThat(bidPrices).isEqualTo(sortedPrices);
// }

// /**
// * ?…ì°°ì ?ˆ˜ ? •?•©?„± ê²?ì¦? ?—¬?¼ ë©”ì„œ?“œ
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
