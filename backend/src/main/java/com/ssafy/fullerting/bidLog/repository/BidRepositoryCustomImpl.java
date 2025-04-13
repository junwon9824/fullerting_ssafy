package com.ssafy.fullerting.bidLog.repository;

//import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import org.springframework.stereotype.Repository;
import jakarta.persistence.*;
import java.util.Optional;

@Repository
public class BidRepositoryCustomImpl implements BidRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

//
//    @Override
//    public Optional<Integer> findMaxBidPriceByExArticleId(String exArticleId) {
//        return Optional.empty();
//    }


    @Override
    public Optional<Integer> findMaxBidPriceByExArticleId(String exArticleId) {
//        String jpql = "SELECT MAX(b.bidLogPrice) FROM BidLog b WHERE b.deal.exArticle.id = :exArticleId";
        String jpql = "SELECT MAX(b.bidLogPrice) FROM BidLog b WHERE b.deal.exArticle.id = :exArticleId";

        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        query.setParameter("exArticleId", exArticleId);

        Integer maxBidPrice = query.getSingleResult();
        return Optional.ofNullable(maxBidPrice);
    }



}
