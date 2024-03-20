package com.ssafy.fullerting.crop.tip.repository;

import com.ssafy.fullerting.crop.tip.model.entity.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropTipRepository extends JpaRepository<Tip, Long> {
    List<Tip> findAllByCropId(Long cropTypeId);
}
