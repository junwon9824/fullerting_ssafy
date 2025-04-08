package com.ssafy.fullerting.bidLog.repository;

import java.util.Optional;

public interface BidRepositoryCustom {
    Optional<Integer> findMaxBidPriceByExArticleId(String exArticleId);
}
