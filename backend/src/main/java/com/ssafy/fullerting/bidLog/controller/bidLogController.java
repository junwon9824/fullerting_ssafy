package com.ssafy.fullerting.bidLog.controller;

import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.dto.request.BidSelectRequest;
import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
//import com.ssafy.fullerting.bidLog.model.entity.BidLog;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.List;
import java.util.Map;

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

//    @PostMapping("/{ex_article_id}/deal")
//    @Operation(summary = "가격 제안하기 ", description = "가격 제안하기")
//    public ResponseEntity<MessageUtils> register(@RequestBody BidProposeRequest bidProposeRequest,
//                                                 @PathVariable Long ex_article_id, @AuthenticationPrincipal String email) {
//
//        UserResponse userResponse = userService.getUserInfo();
//        MemberProfile customUser = userResponse.toEntity(userResponse);
//
//        bidService.deal(bidProposeRequest, customUser, ex_article_id);
//        log.info("[New User]: {}", bidProposeRequest.toString());
//        return ResponseEntity.ok().body(MessageUtils.success());
//    }

    @GetMapping("/{ex_article_id}/suggestion")
    @Operation(summary = "입찰 제안조회하기 ", description = "특정 게시물의 입찰 제안 조회 하기")
    public ResponseEntity<MessageUtils> selectbid(@AuthenticationPrincipal String email, @PathVariable Long ex_article_id) {
        // Redis에서 경매 상태 조회
        String auctionKey = "auction:" + ex_article_id;
        Map<Object, Object> auctionStatus = redisTemplate.opsForHash().entries(auctionKey);
        if (!auctionStatus.isEmpty()) {
            // Redis에 상태가 있으면 이 값으로 응답
            return ResponseEntity.ok().body(MessageUtils.success(auctionStatus));
        }
        // 없으면 기존 로직
        List<BidLogResponse> bidLogs = bidService.selectbid(ex_article_id);
        log.info("[show deal]: {}");
        return ResponseEntity.ok().body(MessageUtils.success(bidLogs));
    }


    @PostMapping("/{ex_article_id}/deal_bid")
    @Operation(summary = "입찰 제안하기 ", description = "특정 게시물의 입찰 제안 하기")
    public ResponseEntity<MessageUtils> dealbid(@RequestBody BidProposeRequest bidProposeRequest, @PathVariable Long ex_article_id) {

//        BidLog bidLog = bidService.dealbid(ex_article_id, bidProposeRequest );
        BidLog bidLog = bidService.dealbid(ex_article_id, bidProposeRequest );
        UserResponse userResponse=userService.getUserInfo();
        MemberProfile customUser=userResponse.toEntity(userResponse);

        BidLogResponse bidLogResponse = bidLog.toBidLogResponse(bidLog,customUser);
        return ResponseEntity.ok().body(MessageUtils.success(bidLogResponse));

    }

    @PostMapping("/{ex_article_id}/select")
    @Operation(summary = "입찰 선택하기 ", description = "특정 게시물의 입찰 선택하기 ")
    public ResponseEntity<MessageUtils> choosetbid(@RequestBody BidSelectRequest bidSelectRequest, @PathVariable Long ex_article_id) {

        BidLog bidLog = bidService.choosetbid(ex_article_id, bidSelectRequest);

//        log.info("[choosetbid  ]: {}");

        return ResponseEntity.ok().body(MessageUtils.success(bidLog.getId()));
    }

}
