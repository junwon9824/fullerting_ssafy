package com.ssafy.fullerting.exArticle.service;

import com.ssafy.fullerting.deal.exception.DealErrorCode;
import com.ssafy.fullerting.deal.exception.DealException;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.deal.service.DealService;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleDoneRequest;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterImageRequest;
import com.ssafy.fullerting.exArticle.model.dto.request.ExArticleRegisterRequest;
import com.ssafy.fullerting.exArticle.model.dto.request.UpdateArticleRequest;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleAllResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleDetailResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleKeywordResponse;
import com.ssafy.fullerting.exArticle.model.dto.response.ExArticleResponse;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.favorite.exception.FavoriteErrorCode;
import com.ssafy.fullerting.favorite.exception.FavoriteException;
import com.ssafy.fullerting.favorite.model.entity.Favorite;
import com.ssafy.fullerting.favorite.repository.favoriteRepository;

import com.ssafy.fullerting.global.s3.model.entity.response.S3ManyFilesResponse;
import com.ssafy.fullerting.global.s3.servcie.AmazonS3Service;
import com.ssafy.fullerting.image.exception.ImageErrorCode;
import com.ssafy.fullerting.image.exception.ImageException;
import com.ssafy.fullerting.image.model.entity.Image;
import com.ssafy.fullerting.image.repository.ImageRepository;
import com.ssafy.fullerting.record.diary.exception.DiaryException;
import com.ssafy.fullerting.record.diary.model.dto.request.UpdateDiaryRequest;
import com.ssafy.fullerting.record.diary.model.entity.Diary;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryErrorCode;
import com.ssafy.fullerting.record.packdiary.exception.PackDiaryException;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import com.ssafy.fullerting.record.packdiary.repository.PackDiaryRepository;
import com.ssafy.fullerting.trans.exception.TransErrorCode;
import com.ssafy.fullerting.trans.exception.TransException;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.fullerting.record.diary.exception.DiaryErrorCode.NOT_EXISTS_DIARY;
import static com.ssafy.fullerting.record.diary.exception.DiaryErrorCode.TRANSACTION_FAIL;

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


    public void registeriamges(List<MultipartFile> files, Long ex_article_id) {

        ExArticle article = exArticleRepository.findById(ex_article_id).orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));


        S3ManyFilesResponse response =
                amazonS3Service.uploadFiles(files);

        List<Image> images = response.getUrls().entrySet().stream().map(stringStringEntry -> {
            Image image = new Image();
            image.setImgStoreUrl(stringStringEntry.getValue());
            image.setExArticle(exArticleRepository.findById(article.getId()).
                    orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS)));
            imageRepository.save(image);
            return image;
        }).collect(Collectors.toList());

//        article.setImage(file);
    }


    public Long register(ExArticleRegisterRequest exArticleRegisterRequest, String email1, List<MultipartFile> files) {
//        public Long register(ExArticleRegisterRequest exArticleRegisterRequest, String email1) {

        if (exArticleRegisterRequest.getExArticleType().equals(null)) {
            throw new ExArticleException(ExArticleErrorCode.NOT_EXISTS);
        }

        CustomUser customUser = userRepository.findByEmail(email1).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
//        log.info("ussssss"+customUser.getEmail());
        log.info("ussssss" + email1);

//        List<MultipartFile> files = exArticleRegisterImageRequest.getMultipartFiles();

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
//                .place(exArticleRegisterRequest.getExArticlePlace())
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

        ExArticle article2 = null;

        List<Image> images = response.getUrls().entrySet().stream().map(stringStringEntry -> {
            Image image = new Image();
            image.setImgStoreUrl(stringStringEntry.getValue());
            image.setExArticle(exArticleRepository.findById(exArticle1.getId()).
                    orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS)));
            imageRepository.save(image);
            return image;
        }).collect(Collectors.toList());

//        exArticle1.setImage(images);
        ExArticle article = exArticleRepository.save(exArticle1);

        if (exArticleRegisterRequest.getExArticleType().equals(ExArticleType.DEAL)) {
            Deal deal = Deal.builder()
                    .dealCurPrice(exArticleRegisterRequest.getDealCurPrice())
                    .build();
            System.out.println("exartt      " + deal);

            deal.setexarticle(article);


            Deal deal1 = dealRepository.save(deal);

            article.setdeal(deal1);
            System.out.println("article.setdeal(deal1);   " + article.getDeal().getDealCurPrice());

            article2 = exArticleRepository.save(article);

            System.out.println("exarttttt22222      " + article2.getDeal());
        } else {
            //sharing,generaltransaction
            int price = 0;

            if (exArticleRegisterRequest.getExArticleType().equals(ExArticleType.GENERAL_TRANSACTION)) {
                price = exArticleRegisterRequest.getDealCurPrice();
            }

            Trans trans = Trans.builder()
                    .trans_sell_price(price)
                    .build();

            trans.setexarticle(article);

            System.out.println("exarttttt   " + exArticle.toString());

            Trans trans1 = transRepository.save(trans);

            article.setTrans(trans1);
            article2 = exArticleRepository.save(article);

        }
        log.info("iiiiiii" + article.getImage());

        return article2.getId();
    }

    public List<ExArticleAllResponse> allArticle() {

        List<ExArticle> exArticle = exArticleRepository.findAll();
        CustomUser user = UserResponse.toEntity(userService.getUserInfo());
//        log.info("eeeeeeeeeeeee" + exArticle.stream().
//                map(exArticle1 -> exArticle1.toResponse(exArticle1, user)).filter( exArticleResponse -> exArticleResponse.getExArticleId()==28).collect(Collectors.toList()));


        List<ExArticleAllResponse> exArticleResponses =
                exArticle.stream().map(exArticle1 -> exArticle1.toAllResponse(exArticle1, user)).
                        collect(Collectors.toList());


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

    public void deletelike(Long ex_article_id) {
        //좋아요 삭제
        ExArticle article = exArticleRepository.findById(ex_article_id).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        UserResponse userResponse = userService.getUserInfo();
        Long userid = userResponse.getId();

        Long articleid = article.getId();

        Favorite favorite1 = favoriteRepository.findByUserIdAndExArticleId(userid, articleid).
                orElseThrow(() -> new FavoriteException(FavoriteErrorCode.NOT_EXISTS));

//
//        Favorite favorite = new Favorite();
//        favorite.setExArticle(article);
//        favorite.setUser(userResponse.toEntity(userResponse));

        article.deletefavorite(favorite1);

        favoriteRepository.delete(favorite1);
    }

    public List<ExArticleResponse> selectFavorite() {  //나의 관심 article 추출.
        UserResponse userResponse = userService.getUserInfo();
        CustomUser user = UserResponse.toEntity(userResponse);

        List<ExArticle> exArticles = exArticleRepository.findAllByUserIdAndFavoriteIsNotEmpty(user.getId()); //내 article 중
        List<ExArticleResponse> exArticleResponses = exArticles.stream().map(exArticle -> exArticle.toResponse(exArticle, user)).collect(Collectors.toList());
        return exArticleResponses;
    }

    public List<ExArticleResponse> finishedarticles() {
        UserResponse userResponse = userService.getUserInfo();
        CustomUser user = UserResponse.toEntity(userResponse);

        List<ExArticle> exArticles = exArticleRepository.findAllByUserIDAndDone(user.getId()); //내 article 중
        List<ExArticleResponse> exArticleResponses = exArticles.stream().map(exArticle -> exArticle.toResponse(exArticle, user)).collect(Collectors.toList());
        return exArticleResponses;
    }

    public void deletearticle(Long ex_article_id) {
        ExArticle article = exArticleRepository.findById(ex_article_id).orElseThrow(()
                -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

        List<Image> image = imageRepository.findAllByExArticleId(ex_article_id);

        for (Image image1 : image) {
            imageRepository.delete(image1);
            amazonS3Service.deleteFile(image1.getImgStoreUrl()); //s3
        }

        if (article.getType().equals(ExArticleType.DEAL)) {
            Deal deal = dealRepository.findById(article.getDeal().getId()).orElseThrow(() -> new DealException(DealErrorCode.NOT_EXISTS));
            dealRepository.delete(deal);
        } else {
            Trans trans = transRepository.findById(article.getTrans().getId()).orElseThrow(()
                    -> new TransException(TransErrorCode.NOT_EXISTS));
            transRepository.delete(trans);
        }

        exArticleRepository.delete(article);

    }

    public void convert_like(Long ex_article_id) {

        ExArticle article = exArticleRepository.findById(ex_article_id).orElseThrow(() ->
                new ExArticleException(ExArticleErrorCode.NOT_EXISTS));


        UserResponse userResponse = userService.getUserInfo();
        Long userid = userResponse.getId();
        Long articleid = article.getId();

        Optional<Favorite> favorite1 = favoriteRepository.findByUserIdAndExArticleId(userid, articleid);

        if (favorite1.isPresent()) { //내가 이미 좋아요를 한경우 삭제를 해야한다.

            article.deletefavorite(favorite1.orElse(null));

            favoriteRepository.delete(favorite1.orElse(null));
            return;
        }
        // 좋아요를 안한경우

        Favorite favorite = new Favorite();
        favorite.setExArticle(article);
        favorite.setUser(userResponse.toEntity(userResponse));


        article.addfavorite(favorite);

        favoriteRepository.save(favorite);

    }

    public ExArticle modifyarticle(Long exArticleId,
                                   UpdateArticleRequest updateArticleRequest, CustomUser customUser,List<MultipartFile> files) {
        ExArticle article = exArticleRepository.findById(exArticleId).orElseThrow(() -> new ExArticleException
                (ExArticleErrorCode.NOT_EXISTS));

        if (customUser.getId() != article.getUser().getId()) {
            throw new ExArticleException(ExArticleErrorCode.NOT_MINE);
        }

        //이미지 삭제
        List<Image> imageList = imageRepository.findAllByExArticleId(exArticleId); //현재 게시물의 모든 이미지들.

        List<Image> unmodifiedimageList = new ArrayList<>();
        List<Image> delete_imageList = new ArrayList<>();

        log.info("updateArticleRequest" + updateArticleRequest.getUnmodifiedimageid());

        updateArticleRequest.getUnmodifiedimageid().forEach(
                aLong -> {
                    Image image = imageRepository.findById(aLong).orElseThrow(() -> new ImageException(ImageErrorCode.NOT_EXISTS));
                    unmodifiedimageList.add(image);
                }
        );
//        log.info("unmodifiedimageList"+ unmodifiedimageList.get(0).getId() );

        imageList.stream().forEach(image -> {
                    if (!unmodifiedimageList.contains(image)) {
                        amazonS3Service.deleteFile(image.getImgStoreUrl());
                        delete_imageList.add(image);
                        article.removeimage(image);
                        log.info("deleeeeee" + image.getId());

                    }


                }
        );

        imageRepository.deleteAll(delete_imageList);

        List<Image> images1 = new ArrayList<>();
        List<Image> newimages = new ArrayList<>();


//        updateArticleRequest.getImages().forEach(file -> {
//            newimages.add(Image.builder()
//                    .imgStoreUrl()
//                    .build());
//        });

        if ( !files.get(0).isEmpty()) {

            log.info("size 가 0보다 크다" +  files.get(0));
            //이미지 업로드
            S3ManyFilesResponse response = amazonS3Service.uploadFiles(files);
            //이미지 DB 저장
            response.getUrls().
                    entrySet().
                    stream().
                    map(stringStringEntry ->
                    {
                        Image image = imageRepository.save(Image.builder()
                                .imgStoreUrl(stringStringEntry.getValue())
                                .exArticle(article)
                                .build());
                        images1.add(image);
                        article.addimage(image);
                        return image;
                    }).

                    collect(Collectors.toList());
        }


//
//        article.setImage(images.stream().map(multipartFile -> {
//            return Image.builder()
//                    .exArticle(article)
//                    .imgStoreUrl()
//                    .build();
//        }).collect(Collectors.toList()));

        if (updateArticleRequest.getExArticleContent() != null) {
            article.setContent(updateArticleRequest.getExArticleContent());
        }

        if (updateArticleRequest.getExArticleTitle() != null) {
            article.setTitle(updateArticleRequest.getExArticleTitle());
        }
        if (updateArticleRequest.getEx_article_location() != null) {

            article.setLocation(updateArticleRequest.getEx_article_location());
        }
        if (updateArticleRequest.getPackdiaryid() != null) {

            article.setPackDiary(packDiaryRepository.findById(updateArticleRequest.getPackdiaryid()).orElseThrow(() ->
                    new PackDiaryException(PackDiaryErrorCode.NOT_EXISTS_PACK_DIARY)));
        }

        if (article.getDeal() != null) {

            Deal deal = article.getDeal();
            Optional<Integer> price = Optional.ofNullable(updateArticleRequest.getPrice());
            price.ifPresent(price1 -> {
                deal.setDealCurPrice(updateArticleRequest.getPrice());
                article.setdeal(deal);

            });

            if (updateArticleRequest.getExArticleType().equals(ExArticleType.SHARING)) {
                Deal deal1 = dealRepository.findById(article.getDeal().getId()).
                        orElseThrow(() -> new DealException(DealErrorCode.NOT_EXISTS));

                dealRepository.delete(deal1);
                article.setdeal(null);

                Trans trans = Trans.builder()
                        .exArticle(article)
                        .trans_sell_price(0)
                        .build();

                transRepository.save(trans);
                article.setTrans(trans);
            }

            if (updateArticleRequest.getExArticleType().equals(ExArticleType.GENERAL_TRANSACTION)) {
                Deal deal1 = dealRepository.findById(article.getDeal().getId()).
                        orElseThrow(() -> new DealException(DealErrorCode.NOT_EXISTS));

                dealRepository.delete(deal1);
                article.setdeal(null);

                Trans trans = Trans.builder()
                        .exArticle(article)
                        .trans_sell_price(updateArticleRequest.getPrice())
                        .build();

                transRepository.save(trans);
                article.setTrans(trans);
            }

//            Trans trans1 = transRepository.findById(article.getTrans().getId()).orElseThrow(() ->
//                    new TransException(TransErrorCode.NOT_EXISTS));
//
//            transRepository.delete(trans1);
//
//            article.setTrans(null);
//
//            Deal deal = Deal.builder()
//                    .exArticle(article)
//                    .dealCurPrice(updateArticleRequest.getPrice())
//                    .build();
//            dealRepository.save(deal);
//
//            article.setdeal(deal);
        }

        if (article.getTrans() != null) {
            Trans trans = article.getTrans();
            Optional<Integer> priceOptional = Optional.ofNullable(updateArticleRequest.getPrice());
            priceOptional.ifPresent(price -> {
                trans.setTrans_sell_price(price);
                article.setTrans(trans);
            });

            if (updateArticleRequest.getExArticleType().equals(ExArticleType.DEAL)) {

                Trans trans1 = transRepository.findById(article.getTrans().getId()).orElseThrow(() ->
                        new TransException(TransErrorCode.NOT_EXISTS));

                transRepository.delete(trans1);

                article.setTrans(null);

                Deal deal = Deal.builder()
                        .exArticle(article)
                        .dealCurPrice(updateArticleRequest.getPrice())
                        .build();
                dealRepository.save(deal);

                article.setdeal(deal);
            }
        }


        ExArticle modifiedexArticle = exArticleRepository.save(article);

//        log.info("modifff" + modifiedexArticle.getImage().get(0).getId());

        return modifiedexArticle;

    }


}
