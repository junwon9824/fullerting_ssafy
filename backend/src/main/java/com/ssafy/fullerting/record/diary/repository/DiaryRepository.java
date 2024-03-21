package com.ssafy.fullerting.record.diary.repository;

import com.ssafy.fullerting.record.diary.model.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByPackDiaryId(Long packDiaryId);
}
