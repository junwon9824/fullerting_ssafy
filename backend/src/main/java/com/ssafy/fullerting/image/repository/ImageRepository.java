package com.ssafy.fullerting.image.repository;


import com.ssafy.fullerting.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long > {
    List<Image> findAllByExArticleId(Long exarticleid);
    List<Image> findAllByDiaryId(Long diaryId);
}
