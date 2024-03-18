package com.ssafy.fullerting.record.packdiary.repository;

import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackDiaryRepository extends JpaRepository<PackDiary, Long> {

}
