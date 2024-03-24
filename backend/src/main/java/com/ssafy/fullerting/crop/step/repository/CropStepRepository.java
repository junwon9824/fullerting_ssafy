package com.ssafy.fullerting.crop.step.repository;

import com.ssafy.fullerting.crop.step.model.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropStepRepository extends JpaRepository<Step, Long> {
    Optional<Step> findByCropIdAndStep(Long cropId, int step);
    @Query("SELECT MAX(s.step) FROM Step s WHERE s.crop.id = :cropId")
    Integer findMaxStepByCropId(Long cropId);
}
