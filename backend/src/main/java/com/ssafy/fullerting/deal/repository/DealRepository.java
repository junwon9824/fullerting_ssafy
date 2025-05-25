package com.ssafy.fullerting.deal.repository;

import com.ssafy.fullerting.deal.model.entity.Deal;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {

    @Query("select d from Deal d where d.exArticle.user.id = :userid and d.exArticle.deal is not null")
    List<Deal> findAllMyDeal(Long userid);

    @Query("select d from Deal d where d.exArticle.deal is not null")
    List<Deal> findAllDeal();

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @QueryHints({
//            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000") // ✅ jakarta로 변경
//    })
//    @Query("SELECT d FROM Deal d WHERE d.id = :id")
//    Optional<Deal> findByIdWithLock(Long id);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000")
    })
    @Query("SELECT d FROM Deal d WHERE d.id = :id")
    Optional<Deal> findByIdWithLock(@Param("id") Long id);


}
