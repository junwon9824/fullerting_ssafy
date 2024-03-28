package com.ssafy.fullerting.trans.service;

import com.ssafy.fullerting.deal.model.dto.request.DealProposeRequest;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleAllResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.trans.exception.TransErrorCode;
import com.ssafy.fullerting.trans.exception.TransException;
import com.ssafy.fullerting.trans.model.dto.response.MyAllTransResponse;
import com.ssafy.fullerting.trans.model.dto.response.TransResponse;
import com.ssafy.fullerting.trans.model.entity.Trans;
import com.ssafy.fullerting.trans.repository.TransRepository;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.service.UserService;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransService {
    private final TransRepository transRepository;
    private final ExArticleRepository exArticleRepository;
    private final UserService userService;

    public List<ExArticleAllResponse> selectAllshare() {

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = userResponse.toEntity(userResponse);

//        List<TransResponse> transResponse = new ArrayList<>();
        List<ExArticleAllResponse> transResponse = new ArrayList<>();

        List<ExArticleResponse> exArticleResponses = exArticleRepository
                .findAllByType(ExArticleType.SHARING)
                .stream()
                .map(exArticles -> exArticles.toResponse(exArticles, customUser))
                .filter(exArticleResponse -> exArticleResponse.getExArticleType().equals(ExArticleType.SHARING))
                .collect(Collectors.toList());

        log.info("exArticleResponsesexArticleResponses" + exArticleResponses.toString());
// sharing 만 가져와야한다.

        transResponse = exArticleResponses.stream().map(exArticleResponse -> {
                    ExArticleAllResponse transResponse1 = new ExArticleAllResponse();
//                    transResponse1.setExArticleResponse(exArticleResponse);

                    Trans trans = transRepository.findByExArticleId(exArticleResponse.getExArticleId())
                            .orElseThrow(() -> new TransException(TransErrorCode.NOT_EXISTS));

                    transResponse1.setTransResponse(trans.toResponse(trans));
                    transResponse1.setExArticleResponse(
                            trans.getExArticle().toResponse(trans.getExArticle(), customUser));
                    transResponse1.setPackDiaryResponse(trans.getExArticle().getPackDiary().toResponse(trans.getExArticle().getPackDiary()));
                    return transResponse1;
                })
                .collect(Collectors.toList());

        log.info("trrr" + transResponse);
        return transResponse;
    }

    public List<MyAllTransResponse> selectTrans() { //나의 일반거래

        UserResponse userResponse = userService.getUserInfo();
        CustomUser customUser = UserResponse.toEntity(userResponse);

        List<Trans> trans = transRepository.findAllTrans(customUser.getId());

//        log.info("transssssssssss"+trans.stream().map(trans1 -> trans1.getExArticle()));


        List<MyAllTransResponse> transResponse = trans.stream().map(trans1 ->
                trans1.toMyAllTransResponse(trans1, customUser)).collect(Collectors.toList());


        ExArticle exArticle;
        transResponse = transResponse.stream().map(myAllTransResponse -> {
                    ExArticle exArticle2 = exArticleRepository.findById(myAllTransResponse.getExarticleid())
                            .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
                    myAllTransResponse.setExArticleResponse(exArticle2.toResponse(exArticle2, customUser));
                    return myAllTransResponse;
                }
        ).collect(Collectors.toList());


        return transResponse;

    }
}
