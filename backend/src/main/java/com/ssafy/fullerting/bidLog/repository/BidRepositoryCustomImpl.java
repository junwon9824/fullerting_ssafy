package com.ssafy.fullerting.bidLog.repository;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BidRepositoryCustomImpl implements BidRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<Integer> findMaxBidPriceByExArticleId(String exArticleId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("deal.exArticle.id").is(exArticleId)),
                Aggregation.group().max("bidLogPrice").as("maxPrice")
        );

        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(aggregation, "bidLog", BasicDBObject.class);
        BasicDBObject result = results.getUniqueMappedResult();

        Integer maxPrice = result != null ? result.getInt("maxPrice") : null;
        return Optional.ofNullable(maxPrice);
    }
}
