package com.ssafy.fullerting.bidLog.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fullerting.bidLog.exception.BidErrorCode;
import com.ssafy.fullerting.bidLog.exception.BidException;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.dto.request.BidSelectRequest;
import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.kafka.BidProducerService;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.repository.MemberRepository;
import com.ssafy.fullerting.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidService {

        private final DealRepository dealRepository;
        private final ExArticleRepository exArticleRepository;
        private final MemberRepository userRepository;

        private final UserService userService;
        private final BidProducerService bidProducerService;

        private final RedisTemplate<String, Object> redisTemplate;
        private final MongoTemplate mongoTemplate;
        private final ObjectMapper objectMapper;

        /**
         * 입찰가 유효성 검사
         */
        public void validateBidPrice(ExArticle exArticle, int proposedPrice) {
                int maxBidPrice = getMaxBidPrice(exArticle);
                int currentPrice = exArticle.getDeal().getDealCurPrice();
                if (proposedPrice <= maxBidPrice || proposedPrice < currentPrice) {
                        throw new RuntimeException("최고가보다 낮은 입찰");
                }
        }

        /**
         * 거래 게시글의 입찰 내역 조회
         * Redis 캐시를 먼저 확인하고, 없을 경우 MongoDB에서 조회하여 캐싱합니다.
         */
        public List<BidLogResponse> selectbid(String ex_article_id) {
                log.info(">>> selectbid called for ex_article_id: {}", ex_article_id);

                String redisKey = "auction:" + ex_article_id + ":logs";
                List<BidLogResponse> cachedBids = getBidLogsFromRedis(redisKey);
                if (cachedBids != null && !cachedBids.isEmpty()) {
                        log.info(">>> Returning cached bids, size: {}", cachedBids.size());
                        return cachedBids;
                }

                Deal deal = dealRepository.findByExArticleId(Long.parseLong(ex_article_id))
                        .orElseThrow(() -> new RuntimeException("Deal not found"));

                List<BidLog> mongoBidLogs = mongoTemplate.find(
                        Query.query(Criteria.where("dealId").is(deal.getId()))
                                .with(Sort.by(Sort.Direction.DESC, "localDateTime")),
                        BidLog.class,
                        "bid_logs");

                log.info(">>> mongoBidLogs size: {}", mongoBidLogs.size());

                if (mongoBidLogs.isEmpty()) {
                        log.info(">>> mongoBidLogs is EMPTY! Returning empty list.");
                        return Collections.emptyList();
                }

                // 입찰자 수 계산
                int bidderCount = mongoBidLogs.stream()
                        .map(BidLog::getUserId)
                        .distinct()
                        .collect(Collectors.toList())
                        .size();

                List<BidLogResponse> responses = mongoBidLogs.stream()
                        .map((BidLog bidLog) -> {
                                MemberProfile user = userRepository.findById(bidLog.getUserId())
                                        .orElse(null);
                                Long exarticleid = bidLog.getDeal() != null && bidLog.getDeal().getExArticle() != null
                                        ? bidLog.getDeal().getExArticle().getId() : null;
                                return bidLog.toBidLogResponse(user, exarticleid, bidderCount);
                        })
                        .collect(Collectors.toList());

                // responses: List<BidLogResponse>
                if (!responses.isEmpty()) {
                        try {
                                redisTemplate.delete(redisKey); // 기존 캐시 삭제
                                for (BidLogResponse resp : responses) {
                                        redisTemplate.opsForList().rightPush(redisKey, objectMapper.writeValueAsString(resp));
                                }
                                redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
                        } catch (Exception e) {
                                log.error("Error caching bid logs in Redis", e);
                        }
                }


                return responses;
        }
        private List<BidLogResponse> getBidLogsFromRedis(String redisKey) {
                try {
                        List<Object> cachedList = redisTemplate.opsForList().range(redisKey, 0, -1);
                        if (cachedList != null && !cachedList.isEmpty()) {
                                List<BidLogResponse> responses = new ArrayList<>();
                                for (Object item : cachedList) {
                                        responses.add(objectMapper.readValue(item.toString(), BidLogResponse.class));
                                }
                                return responses;
                        }
                } catch (Exception e) {
                        log.error("Error while getting bid logs from Redis List", e);
                }
                return null;
        }




        private void updateRedisCache(ExArticle exArticle, BidLog savedBidLog, UserResponse user) {
                String redisKey = "auction:" + exArticle.getId() + ":logs";

                // 기존 캐시 무효화
                redisTemplate.delete(redisKey);

                // 경매 요약 정보 업데이트
                String auctionKey = "auction:" + exArticle.getId();
                Map<String, Object> auctionData = new HashMap<>();
                auctionData.put("currentPrice", savedBidLog.getBidLogPrice());
                auctionData.put("topBidderId", savedBidLog.getUserId());
                auctionData.put("bidLogId", savedBidLog.getId());

                redisTemplate.opsForHash().putAll(auctionKey, auctionData);
                redisTemplate.expire(auctionKey, 24, TimeUnit.HOURS);

                log.info("✅ Updated Redis cache for article: {}, new price: {}",
                        exArticle.getId(), savedBidLog.getBidLogPrice());
        }

        /**
         * WebSocket을 통해 입찰을 처리하고 결과를 반환하는 메서드
         */
        @Transactional
        public BidLog socketdealbid(ExArticle exArticle, BidProposeRequest bidProposeRequest) {
                if (exArticle.getType() != ExArticleType.DEAL) {
                        throw new ExArticleException(ExArticleErrorCode.DIFFERENT_TYPE);
                }

                // Get user ID from request or fallback to security context
                Long userId = bidProposeRequest.getUserId();
                if (userId == null) {
                        UserResponse userResponse = userService.getUserInfo();
                        userId = userResponse.getId();
                }

                MemberProfile member = userRepository.findById(userId)
                        .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

                // Check if bid price is valid
                if (bidProposeRequest.getDealCurPrice() <= 0) {
                        throw new BidException(BidErrorCode.NOT_DEAL);
                }

                // 1. MongoDB에 입찰 로그 저장
                BidLog bidLog = BidLog.builder()
                        .deal(exArticle.getDeal())
                        .userId(userId)
                        .localDateTime(LocalDateTime.now())
                        .bidLogPrice(bidProposeRequest.getDealCurPrice())
                        .build();

                BidLog savedBidLog = mongoTemplate.save(bidLog, "bid_logs");

                // 2. Redis List에 입찰 로그 추가 (실시간 캐싱)
                try {
                        String redisKey = "auction:" + exArticle.getId() + ":logs";
                        BidLogResponse bidLogResponse = savedBidLog.toBidLogResponse(member, exArticle.getId(), 0); // bidderCount 필요시 계산
                        redisTemplate.opsForList().rightPush(redisKey, objectMapper.writeValueAsString(bidLogResponse));
                        redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
                } catch (Exception e) {
                        log.error("Error adding bid log to Redis List", e);
                }

                // 3. (선택) 경매 요약 정보도 Redis Hash로 갱신
                try {
                        String auctionKey = "auction:" + exArticle.getId();
                        Map<String, Object> auctionData = new HashMap<>();
                        auctionData.put("currentPrice", savedBidLog.getBidLogPrice());
                        auctionData.put("topBidderId", savedBidLog.getUserId());
                        auctionData.put("bidLogId", savedBidLog.getId());
                        redisTemplate.opsForHash().putAll(auctionKey, auctionData);
                        redisTemplate.expire(auctionKey, 24, TimeUnit.HOURS);
                } catch (Exception e) {
                        log.error("Error updating auction summary in Redis", e);
                }

                return savedBidLog;
        }


        /**
         * 낙찰자 선정
         */
        public BidLog choosetbid(Long exArticleId, BidSelectRequest bidSelectRequest) {
                UserResponse userResponse = userService.getUserInfo();
                MemberProfile customUser = userRepository.findById(userResponse.getId())
                        .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

                ExArticle article = exArticleRepository.findById(exArticleId)
                        .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

                BidLog bidLog = mongoTemplate.findById(bidSelectRequest.getBidid(), BidLog.class, "bid_logs");
                if (bidLog == null) {
                        throw new BidException(BidErrorCode.NOT_EXISTS);
                }

                article.setDone(true);
                exArticleRepository.save(article);

                return bidLog;
        }

        /**
         * 거래 게시글의 입찰자 수 조회
         */
        public int getBidderCount(Deal deal) {
                return mongoTemplate.find(
                                Query.query(Criteria.where("dealId").is(deal.getId())),
                                BidLog.class,
                                "bid_logs").stream()
                        .map(BidLog::getUserId)
                        .distinct()
                        .collect(Collectors.toList())
                        .size();
        }

        /**
         * 거래 게시글의 최고 입찰가 조회
         */
        public int getMaxBidPrice(ExArticle exArticle) {
                // MongoDB aggregation을 사용한 최고 입찰가 조회
                org.springframework.data.mongodb.core.aggregation.Aggregation aggregation = org.springframework.data.mongodb.core.aggregation.Aggregation
                        .newAggregation(
                                org.springframework.data.mongodb.core.aggregation.Aggregation.match(
                                        org.springframework.data.mongodb.core.query.Criteria
                                                .where("dealId")
                                                .is(exArticle.getDeal().getId())),
                                org.springframework.data.mongodb.core.aggregation.Aggregation.group()
                                        .max("bidLogPrice").as("maxPrice"));

                // 여기서 결과 타입을 Document로 변경
                org.springframework.data.mongodb.core.aggregation.AggregationResults<Document> results = mongoTemplate
                        .aggregate(
                                aggregation, "bid_logs",
                                Document.class);

                Document result = results.getUniqueMappedResult();
                return result != null ? result.getInteger("maxPrice", 0) : 0;
        }

        /**
         * MongoDB에 입찰 로그 저장
         */
        private void saveBidLogToMongo(BidLog bidLog) {
                mongoTemplate.save(bidLog, "bidLog");
        }
}
