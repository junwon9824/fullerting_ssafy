package com.ssafy.fullerting.crop.type.repository;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropTypeRepository extends JpaRepository<Crop,Long> {

}
