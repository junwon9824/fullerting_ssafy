package com.ssafy.fullerting.exArticle.repository;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.record.steplog.model.entity.StepLog;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExArticleRepository extends JpaRepository<ExArticle, Long>     {
    Optional<List<ExArticle>> findAllByTitleContaining(String keyword);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM ExArticle e JOIN FETCH e.deal WHERE e.id = :id")
    Optional<ExArticle> findByIdWithLock(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from ExArticle a left join fetch a.deal where a.id = :id")
    Optional<ExArticle> findWithDealByIdwithLock(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from ExArticle a  where a.id = :id")
    Optional<ExArticle> findByIdwithLock(@Param("id") Long id);



    @Query("SELECT ea FROM ExArticle ea LEFT JOIN FETCH ea.deal WHERE ea.id = :id")
    Optional<ExArticle> findWithDealById(@Param("id") Long id);


    List<ExArticle> findAllByType(ExArticleType type);

    @Query("SELECT e FROM ExArticle e WHERE  e.location like concat('%', :location , '%') and e.isDone=false  order by e.created_at  desc")
    List<ExArticle> findAllByOrderByCreated_atDescandlocation(String location); //:location 이 로그인한 유저 현재 위치

    @Query("select e from ExArticle e join e.favorite f where e.user.id = :userId and f.user.id = :userId")
    List<ExArticle> findAllByUserIdAndFavoriteIsNotEmpty(Long userId);


    @Query("select e from ExArticle e  where e.user.id = :userid and e.isDone = false ")
    List<ExArticle> findAllByUserID(Long userid);

    @Query("select e from ExArticle e  where e.user.id = :userid and e.isDone = true ")
    List<ExArticle> findAllByUserIDAndDone(Long userid);

    List<ExArticle> findAllByPackDiaryId(Long packDiaryId);

}
