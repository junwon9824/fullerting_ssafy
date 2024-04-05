package com.ssafy.fullerting.record.steplog.repository;

import com.ssafy.fullerting.record.diary.model.entity.Diary;
import com.ssafy.fullerting.record.steplog.model.entity.StepLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropStepLogRepository extends JpaRepository<StepLog, Long> {
    List<StepLog> findAllByPackDiaryId(Long packDiaryId);
}
