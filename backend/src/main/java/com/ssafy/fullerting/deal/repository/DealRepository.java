package com.ssafy.fullerting.deal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.ssafy.fullerting.deal.model.entity.Deal;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface DealRepository extends JpaRepository<Deal, Long> {

    @Query("select d from Deal d where d.exArticle.user.id = :userid and d.exArticle.deal is not null")
    List<Deal> findAllMyDeal(Long userid);

    @Query("select d from Deal d where d.exArticle.deal is not null")
    List<Deal> findAllDeal();

    @Query("SELECT d FROM Deal d WHERE d.exArticle.id = :exArticleId")
    Optional<Deal> findByExArticleId(@Param("exArticleId") Long exArticleId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000")
    })
    @Query("SELECT d FROM Deal d WHERE d.id = :id")
    Optional<Deal> findByIdWithLock(@Param("id") Long id);
}
