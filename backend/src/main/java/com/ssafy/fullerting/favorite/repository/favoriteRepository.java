package com.ssafy.fullerting.favorite.repository;


import com.ssafy.fullerting.favorite.model.entity.Favorite;
 import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface favoriteRepository extends JpaRepository<Favorite,Long > {

  Optional<Favorite> findByUserIdAndExArticleId(Long userid,Long exarticleid);

}
