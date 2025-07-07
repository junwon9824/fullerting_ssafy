package com.ssafy.fullerting.bidLog.controller;

import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.dto.request.BidSelectRequest;
import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exchanges")
@Tag(name = "입찰 기능 API", description = "입찰과 관련된 기능 제공")
public class bidLogController {

    private final DealService dealService;
    private final BidService bidService;
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;

    // @PostMapping("/{ex_article_id}/deal")
    // @Operation(summary = "가격 제안하기 ", description = "가격 제안하기")
    // public ResponseEntity<MessageUtils> register(@RequestBody BidProposeRequest
    // bidProposeRequest,
    // @PathVariable Long ex_article_id, @AuthenticationPrincipal String email) {
    //
    // UserResponse userResponse = userService.getUserInfo();
    // MemberProfile customUser = userResponse.toEntity(userResponse);
    //
    // bidService.deal(bidProposeRequest, customUser, ex_article_id);
    // log.info("[New User]: {}", bidProposeRequest.toString());
    // return ResponseEntity.ok().body(MessageUtils.success());
    // }

    @GetMapping("/{ex_article_id}/suggestion")
    @PreAuthorize("permitAll()")
    @Operation(summary = "입찰 제안 조회", description = "특정 게시물의 입찰 제안 조회 – 비로그인 허용")
    public ResponseEntity<MessageUtils> selectbid(@PathVariable Long ex_article_id) {
        // Redis에서 경매 상태 조회 (최근 입찰 로그 리스트 우선)
        String baseKey = "auction:" + ex_article_id;
        String logKey = baseKey + ":logs";
        log.info("Redis 키 조회 시도: " + logKey);
        List<Object> redisList = redisTemplate.opsForList().range(logKey, 0, -1);
        log.info("Redis 조회 결과 - 리스트 크기: " + (redisList != null ? redisList.size() : "null"));

        if (redisList != null && !redisList.isEmpty()) {
            log.info("첫 번째 요소 클래스: " + (redisList.get(0) != null ? redisList.get(0).getClass().getName() : "null"));

            List<BidLogResponse> cachedList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            for (Object item : redisList) {
                try {
                    if (item instanceof Map) {
                        // LinkedHashMap을 BidLogResponse로 변환
                        BidLogResponse bidLog = objectMapper.convertValue(item, BidLogResponse.class);
                        cachedList.add(bidLog);
                    } else if (item instanceof BidLogResponse) {
                        cachedList.add((BidLogResponse) item);
                    }
                } catch (Exception e) {
                    log.error("Redis에서 가져온 데이터 변환 중 오류 발생: " + e.getMessage(), e);
                }
            }

            log.info("변환 성공한 항목 수: " + cachedList.size());

            if (!cachedList.isEmpty()) {
                log.info("Redis 캐시에서 데이터 반환");
                return ResponseEntity.ok().body(MessageUtils.success(cachedList));
            }
        }
        log.info("Redis에 데이터가 없거나 변환에 실패하여 DB에서 조회");
        List<BidLogResponse> bidLogs = bidService.selectbid(ex_article_id);
        return ResponseEntity.ok().body(MessageUtils.success(bidLogs));
    }

    @PostMapping("/{ex_article_id}/deal_bid")
    @Operation(summary = "입찰 제안하기 ", description = "특정 게시물의 입찰 제안 하기")
    public ResponseEntity<MessageUtils> dealbid(@RequestBody BidProposeRequest bidProposeRequest,
            @PathVariable Long ex_article_id) {

        // BidLog bidLog = bidService.dealbid(ex_article_id, bidProposeRequest );
        BidLog bidLog = bidService.dealbid(ex_article_id, bidProposeRequest);
        UserResponse userResponse = userService.getUserInfo();
        MemberProfile customUser = userResponse.toEntity(userResponse);

        BidLogResponse bidLogResponse = bidLog.toBidLogResponse(bidLog, customUser);
        return ResponseEntity.ok().body(MessageUtils.success(bidLogResponse));

    }

    @PostMapping("/{ex_article_id}/select")
    @Operation(summary = "입찰 선택하기 ", description = "특정 게시물의 입찰 선택하기 ")
    public ResponseEntity<MessageUtils> choosetbid(@RequestBody BidSelectRequest bidSelectRequest,
            @PathVariable Long ex_article_id) {

        BidLog bidLog = bidService.choosetbid(ex_article_id, bidSelectRequest);

        // log.info("[choosetbid ]: {}");

        return ResponseEntity.ok().body(MessageUtils.success(bidLog.getId()));
    }

}
