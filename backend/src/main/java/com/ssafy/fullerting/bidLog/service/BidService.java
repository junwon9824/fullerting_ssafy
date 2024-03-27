package com.ssafy.fullerting.bidLog.service;

import com.ssafy.fullerting.bidLog.exception.BidErrorCode;
import com.ssafy.fullerting.bidLog.exception.BidException;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.dto.request.BidSelectRequest;
import com.ssafy.fullerting.bidLog.model.dto.response.BidLogResponse;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.deal.exception.DealErrorCode;
import com.ssafy.fullerting.deal.exception.DealException;
import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final DealRepository dealRepository;
    private final ExArticleRepository exArticleRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    public void deal(BidProposeRequest bidProposeRequest, CustomUser user, Long ex_article_id) {
        LocalDateTime time = LocalDateTime.now();


        ExArticle exArticle = exArticleRepository.findById(ex_article_id).orElseThrow
                (() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        Long dealid = exArticle.getDeal().getId();

        Deal deal = dealRepository.findById(dealid).orElseThrow(
                () -> new DealException(DealErrorCode.NOT_EXISTS));

        BidLog bidLog = bidRepository.save(BidLog.builder()
                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                .localDateTime(time)
                .userId(user.getId())
                .deal(deal)
                .build());

    }

    public List<BidLogResponse> selectbid(Long ex_article_id) {
        ExArticle exArticle = exArticleRepository.findById(ex_article_id).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        if (!exArticle.getType().equals(ExArticleType.DEAL)) {
            throw new BidException(BidErrorCode.NOT_DEAL);
        }

        List<BidLog> bidLog = bidRepository.findAllByDealId(exArticle.getDeal().getId());

        List<BidLogResponse> bidLogResponses = bidLog.stream().map(bidLog1 -> {
                    CustomUser user = userRepository.
                            findById(bidLog1.getUserId()).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
                    return bidLog1.toBidLogsuggestionResponse(bidLog1, user);
                })
                .collect(Collectors.toList());

        return bidLogResponses;

    }


    public BidLog socketdealbid(Long exArticleId, BidProposeRequest bidProposeRequest) {

//        UserResponse userResponse = userService.getUserInfo();
//        CustomUser customUser = userResponse.toEntity(userResponse);
//        CustomUser customUser = userRepository.findById(bidProposeRequest.getUserId()).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

        ExArticle exArticle = exArticleRepository.findById(exArticleId).orElseThrow(() -> new ExArticleException(
                ExArticleErrorCode.NOT_EXISTS));

        if (exArticle.getDeal() == null) {
            throw new BidException(BidErrorCode.NOT_DEAL);
        }

        Deal deal = dealRepository.findById(exArticle.getDeal().getId()).orElseThrow(() ->
                new DealException(DealErrorCode.NOT_EXISTS));

        BidLog bidLog = bidRepository.save(BidLog.builder()
                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                .deal(deal)
                .userId(bidProposeRequest.getUserId())
                .localDateTime(LocalDateTime.now())
                .build());

        return bidLog;
    }

    public BidLog choosetbid(Long exArticleId, BidSelectRequest bidSelectRequest) {

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

        ExArticle article = exArticleRepository.findById(exArticleId).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        BidLog bidLog = bidRepository.findById(bidSelectRequest.getBidid()).orElseThrow(() ->
                new BidException(BidErrorCode.NOT_EXISTS));

        article.setDone(true);
        exArticleRepository.save(article);


        return bidLog;
    }

    public BidLog dealbid(Long exArticleId, BidProposeRequest bidProposeRequest) {

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

//        CustomUser customUser = userRepository.findById(bidProposeRequest.getUserId()).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

        ExArticle exArticle = exArticleRepository.findById(exArticleId).orElseThrow(() -> new ExArticleException(
                ExArticleErrorCode.NOT_EXISTS));

        if (exArticle.getDeal() == null) {
            throw new BidException(BidErrorCode.NOT_DEAL);
        }

        Deal deal = dealRepository.findById(exArticle.getDeal().getId()).orElseThrow(() ->
                new DealException(DealErrorCode.NOT_EXISTS));

        BidLog bidLog = bidRepository.save(BidLog.builder()
                .bidLogPrice(bidProposeRequest.getDealCurPrice())
                .deal(deal)
                .userId(customUser.getId())
                .localDateTime(LocalDateTime.now())
                .build());

        return bidLog;
    }
}
