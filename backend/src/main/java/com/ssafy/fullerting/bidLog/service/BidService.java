package com.ssafy.fullerting.bidLog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fullerting.bidLog.exception.BidErrorCode;
import com.ssafy.fullerting.bidLog.exception.BidException;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.dto.request.BidSelectRequest;
import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidService {

        private final BidRepository bidRepository;
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
         * 
         * @param exArticle     거래 게시글
         * @param proposedPrice 제안된 입찰가
         * @throws RuntimeException 제안된 가격이 현재 최고가보다 낮을 경우
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
         *
         * @param ex_article_id 조회할 거래 게시글 ID
         * @return 입찰 내역 목록 (BidLogResponse 리스트)
         * @throws ExArticleException 해당 게시글이 존재하지 않을 경우 발생
         */
        public List<BidLogResponse> selectbid(Long ex_article_id) {
                ExArticle exArticle = exArticleRepository.findById(ex_article_id)
                                .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

                // Redis 캐시에서 조회
                String logKey = "auction:" + ex_article_id + ":logs";
                List<Object> redisList = redisTemplate.opsForList().range(logKey, 0, -1);

                if (redisList != null && !redisList.isEmpty()) {
                        // 캐시된 데이터가 있으면 반환 (수동으로 변환)
                        return redisList.stream()
                                        .map(obj -> {
                                                if (obj instanceof LinkedHashMap) {
                                                        // LinkedHashMap을 BidLogResponse로 변환
                                                        return objectMapper.convertValue(obj, BidLogResponse.class);
                                                }
                                                return (BidLogResponse) obj;
                                        })
                                        .collect(Collectors.toList());
                }

                // Redis에 데이터가 없으면 MongoDB에서 조회
                log.info("No bid logs found in Redis for article {}. Fetching from MongoDB...", ex_article_id);

                // MongoDB에서 해당 게시글의 입찰 내역 조회 (최신순으로 정렬)
                Query query = new Query(Criteria.where("deal.exArticle.id").is(ex_article_id))
                                .with(Sort.by(Sort.Direction.DESC, "localDateTime"))
                                .limit(50); // 최대 50개만 가져오기

                List<BidLog> mongoBidLogs = mongoTemplate.find(query, BidLog.class, "bidLog");

                if (mongoBidLogs.isEmpty()) {
                        return Collections.emptyList();
                }

                // MongoDB에서 가져온 데이터를 Redis에 캐싱
                List<BidLogResponse> bidResponses = new ArrayList<>();
                String auctionKey = "auction:" + ex_article_id;

                for (BidLog bidLog : mongoBidLogs) {
                        MemberProfile user = userRepository.findById(bidLog.getUserId())
                                        .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

                        BidLogResponse response = bidLog.toBidLogSuggestionResponse(bidLog, user, mongoBidLogs.size());
                        bidResponses.add(response);

                        try {
                                // ObjectMapper를 사용하여 직렬화
                                String jsonBidDto = objectMapper.writeValueAsString(response);

                                // Redis에 캐싱 (최신순으로 저장)
                                redisTemplate.opsForList().rightPush(logKey, jsonBidDto);
                        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                                log.error("Failed to serialize bid response to JSON: {}", e.getMessage());
                                // Optionally rethrow as a runtime exception or handle appropriately
                                throw new RuntimeException("Failed to process bid data", e);
                        }
                }

                // Redis에 만료 시간 설정 (24시간)
                redisTemplate.expire(logKey, 24, TimeUnit.HOURS);

                // 경매 요약 정보도 업데이트 (가장 최근 입찰 정보로)
                if (!mongoBidLogs.isEmpty()) {
                        BidLog latestBid = mongoBidLogs.get(0);
                        Map<String, Object> auctionSummary = new HashMap<>();
                        auctionSummary.put("currentPrice", latestBid.getBidLogPrice());
                        auctionSummary.put("topBidderId", latestBid.getUserId());
                        auctionSummary.put("bidLogId", latestBid.getId());

                        redisTemplate.opsForHash().putAll(auctionKey, auctionSummary);
                        redisTemplate.expire(auctionKey, 24, TimeUnit.HOURS);
                }

                return bidResponses;
        }

        /**
         * 웹소켓을 통해 입찰을 처리하고 결과를 반환하는 메서드
         * 
         * @param exArticle         입찰이 진행되는 거래 게시글 정보
         * @param bidProposeRequest 입찰 요청 정보 (사용자 ID, 입찰가 포함)
         * @return 생성된 입찰 로그 정보
         * @throws BidException  거래 정보가 없는 경우 발생
         * @throws DealException 존재하지 않는 거래인 경우 발생
         * @throws UserException 존재하지 않는 사용자인 경우 발생
         * 
         * @작동 방식:
         *     1. 거래 정보 유효성 검증
         *     2. MongoDB에 입찰 로그 저장
         *     3. Redis에 입찰 정보 캐싱:
         *     - 경매 요약 정보(현재가, 최고 입찰자, 최근 로그 ID) 저장
         *     - 최근 입찰 기록(최대 50건) 저장
         *     4. 거래의 현재가 업데이트 및 저장
         */
        public BidLog socketdealbid(ExArticle exArticle, BidProposeRequest bidProposeRequest) {
                exArticle.getDeal().setDealCurPrice(bidProposeRequest.getDealCurPrice());
                if (exArticle.getDeal() == null) {
                        throw new BidException(BidErrorCode.NOT_DEAL);
                }

                Deal deal = dealRepository.findById(exArticle.getDeal().getId())
                                .orElseThrow(() -> new DealException(DealErrorCode.NOT_EXISTS));

                // MongoDB에 입찰 기록 저장
                BidLog bidLog = bidRepository.save(BidLog.builder()
                                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                                .deal(deal)
                                .userId(bidProposeRequest.getUserId())
                                .localDateTime(LocalDateTime.now())
                                .build());

                // ---- Redis 캐싱 ----
                // 1) 경매 요약 Hash : 현재가 / 최고 입찰자 / 최근 로그 ID
                String auctionKey = "auction:" + exArticle.getId();
                Map<String, Object> auctionSummary = Map.of(
                                "currentPrice", bidProposeRequest.getDealCurPrice(),
                                "topBidderId", bidProposeRequest.getUserId(),
                                "bidLogId", bidLog.getId());
                redisTemplate.opsForHash().putAll(auctionKey, auctionSummary);
                redisTemplate.expire(auctionKey, 24, TimeUnit.HOURS);

                // 2) 입찰 로그 List : 최근 N건만 유지 (예: 50건)
                String logKey = auctionKey + ":logs"; // auction:3:logs
                MemberProfile user = userRepository.findById(bidProposeRequest.getUserId())
                                .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
                BidLogResponse bidDto = bidLog.toBidLogSuggestionResponse(bidLog, user, 1);
                String jsonBidDto;
                try {
                        jsonBidDto = objectMapper.writeValueAsString(bidDto);
                } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                        log.error("Failed to serialize bid DTO to JSON: {}", e.getMessage());
                        // Optionally rethrow as a runtime exception or handle appropriately
                        throw new RuntimeException("Failed to process bid data", e);
                }
                redisTemplate.opsForList().leftPush(logKey, jsonBidDto);
                // 로그 리스트 만료 시간도 동일하게 맞춤
                redisTemplate.expire(logKey, 24, TimeUnit.HOURS);
                // 리스트 길이 제한 (메모리 절감)
                redisTemplate.opsForList().trim(logKey, 0, 49); // 최근 50개 유지

                Long size = redisTemplate.opsForHash().size(auctionKey);
                log.info("🔍 Redis hash size={}", size); // 0 이면 저장 실패
                log.info("🔍 Redis entries={}", redisTemplate.opsForHash().entries(auctionKey));
                log.info("🔍 Redis entries={}", redisTemplate.opsForHash().entries(auctionKey));

                // 저장된 ID 확인 로그
                log.info("✅ [Mongo] 저장된 입찰 로그 ID: {}", bidLog.getId());
                log.info("💰 [WebSocket] 입찰 요청 - 사용자 ID: {}, 입찰가: {}, 게시글 ID: {}",
                                bidProposeRequest.getUserId(), bidProposeRequest.getDealCurPrice(), exArticle.getId());

                // MongoDB에 실제 저장됐는지 바로 조회해서 검증
                BidLog savedCheck = bidRepository.findById(bidLog.getId()).orElse(null);
                if (savedCheck == null) {
                        log.warn("❌ [Mongo] 입찰 로그 저장 실패! ID: {}", bidLog.getId());
                } else {
                        log.info("✅ [Mongo] 입찰 로그 저장 확인 완료. 가격: {}", savedCheck.getBidLogPrice());
                }

                log.info("price" + bidLog.getBidLogPrice());
                Deal deal1 = exArticle.getDeal();
                log.info("💰 [WebSocket] 입찰 요청 - 사용자 ID: {}, 입찰가: {}, 게시글 ID: {}", bidProposeRequest.getUserId(),
                                bidProposeRequest.getDealCurPrice(), exArticle.getId());

                deal.setDealCurPrice(bidProposeRequest.getDealCurPrice());
                dealRepository.save(deal1);

                ExArticle article = exArticleRepository.save(exArticle);

                return bidLog;
        }

        /**
         * 일반 HTTP 요청을 통한 입찰 처리
         * 
         * @param exArticleId       거래 게시글 ID
         * @param bidProposeRequest 입찰 요청 정보
         * @return 처리된 입찰 로그
         */
        public BidLog dealbid(Long exArticleId, BidProposeRequest bidProposeRequest) {
                UserResponse user = userService.getUserInfo();
                ExArticle exArticle = exArticleRepository.findById(exArticleId)
                                .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

                validateBidPrice(exArticle, bidProposeRequest.getDealCurPrice());

                // 1. Save bid log to database
                BidLog bidLog = bidRepository.save(BidLog.builder()
                                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                                .localDateTime(LocalDateTime.now())
                                .userId(user.getId())
                                .deal(exArticle.getDeal())
                                .build());

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

                // 4. Save to MongoDB for persistence
                saveBidLogToMongo(bidLog);

                log.info("✅ New bid logged - Article ID: {}, User ID: {}, Price: {}",
                                exArticleId, user.getId(), bidProposeRequest.getDealCurPrice());

                return bidLog;
        }

        /**
         * 낙찰자 선정
         * 
         * @param exArticleId      거래 게시글 ID
         * @param bidSelectRequest 입찰 선택 요청 정보 (선택된 입찰 ID 등)
         * @return 선택된 입찰 로그
         */
        public BidLog choosetbid(Long exArticleId, BidSelectRequest bidSelectRequest) {

                UserResponse userResponse = userService.getUserInfo();
                // Fix: Call the static method on the class, not instance
                MemberProfile customUser = UserResponse.toEntity(userResponse);

                ExArticle article = exArticleRepository.findById(exArticleId)
                                .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

                BidLog bidLog = bidRepository.findById(((bidSelectRequest.getBidid())))
                                .orElseThrow(() -> new BidException(BidErrorCode.NOT_EXISTS));

                article.setDone(true);
                exArticleRepository.save(article);

                return bidLog;
        }

        /**
         * 거래 게시글의 입찰자 수 조회
         * 
         * @param deal 거래 정보
         * @return 고유 입찰자 수
         */
        public int getBidderCount(Deal deal) {
                return bidRepository.countDistinctUserIdsByExArticleId(deal.getExArticle().getId());
        }

        /**
         * 거래 게시글의 최고 입찰가 조회
         * 
         * @param exArticle 거래 게시글
         * @return 최고 입찰가 (입찰 내역이 없을 경우 0 반환)
         */
        public int getMaxBidPrice(ExArticle exArticle) {
                Optional<Integer> maxBidPriceOptional = bidRepository
                                .findMaxBidPriceByExArticleId(String.valueOf(exArticle.getId()));
                return maxBidPriceOptional.orElse(0);
        }

        /**
         * MongoDB에 입찰 로그 저장
         * 
         * @param bidLog 저장할 입찰 로그
         */
        private void saveBidLogToMongo(BidLog bidLog) {
                // MongoTemplate 는 POJO 를 그대로 BSON 으로 직렬화해 저장 가능하다.
                // JPA annotation 이 있어도 무시되며, 컬렉션 스키마가 자유롭기 때문에 insert 만 수행.
                // 동일 id 로 중복 저장을 막기 위해 upsert(save) 사용.
                mongoTemplate.save(bidLog, "bidLog");
        }
}
