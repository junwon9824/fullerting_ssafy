package com.ssafy.fullerting.favorite.repository;


import com.ssafy.fullerting.favorite.model.entity.Favorite;
 import org.springframework.data.jpa.repository.JpaRepository;

public interface favoriteRepository extends JpaRepository<Favorite,Long > {


}
