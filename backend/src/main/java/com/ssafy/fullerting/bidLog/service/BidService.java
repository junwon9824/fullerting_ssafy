package com.ssafy.fullerting.bidLog.service;

import com.ssafy.fullerting.bidLog.exception.BidErrorCode;
import com.ssafy.fullerting.bidLog.exception.BidException;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.dto.request.BidSelectRequest;
import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
//import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
//import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.bidLog.repository.BidLogMongoRepository;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidService {
    private final BidLogMongoRepository bidRepository;
    private final DealRepository dealRepository;
    private final ExArticleRepository exArticleRepository;
    private final MemberRepository userRepository;

    private final UserService userService;
    private final BidProducerService bidProducerService;


    @Transactional
    public void processBidWithLock(Long exArticleId, int dealCurPrice, MemberProfile bidder, String redirectUrl) {
        // 🔒 비관적 락으로 게시물 조회
        ExArticle exArticle = exArticleRepository.findByIdWithLock(exArticleId)
                .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        int currentPrice = exArticle.getDeal().getDealCurPrice();

        log.info("현재가: {}, 희망가: {}", currentPrice, dealCurPrice);

        if (dealCurPrice <= currentPrice) {
            throw new RuntimeException("최고가보다 높은 금액을 입력해주세요!! 현재가: " + currentPrice);
        }

        // ✅ 가격 갱신
        exArticle.getDeal().setDealCurPrice(dealCurPrice);

        // ✅ 카프카로 알림 전송
        bidProducerService.kafkaalarmproduce(bidder, exArticle, redirectUrl);
    }


    public void deal(BidProposeRequest bidProposeRequest, MemberProfile user, Long ex_article_id) {
        LocalDateTime time = LocalDateTime.now();


        ExArticle exArticle = exArticleRepository.findById(ex_article_id).orElseThrow
                (() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        Long dealid = exArticle.getDeal().getId();

        Deal deal = dealRepository.findById(dealid).orElseThrow(
                () -> new DealException(DealErrorCode.NOT_EXISTS));

//        BidLog bidLog = bidRepository.save(BidLog.builder()
//                .bidLogPrice(bidProposeRequest.getDealCurPrice())
//                .localDateTime(time)
//                .userId(user.getId())
//                .deal(deal)
//                .build());

        BidLog bidLog = bidRepository.save(BidLog.builder()
                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                .localDateTime(time)
                .userId(user.getId())
                .dealId(deal.getId())
                .build());

    }

    public List<BidLogResponse> selectbid(Long ex_article_id) {
        ExArticle exArticle = exArticleRepository.findById(ex_article_id).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        if (!exArticle.getType().equals(ExArticleType.DEAL)) {
            throw new BidException(BidErrorCode.NOT_DEAL);
        }

//        List<BidLog> bidLog = bidRepository.findAllByDealId(exArticle.getDeal().getId());
        List<BidLog> bidLog = bidRepository.findAllByDealId(exArticle.getDeal().getId());

        HashSet<Long> bidLogs = new HashSet<>();

//        for (BidLog bl : bidLog) {
//            bidLogs.add(bl.getUserId());
//        }

        for (BidLog bl : bidLog) {
            bidLogs.add(bl.getUserId());
        }

        List<BidLogResponse> bidLogResponses = bidLog.stream().map(bidLog1 -> {
                    MemberProfile user = userRepository.
                            findById(bidLog1.getUserId()).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
                    return bidLog1.toBidLogSuggestionResponse(bidLog1, user, bidLogs.size());
                })
//                .sorted(Comparator.comparing(BidLogResponse::getBidLogPrice).reversed())
                .collect(Collectors.toList());

        return bidLogResponses;

    }

//    // 웹소켓 전용
//    // 입찰 제안을 DB에 저장한다 -> 입찰기록을 만든다
//    public BidLog socketdealbid(ExArticle exArticle, BidProposeRequest bidProposeRequest) {
//        exArticle.getDeal().setDealCurPrice(bidProposeRequest.getDealCurPrice());
//        if (exArticle.getDeal() == null) {
//            throw new BidException(BidErrorCode.NOT_DEAL);
//        }
//
//
//        Deal deal = dealRepository.findById(exArticle.getDeal().getId()).orElseThrow(() ->
//                new DealException(DealErrorCode.NOT_EXISTS));
//
//        BidLog bidLog = bidRepository.save(BidLog.builder()
//                .bidLogPrice(bidProposeRequest.getDealCurPrice())
//                .deal(deal)
//                .userId(bidProposeRequest.getUserId())
//                .localDateTime(LocalDateTime.now())
//                .build());
//
//
//        log.info("price" + bidLog.getBidLogPrice());
//        Deal deal1 = exArticle.getDeal();
//        log.info("💰 [WebSocket] 입찰 요청 - 사용자 ID: {}, 입찰가: {}, 게시글 ID: {}", bidProposeRequest.getUserId(), bidProposeRequest.getDealCurPrice(), exArticle.getId());
//
//        deal.setDealCurPrice(bidProposeRequest.getDealCurPrice());
//        dealRepository.save(deal1);
//
//        ExArticle article = exArticleRepository.save(exArticle);
//
//
//        return bidLog;
//    }

    // 입찰 제안을 mongoDB에 저장한다 -> 입찰기록을 만든다
    public BidLog socketdealbid(ExArticle exArticle, BidProposeRequest bidProposeRequest) {
        exArticle.getDeal().setDealCurPrice(bidProposeRequest.getDealCurPrice());
        if (exArticle.getDeal() == null) {
            throw new BidException(BidErrorCode.NOT_DEAL);
        }


        Deal deal = dealRepository.findById(exArticle.getDeal().getId()).orElseThrow(() ->
                new DealException(DealErrorCode.NOT_EXISTS));

        // MongoDB에 입찰 기록 저장
        BidLog bidLog = bidRepository.save(BidLog.builder()
                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                .dealId(deal.getId())
                .userId(bidProposeRequest.getUserId())
                .localDateTime(LocalDateTime.now())
                .build());

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
        log.info("💰 [WebSocket] 입찰 요청 - 사용자 ID: {}, 입찰가: {}, 게시글 ID: {}", bidProposeRequest.getUserId(), bidProposeRequest.getDealCurPrice(), exArticle.getId());

        deal.setDealCurPrice(bidProposeRequest.getDealCurPrice());
        dealRepository.save(deal1);

        ExArticle article = exArticleRepository.save(exArticle);


        return bidLog;
    }

    //    public BidLog dealbid(Long exArticleId, BidProposeRequest bidProposeRequest) {
    public BidLog dealbid(Long exArticleId, BidProposeRequest bidProposeRequest) {

        UserResponse userResponse = userService.getUserInfo();
        MemberProfile customUser = userResponse.toEntity(userResponse);

        ExArticle exArticle = exArticleRepository.findById(exArticleId).orElseThrow(() -> new ExArticleException(
                ExArticleErrorCode.NOT_EXISTS));

        exArticle.getDeal().setDealCurPrice(bidProposeRequest.getDealCurPrice());
        exArticleRepository.save(exArticle);
        if (exArticle.getDeal() == null) {
            throw new BidException(BidErrorCode.NOT_DEAL);
        }

        Deal deal = dealRepository.findById(exArticle.getDeal().getId()).orElseThrow(() ->
                new DealException(DealErrorCode.NOT_EXISTS));

//            BidLog bidLog = bidRepository.save(BidLog.builder()
//                    .bidLogPrice(bidProposeRequest.getDealCurPrice())
//                    .deal(deal)
//                    .userId(customUser.getId())
//                    .localDateTime(LocalDateTime.now())
//                    .build());


        BidLog bidLog = bidRepository.save(BidLog.builder()
                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                .dealId(deal.getId())
                .userId(customUser.getId())
                .localDateTime(LocalDateTime.now())
                .build());


        log.info("💰 입찰 요청 - 사용자 ID: {}, 입찰가: {}, 게시글 ID: {}", customUser.getId(), bidProposeRequest.getDealCurPrice(), exArticleId);

//        bidRepository.save(bidLog);

        return bidLog;
    }

    public BidLog choosetbid(Long exArticleId, BidSelectRequest bidSelectRequest) {

        UserResponse userResponse = userService.getUserInfo();
        MemberProfile customUser = userResponse.toEntity(userResponse);

        ExArticle article = exArticleRepository.findById(exArticleId).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        BidLog bidLog = bidRepository.findById(String.valueOf((bidSelectRequest.getBidid()))).orElseThrow(() ->
                new BidException(BidErrorCode.NOT_EXISTS));

        article.setDone(true);
        exArticleRepository.save(article);

        return bidLog;
    }


    public int getBidderCount(Deal deal) {
        return bidRepository.countDistinctUserIdByDealId((deal.getId()));
//        return bidRepository.countDistinctUserIdsByExArticleId((exArticle.getId()));
    }

    public int getMaxBidPrice(ExArticle exArticle) {
        Optional<Integer> maxBidPriceOptional = bidRepository.findMaxBidPriceByExArticleId(String.valueOf(exArticle.getId()));
        return maxBidPriceOptional.orElse(0);
    }
}
