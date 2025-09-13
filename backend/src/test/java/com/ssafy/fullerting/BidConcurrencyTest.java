// package com.ssafy.fullerting;

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
// import org.springframework.beans.MethodInvocationException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import java.time.LocalDateTime;
// import java.util.concurrent.CountDownLatch;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;

// import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest
// public class BidConcurrencyTest {

// @Autowired
// private BidConsumerService bidConsumerService;

// @Autowired
// private ExArticleRepository exArticleRepository;

// @Autowired
// private DealRepository dealRepository;

// @Autowired
// private MemberRepository memberRepository;

// private ExArticle testArticle;
// private MemberProfile testBidder;

// @BeforeEach
// void setUp() {
// // ?…Œ?Š¤?Š¸ ?°?´?„° ?„¤? •
// // MemberProfile sellerProfile =
// MemberProfile.builder().nickname("testSeller").build();
// MemberProfile sellerProfile =
// memberRepository.findByEmail("user001@example.com").orElseThrow(()->new
// UserException(UserErrorCode.NOT_EXISTS_USER));
// // memberRepository.save(sellerProfile);

// // testBidder = MemberProfile.builder().nickname("testBidder").build();
// testBidder =
// memberRepository.findByEmail("user002@example.com").orElseThrow(()->new
// UserException(UserErrorCode.NOT_EXISTS_USER));

// // memberRepository.save(testBidder);

// Deal deal = Deal.builder().dealCurPrice(100).bidderCount(0).build();

// // testArticle = ExArticle.builder().
// // user(sellerProfile).title("Test Article").content("Concurrency Test")
// // .isDone(false).created_at(LocalDateTime.now()).deal(deal).
// //
// // build();
// testArticle = exArticleRepository.findById(4L).orElseThrow(()->new
// ExArticleException(ExArticleErrorCode.NOT_EXISTS));

// deal.setexarticle(testArticle);
// // exArticleRepository.save(testArticle);
// }

// // @AfterEach
// // void tearDown() {
// // // ?…Œ?Š¤?Š¸ ?°?´?„° ? •ë¦?
// // exArticleRepository.deleteAll();
// // memberRepository.deleteAll();
// // }

// @Test
// @DisplayName("?—¬?Ÿ¬ ?Š¤? ˆ?“œê°? ?™?‹œ?— ?ž…ì°°ì„ ?š”ì²??•  ?•Œ ë¹„ê???  ?½?´ ?°?´?„°
// ? •?•©?„±?„ ë³´ìž¥?•˜?Š”ì§? ?…Œ?Š¤?Š¸")
// void testConcurrentBidsWithPessimisticLock() throws InterruptedException {
// // given
// int numberOfThreads = 100;
// ExecutorService executorService = Executors.newFixedThreadPool(32);
// CountDownLatch latch = new CountDownLatch(numberOfThreads);
// int initialPrice = 100;

// // when
// for (int i = 0; i < numberOfThreads; i++) {
// int bidPrice = initialPrice + i + 1; // 101, 102, ..., 200
// executorService.submit(() -> {
// try {
// BidRequestMessage message = new BidRequestMessage(testArticle.getId(),
// bidPrice,
// testBidder.getNickname());
// // bidConsumerService.consumeBidRequest(message);
// bidConsumerService.consumeBidRequestwithouLock(message);
// } finally {
// latch.countDown();
// }
// });
// }

// latch.await(); // ëª¨ë“  ?Š¤? ˆ?“œê°? ?ž‘?—…?„ ë§ˆì¹  ?•Œê¹Œì?? ???ê¸?
// executorService.shutdown();

// // then
// Deal finalDeal =
// dealRepository.findByExArticleId(testArticle.getId()).orElseThrow();
// int expectedFinalPrice = initialPrice + numberOfThreads; // 100 + 100 = 200

// assertThat(finalDeal.getDealCurPrice()).isEqualTo(expectedFinalPrice);
// }
// }