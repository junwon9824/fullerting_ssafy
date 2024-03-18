package com.ssafy.fullerting.record.steplog.model.entity;

import com.ssafy.fullerting.crop.step.model.entity.Step;
import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name="crop_step_log")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class StepLog { //작물단계기록
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_step_log_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="pack_diary_id")
    private PackDiary packDiary; //작물일지

    @ManyToOne
    @JoinColumn(name="crop_step_id")
    private Step step; //단계

    @Column(name = "crop_step_log_updated_at")
    @NotNull
    private Timestamp updatedAt; //갱신일
}
