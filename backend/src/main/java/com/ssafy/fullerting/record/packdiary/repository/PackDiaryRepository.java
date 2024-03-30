package com.ssafy.fullerting.record.packdiary.repository;

import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackDiaryRepository extends JpaRepository<PackDiary, Long> {
    @Query(value = "SELECT p FROM PackDiary p WHERE p.title LIKE %:keyword%")
    List<PackDiary> findByTitle(@Param("keyword") String keyword);
}
