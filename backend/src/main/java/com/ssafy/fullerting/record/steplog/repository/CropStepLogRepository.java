package com.ssafy.fullerting.record.steplog.repository;

import com.ssafy.fullerting.record.steplog.model.entity.StepLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropStepLogRepository extends JpaRepository<StepLog, Long> {

}
