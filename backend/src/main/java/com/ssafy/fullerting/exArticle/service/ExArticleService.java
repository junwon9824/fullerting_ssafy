package com.ssafy.fullerting.exArticle.service;

import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleDoneRequest;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleAllResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleDetailResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleKeywordResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.favorite.repository.favoriteRepository;

import com.ssafy.fullerting.global.s3.model.entity.response.S3ManyFilesResponse;
import com.ssafy.fullerting.global.s3.servcie.AmazonS3Service;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.image.repository.ImageRepository;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import com.ssafy.fullerting.trans.model.entity.Trans;
import com.ssafy.fullerting.trans.repository.TransRepository;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExArticleService {
    private final ExArticleRepository exArticleRepository;
    private final DealRepository dealRepository;
    private final UserRepository userRepository;
    private final favoriteRepository favoriteRepository;
    private final TransRepository transRepository;
    private final PackDiaryRepository packDiaryRepository;
    private final DealService dealService;
    private final UserService userService;
    private final AmazonS3Service amazonS3Service;
    private final ImageRepository imageRepository;


    public void register(ExArticleRegisterRequest exArticleRegisterRequest, String email1, List<MultipartFile> files) {

        CustomUser customUser = userRepository.findByEmail(email1).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
//        log.info("ussssss"+customUser.getEmail());
        log.info("ussssss" + email1);
        S3ManyFilesResponse response =
                amazonS3Service.uploadFiles(files);

        Optional<PackDiary> packDiary = null;

        if (exArticleRegisterRequest.getPackdiaryid() != null)
            packDiary = packDiaryRepository.findById(exArticleRegisterRequest.getPackdiaryid());


        LocalDateTime createdAt = LocalDateTime.now(); // 현재 시각 설정


        ExArticle exArticle = ExArticle.builder()
                .id(exArticleRegisterRequest.getId())
                .title(exArticleRegisterRequest.getExArticleTitle())
                .content(exArticleRegisterRequest.getExArticleContent())
                .place(exArticleRegisterRequest.getExArticlePlace())
                .type(exArticleRegisterRequest.getExArticleType())
                .created_at(createdAt)
                .location(exArticleRegisterRequest.getEx_article_location())
                .user(customUser)
                .favorite(exArticleRegisterRequest.getFavorite())
                .packDiary(packDiary != null ? packDiary.orElse(null) : null)
                .build();

        log.info("exxxxx" + exArticle.toString());
//        exArticleRepository.saveAndFlush(exArticle);
        ExArticle exArticle1 = exArticleRepository.save(exArticle);


        List<Image> images = response.getUrls().entrySet().stream().map(stringStringEntry -> {
            Image image = new Image();
            image.setImg_store_url(stringStringEntry.getValue());
            image.setExArticle(exArticleRepository.findById(exArticle1.getId()).
                    orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS)));
            imageRepository.save(image);
            return image;
        }).collect(Collectors.toList());

//        exArticle1.setImage(images);
        ExArticle article = exArticleRepository.save(exArticle1);

        if (exArticleRegisterRequest.getExArticleType().equals(ExArticleType.DEAL)) {
            Deal deal = Deal.builder()
                    .deal_cur_price(exArticleRegisterRequest.getDeal_cur_price())
                    .build();

            deal.setexarticle(exArticle);

            System.out.println("exarttttt   " + exArticle.toString());

            dealRepository.save(deal);

            exArticle1.setdeal(deal);
            exArticleRepository.save(exArticle1);
//            System.out.println("exarttttt22222      " + exArticle.toString());
        } else {
 //sharing,generaltransaction
            int price = 0;

            if (exArticleRegisterRequest.getExArticleType().equals(ExArticleType.GENERAL_TRANSACTION)) {
                price = exArticleRegisterRequest.getDeal_cur_price();
            }

            Trans trans = Trans.builder()
                    .trans_sell_price(price)
                    .build();

            trans.setexarticle(exArticle);

            System.out.println("exarttttt   " + exArticle.toString());

            transRepository.save(trans);

            exArticle1.setTrans(trans); 
            exArticleRepository.save(exArticle1);

        }

        log.info("iiiiiii" + article.getImage());
    }

    public List<ExArticleAllResponse> allArticle() {

        List<ExArticle> exArticle = exArticleRepository.findAll();
        CustomUser user = UserResponse.toEntity(userService.getUserInfo());
        log.info("fffffffff");
//        log.info("eeeeeeeeeeeee" + exArticle.stream().
//                map(exArticle1 -> exArticle1.toResponse(exArticle1, user)).filter( exArticleResponse -> exArticleResponse.getExArticleId()==28).collect(Collectors.toList()));


        List<ExArticleAllResponse> exArticleResponses =
                exArticle.stream().map(exArticle1 -> exArticle1.toAllResponse(exArticle1, user)).
                        collect(Collectors.toList());

        log.info("exarticleeeee" + exArticleResponses.toString());

        return exArticleResponses;
    }


    public void like(Long ex_article_id) {
        //좋아요 로직
        ExArticle article = exArticleRepository.findById(ex_article_id).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));


        UserResponse userResponse = userService.getUserInfo();
        Long userid = userResponse.getId();
        Long articleid = article.getId();

        Optional<Favorite> favorite1 = favoriteRepository.findByUserIdAndExArticleId(userid, articleid);

        if (favorite1.isPresent()) {
            throw new ExArticleException(ExArticleErrorCode.ALREADY_LIKED);
        }

        Favorite favorite = new Favorite();
        favorite.setExArticle(article);
        favorite.setUser(userResponse.toEntity(userResponse));


        article.addfavorite(favorite);

        favoriteRepository.save(favorite);
    }

    public List<ExArticleKeywordResponse> keyword(String keyword) { //keyword 검색.
        List<ExArticle> exArticles = exArticleRepository.findAllByTitleContaining(keyword).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
        CustomUser user = UserResponse.toEntity(userService.getUserInfo());
        return exArticles.stream().map(exArticle -> exArticle.tokeyResponse(exArticle, user)).collect(Collectors.toList());

    }

    public ExArticleDetailResponse detail(Long ex_article_id) {
        ExArticle article = exArticleRepository.findById(ex_article_id).orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
        UserResponse userResponse = userService.getUserInfo();
        CustomUser user = userResponse.toEntity(userResponse);

        return article.toDetailResponse(article, user);

    }

    public void done(Long exArticleId, ExArticleDoneRequest exArticleDoneRequest) {
        //구매자 누군지 설정, 거래완료 true

        CustomUser customUser = UserResponse.toEntity(userService.getUserInfo());
        ExArticle exArticle = exArticleRepository.findById(exArticleId).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        log.info("exxxx" + exArticle.getPurchaserId());
        exArticle.setdone();
        exArticle.setpurchaserid(exArticleDoneRequest.getExArticlePurchaserId());

        exArticleRepository.save(exArticle);
        log.info("exxxx" + exArticle.getPurchaserId());
    }
}
