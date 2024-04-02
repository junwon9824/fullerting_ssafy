package com.ssafy.fullerting.exArticle.repository;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.record.steplog.model.entity.StepLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExArticleRepository extends JpaRepository<ExArticle, Long> {
    Optional<List<ExArticle>> findAllByTitleContaining(String keyword);

    List<ExArticle> findAllByType(ExArticleType type);

    @Query("SELECT e FROM ExArticle e WHERE  e.location like concat('%', :location , '%')   order by e.created_at  desc")
    List<ExArticle> findAllByOrderByCreated_atDescandlocation(String location); //:location 이 로그인한 유저 현재 위치


    List<ExArticle> findAllByUserIdAndFavoriteIsNotEmpty(Long userid);

    @Query("select e from ExArticle e  where e.user.id = :userid and e.isDone = true ")
    List<ExArticle> findAllByUserIDAndDone(Long userid);

    List<ExArticle> findAllByPackDiaryId(Long packDiaryId);

}
