package com.ssafy.fullerting.image.repository;


import com.ssafy.fullerting.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long > {


}
