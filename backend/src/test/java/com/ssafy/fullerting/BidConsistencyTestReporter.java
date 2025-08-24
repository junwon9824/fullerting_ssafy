package com.ssafy.fullerting;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 입찰 정합성 테스트 결과를 분석하고 리포트를 생성하는 유틸리티 클래스
 */
@Component
public class BidConsistencyTestReporter {

    private static final Logger log = LoggerFactory.getLogger(BidConsistencyTestReporter.class);

    private final BidRepository bidRepository;
    private final DealRepository dealRepository;
    private final ExArticleRepository exArticleRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public BidConsistencyTestReporter(BidRepository bidRepository,
                                    DealRepository dealRepository,
                                    ExArticleRepository exArticleRepository,
                                    RedisTemplate<String, Object> redisTemplate) {
        this.bidRepository = bidRepository;
        this.dealRepository = dealRepository;
        this.exArticleRepository = exArticleRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 입찰 정합성 상태를 종합적으로 분석하고 리포트를 생성
     */
    public void generateConsistencyReport(Long exArticleId) {
        log.info("=== 입찰 정합성 분석 리포트 ===");
        log.info("게시글 ID: {}", exArticleId);
        log.info("생성 시간: {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        try {
            // 1. 기본 거래 정보 분석
            analyzeDealInfo(exArticleId);

            // 2. 입찰 내역 분석
            analyzeBidHistory(exArticleId);

            // 3. 데이터 정합성 검증
            verifyDataConsistency(exArticleId);

            // 4. Redis 캐시 상태 분석
            analyzeRedisCache(exArticleId);

            // 5. 정합성 점수 계산
            calculateConsistencyScore(exArticleId);

        } catch (Exception e) {
            log.error("리포트 생성 중 오류 발생: {}", e.getMessage(), e);
        }

        log.info("=== 리포트 생성 완료 ===");
    }

    /**
     * 거래 정보 분석
     */
    private void analyzeDealInfo(Long exArticleId) {
        try {
            Deal deal = dealRepository.findByExArticleId(exArticleId).orElse(null);
            if (deal == null) {
                log.warn("거래 정보를 찾을 수 없습니다: {}", exArticleId);
                return;
            }

            log.info("--- 거래 정보 분석 ---");
            log.info("현재가: {}원", deal.getDealCurPrice());
            log.info("입찰자 수: {}명", deal.getBidderCount());
            log.info("거래 ID: {}", deal.getId());
            
        } catch (Exception e) {
            log.error("거래 정보 분석 실패: {}", e.getMessage());
        }
    }

    /**
     * 입찰 내역 분석
     */
    private void analyzeBidHistory(Long exArticleId) {
        try {
            Deal deal = dealRepository.findByExArticleId(exArticleId).orElse(null);
            if (deal == null) return;

            List<BidLog> bids = bidRepository.findByDealId(deal.getId().toString());

            log.info("--- 입찰 내역 분석 ---");
            log.info("총 입찰 건수: {}건", bids.size());

            if (!bids.isEmpty()) {
                // 입찰가 분포 분석
                Map<Integer, Long> priceDistribution = bids.stream()
                        .collect(Collectors.groupingBy(BidLog::getBidLogPrice, Collectors.counting()));

                log.info("입찰가 분포:");
                priceDistribution.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> log.info("  {}원: {}건", entry.getKey(), entry.getValue()));

                // 입찰자별 입찰 횟수 분석
                Map<Long, Long> bidderDistribution = bids.stream()
                        .collect(Collectors.groupingBy(BidLog::getUserId, Collectors.counting()));

                log.info("입찰자별 입찰 횟수:");
                bidderDistribution.entrySet().stream()
                        .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                        .forEach(entry -> log.info("  사용자 {}: {}회", entry.getKey(), entry.getValue()));

                // 시간별 입찰 패턴 분석
                Map<String, Long> timeDistribution = bids.stream()
                        .collect(Collectors.groupingBy(
                                bid -> bid.getLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                                Collectors.counting()
                        ));

                log.info("시간별 입찰 패턴:");
                timeDistribution.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> log.info("  {}: {}건", entry.getKey(), entry.getValue()));
            }

        } catch (Exception e) {
            log.error("입찰 내역 분석 실패: {}", e.getMessage());
        }
    }

    /**
     * 데이터 정합성 검증
     */
    private void verifyDataConsistency(Long exArticleId) {
        try {
            Deal deal = dealRepository.findByExArticleId(exArticleId).orElse(null);
            if (deal == null) return;

            List<BidLog> bids = bidRepository.findByDealId(deal.getId().toString());

            log.info("--- 데이터 정합성 검증 ---");

            // 1. 현재가 정합성 검증
            int maxBidPrice = bids.stream()
                    .mapToInt(BidLog::getBidLogPrice)
                    .max()
                    .orElse(0);

            boolean priceConsistent = deal.getDealCurPrice() == maxBidPrice;
            log.info("현재가 정합성: {} (DB: {}, 계산값: {})",
                    priceConsistent ? "✅" : "❌",
                    deal.getDealCurPrice(),
                    maxBidPrice);

            // 2. 입찰자 수 정합성 검증
            long uniqueBidders = bids.stream()
                    .map(BidLog::getUserId)
                    .distinct()
                    .count();

            boolean bidderCountConsistent = deal.getBidderCount() == uniqueBidders;
            log.info("입찰자 수 정합성: {} (DB: {}, 계산값: {})",
                    bidderCountConsistent ? "✅" : "❌",
                    deal.getBidderCount(),
                    uniqueBidders);

            // 3. 입찰 순서 정합성 검증
            boolean orderConsistent = true;
            if (bids.size() > 1) {
                for (int i = 1; i < bids.size(); i++) {
                    if (bids.get(i).getBidLogPrice() <= bids.get(i-1).getBidLogPrice()) {
                        orderConsistent = false;
                        break;
                    }
                }
            }
            log.info("입찰 순서 정합성: {} (오름차순 정렬)",
                    orderConsistent ? "✅" : "❌");

        } catch (Exception e) {
            log.error("데이터 정합성 검증 실패: {}", e.getMessage());
        }
    }

    /**
     * Redis 캐시 상태 분석
     */
    private void analyzeRedisCache(Long exArticleId) {
        try {
            String redisKey = "auction:" + exArticleId + ":logs";
            List<Object> cachedBids = redisTemplate.opsForList().range(redisKey, 0, -1);

            log.info("--- Redis 캐시 상태 분석 ---");
            log.info("캐시 키: {}", redisKey);
            log.info("캐시된 데이터 수: {}", cachedBids != null ? cachedBids.size() : 0);

            // DB와 캐시 데이터 비교
            Deal deal = dealRepository.findByExArticleId(exArticleId).orElse(null);
            if (deal != null) {
                List<BidLog> dbBids = bidRepository.findByDealId(deal.getId().toString());
                boolean cacheConsistent = cachedBids != null && cachedBids.size() == dbBids.size();

                log.info("캐시-DB 정합성: {} (캐시: {}, DB: {})",
                        cacheConsistent ? "✅" : "❌",
                        cachedBids != null ? cachedBids.size() : 0,
                        dbBids.size());
            }

        } catch (Exception e) {
            log.error("Redis 캐시 분석 실패: {}", e.getMessage());
        }
    }

    /**
     * 정합성 점수 계산
     */
    private void calculateConsistencyScore(Long exArticleId) {
        try {
            Deal deal = dealRepository.findByExArticleId(exArticleId).orElse(null);
            if (deal == null) return;

            List<BidLog> bids = bidRepository.findByDealId(deal.getId().toString());

            int totalChecks = 0;
            int passedChecks = 0;

            // 1. 현재가 정합성 체크
            totalChecks++;
            int maxBidPrice = bids.stream()
                    .mapToInt(BidLog::getBidLogPrice)
                    .max()
                    .orElse(0);
            if (deal.getDealCurPrice() == maxBidPrice) passedChecks++;

            // 2. 입찰자 수 정합성 체크
            totalChecks++;
            long uniqueBidders = bids.stream()
                    .map(BidLog::getUserId)
                    .distinct()
                    .count();
            if (deal.getBidderCount() == uniqueBidders) passedChecks++;

            // 3. 입찰 순서 정합성 체크
            totalChecks++;
            boolean orderConsistent = true;
            if (bids.size() > 1) {
                for (int i = 1; i < bids.size(); i++) {
                    if (bids.get(i).getBidLogPrice() <= bids.get(i-1).getBidLogPrice()) {
                        orderConsistent = false;
                        break;
                    }
                }
            }
            if (orderConsistent) passedChecks++;

            // 4. Redis 캐시 정합성 체크
            totalChecks++;
            String redisKey = "auction:" + exArticleId + ":logs";
            List<Object> cachedBids = redisTemplate.opsForList().range(redisKey, 0, -1);
            if (cachedBids != null && cachedBids.size() == bids.size()) passedChecks++;

            double consistencyScore = (double) passedChecks / totalChecks * 100;

            log.info("--- 정합성 점수 ---");
            log.info("전체 검증 항목: {}개", totalChecks);
            log.info("통과 항목: {}개", passedChecks);
            log.info("정합성 점수: {:.1f}%", consistencyScore);

            if (consistencyScore >= 90) {
                log.info("정합성 상태: 🟢 우수");
            } else if (consistencyScore >= 70) {
                log.info("정합성 상태: 🟡 양호");
            } else {
                log.info("정합성 상태: 🔴 주의 필요");
            }

        } catch (Exception e) {
            log.error("정합성 점수 계산 실패: {}", e.getMessage());
        }
    }
}
