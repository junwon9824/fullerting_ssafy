package com.ssafy.fullerting.record.diary.model.entity;

import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name="diary")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class Diary { //작물일기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="pack_diary_id")
    private PackDiary packDiary; //작물일지

    @Column(name = "diary_behavior", length = 10)
    @NotNull
    private String behavior; //행동(다이어리,물주기)

    @Column(name = "diary_title", length = 60)
    private String title; //제목

    @Column(name = "diary_content", length = 1000)
    private String content; //내용

    @Column(name = "diary_selected_at")
    @NotNull
    private LocalDate selectedAt; //선택일

    @Column(name = "diary_created_at")
    @NotNull
    private Timestamp createdAt; //생성일
}
