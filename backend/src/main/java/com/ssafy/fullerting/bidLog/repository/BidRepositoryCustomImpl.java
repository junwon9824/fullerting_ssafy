package com.ssafy.fullerting.bidLog.repository;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BidRepositoryCustomImpl implements BidRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public BidRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Integer> findMaxBidPriceByExArticleId(String exArticleId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("deal.exArticle.id").is(exArticleId)),
                Aggregation.group().max("bidLogPrice").as("maxBidPrice")
        );

        AggregationResults<MaxBidPrice> results = mongoTemplate.aggregate(aggregation, BidLog.class, MaxBidPrice.class);
        MaxBidPrice maxBidPrice = results.getUniqueMappedResult();
        return Optional.ofNullable(maxBidPrice != null ? maxBidPrice.getMaxBidPrice() : null);
    }

    private static class MaxBidPrice {
        private Integer maxBidPrice;

        public Integer getMaxBidPrice() {
            return maxBidPrice;
        }

        public void setMaxBidPrice(Integer maxBidPrice) {
            this.maxBidPrice = maxBidPrice;
        }
    }
}
