package com.ssafy.fullerting.bidLog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fullerting.bidLog.exception.BidErrorCode;
import com.ssafy.fullerting.bidLog.exception.BidException;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.dto.request.BidSelectRequest;
import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.deal.exception.DealErrorCode;
import com.ssafy.fullerting.deal.exception.DealException;
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
import org.bson.Document; // 추가된 부분
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        @Transactional
        public List<BidLogResponse> selectbid(String ex_article_id) {
                String redisKey = "auction:" + ex_article_id + ":logs";

                // Redis에서 조회 시도
                List<BidLogResponse> cachedBids = getBidLogsFromRedis(redisKey);
                if (cachedBids != null && !cachedBids.isEmpty()) {
                        return cachedBids;
                }

                // MongoDB에서 조회
                List<BidLog> mongoBidLogs = mongoTemplate.find(
                                Query.query(Criteria.where("deal.exArticle.id").is(ex_article_id))
                                                .with(Sort.by(Sort.Direction.DESC, "localDateTime")),
                                BidLog.class,
                                "bid_logs");

                if (mongoBidLogs.isEmpty()) {
                        return Collections.emptyList();
                }

                // MongoDB 결과를 Redis에 캐싱
                List<BidLogResponse> responses = mongoBidLogs.stream()
                                .map((BidLog bidLog) -> {
                                        MemberProfile user = userRepository.findById(bidLog.getUserId())
                                                        .orElseThrow(() -> new UserException(
                                                                        UserErrorCode.NOT_EXISTS_USER));
                                        return bidLog.toBidLogResponse(user);
                                })
                                .collect(Collectors.toList());

                // Redis에 캐싱
                if (!responses.isEmpty()) {
                        try {
                                String jsonResponse = objectMapper.writeValueAsString(responses);
                                redisTemplate.opsForValue().set(redisKey, jsonResponse, 24, TimeUnit.HOURS);
                        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                                log.error("Failed to serialize bid logs for Redis", e);
                        }
                }

                return responses;
        }

        private List<BidLogResponse> getBidLogsFromRedis(String redisKey) {
                try {
                        String cachedData = (String) redisTemplate.opsForValue().get(redisKey);
                        if (cachedData != null) {
                                return objectMapper.readValue(
                                                cachedData,
                                                objectMapper.getTypeFactory().constructCollectionType(List.class,
                                                                BidLogResponse.class));
                        }
                } catch (Exception e) {
                        log.error("Error while getting bid logs from Redis", e);
                }
                return null;
        }

        /**
         * 웹소켓을 통해 입찰을 처리하고 결과를 반환하는 메서드
         */
        @Transactional
        public BidLog dealbid(String exArticleId, BidProposeRequest bidProposeRequest) {
                UserResponse user = userService.getUserInfo();
                ExArticle exArticle = exArticleRepository.findById(exArticleId)
                                .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

                validateBidPrice(exArticle, bidProposeRequest.getDealCurPrice());

                // MongoDB에 입찰 로그 저장
                BidLog bidLog = BidLog.builder()
                                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                                .localDateTime(LocalDateTime.now())
                                .userId(user.getId())
                                .deal(exArticle.getDeal())
                                .build();

                // MongoDB에 저장
                BidLog savedBidLog = mongoTemplate.save(bidLog, "bid_logs");

                // Redis 캐시 업데이트
                updateRedisCache(exArticle, savedBidLog, user);

                return savedBidLog;
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
         * 일반 HTTP 요청을 통한 입찰 처리
         */
        public BidLog dealbid(Long exArticleId, BidProposeRequest bidProposeRequest) {
                UserResponse user = userService.getUserInfo();
                ExArticle exArticle = exArticleRepository.findById(exArticleId.toString())
                                .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

                validateBidPrice(exArticle, bidProposeRequest.getDealCurPrice());

                // 1. Save bid log to MongoDB
                BidLog bidLog = BidLog.builder()
                                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                                .localDateTime(LocalDateTime.now())
                                .userId(user.getId())
                                .deal(exArticle.getDeal())
                                .build();

                // Save to MongoDB
                mongoTemplate.save(bidLog, "bid_logs");

                // 2. Get user entity for response
                MemberProfile userEntity = userRepository.findById(user.getId())
                                .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

                // 3. Update Redis cache
                String auctionKey = "auction:" + exArticleId;
                String logKey = auctionKey + ":logs";

                // 3.1 Update auction summary
                Map<String, Object> auctionSummary = new HashMap<>();
                auctionSummary.put("currentPrice", bidProposeRequest.getDealCurPrice());
                auctionSummary.put("topBidderId", user.getId());
                auctionSummary.put("bidLogId", bidLog.getId());
                auctionSummary.put("bidTime", LocalDateTime.now().toString());

                redisTemplate.opsForHash().putAll(auctionKey, auctionSummary);
                redisTemplate.expire(auctionKey, 24, TimeUnit.HOURS);

                // 3.2 Add new bid log to Redis list
                BidLogResponse bidDto = bidLog.toBidLogSuggestionResponse(bidLog, userEntity, 1);

                String jsonBidDto;
                try {
                        jsonBidDto = objectMapper.writeValueAsString(bidDto);
                } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                        log.error("Failed to serialize bid DTO to JSON: {}", e.getMessage());
                        throw new RuntimeException("Failed to process bid data", e);
                }

                // JSON 문자열로 저장
                redisTemplate.opsForList().leftPush(logKey, jsonBidDto);
                // Trim the list to keep only the last 50 entries
                redisTemplate.opsForList().trim(logKey, 0, 49);
                // Set expiration
                redisTemplate.expire(logKey, 24, TimeUnit.HOURS);

                log.info("✅ New bid logged - Article ID: {}, User ID: {}, Price: {}",
                                exArticleId, user.getId(), bidProposeRequest.getDealCurPrice());

                return bidLog;
        }

        /**
         * WebSocket을 통해 입찰을 처리하고 결과를 반환하는 메서드
         */
        @Transactional
        public BidLog socketdealbid(ExArticle exArticle, BidProposeRequest bidProposeRequest) {
                // Verify the article exists and is an AUCTION type
                if (exArticle == null) {
                        throw new ExArticleException(ExArticleErrorCode.NOT_EXISTS);
                }

                if (exArticle.getType() != ExArticleType.DEAL) {
                        throw new ExArticleException(ExArticleErrorCode.DIFFERENT_TYPE);
                }

                // Get user
                UserResponse userResponse = userService.getUserInfo();
                MemberProfile member = userService.getUserEntityById(userResponse.getId())
                                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));

                // Check if bid price is valid
                if (bidProposeRequest.getDealCurPrice() <= 0) {
                        throw new BidException(BidErrorCode.INVALID_BID_PRICE);
                }

                // Create and save bid log
                BidLog bidLog = BidLog.builder()
                                .exArticle(exArticle)
                                .member(member)
                                .bidPrice(bidProposeRequest.getDealCurPrice())
                                .bidTime(LocalDateTime.now())
                                .build();

                return mongoTemplate.save(bidLog, "bid_logs");
        }

        /**
         * 낙찰자 선정
         */
        public BidLog choosetbid(Long exArticleId, BidSelectRequest bidSelectRequest) {
                UserResponse userResponse = userService.getUserInfo();
                MemberProfile customUser = UserResponse.toEntity(userResponse);

                ExArticle article = exArticleRepository.findById(exArticleId.toString())
                                .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

                BidLog bidLog = mongoTemplate.findById(bidSelectRequest.getBidid(), BidLog.class, "bid_logs")
                                .orElseThrow(() -> new BidException(BidErrorCode.NOT_EXISTS));

                article.setDone(true);
                exArticleRepository.save(article);

                return bidLog;
        }

        /**
         * 거래 게시글의 입찰자 수 조회
         */
        public int getBidderCount(Deal deal) {
                return mongoTemplate.find(
                                Query.query(Criteria.where("deal.exArticle.id").is(deal.getExArticle().getId())),
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
                                                                                .where("deal.exArticle.id")
                                                                                .is(exArticle.getId())),
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
