package com.ssafy.fullerting.community.love.repository;

import com.ssafy.fullerting.community.love.model.entity.Love;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoveRepository extends JpaRepository<Love, Long> {
    //    @Query("select from user u")
    Love findByCustomUserIdAndArticleId(Long userid,Long Articleid);
}
