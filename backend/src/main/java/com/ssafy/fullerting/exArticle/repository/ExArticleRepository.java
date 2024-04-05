package com.ssafy.fullerting.exArticle.repository;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.model.entity.enums.ExArticleType;
import com.ssafy.fullerting.record.steplog.model.entity.StepLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExArticleRepository extends JpaRepository<ExArticle,Long > {
    Optional<List<ExArticle>> findAllByTitleContaining(String keyword);
    List<ExArticle> findAllByType(ExArticleType type);
    List<ExArticle> findAllByUserIdAndFavoriteIsNotEmpty(Long userid);
    @Query("select e from ExArticle e  where e.user.id = :userid and e.isDone = true ")
    List<ExArticle> findAllByUserIDAndDone(Long userid);
    List<ExArticle> findAllByPackDiaryId(Long packDiaryId);

}
